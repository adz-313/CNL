#include <fstream>
#include <iostream>
#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#define PORT 8080
using namespace std;

int main(int argc, char const *argv[])
{
	int sock = 0, valread;
	struct sockaddr_in serv_addr;
	char buffer[1024] = {0};
	char *hello = "Hello from client";
	if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
	{
		printf("\n Socket creation error \n");
		return -1;
	}

	serv_addr.sin_family = AF_INET;
	serv_addr.sin_port = htons(PORT);

	// Convert IPv4 and IPv6 addresses from text to binary form
	if(inet_pton(AF_INET, "127.0.0.1", &serv_addr.sin_addr)<=0)
	{
		printf("\nInvalid address/ Address not supported \n");
		return -1;
	}

	if (connect(sock, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0)
	{
		printf("\nConnection Failed \n");
		return -1;
	}

    cout << "TCP Socket Programming" << endl;
    cout << "1. Send hello to client" << endl;
    cout << "2. Receive file" << endl;
    cout << "3. Trigonometric calculator" << endl;

    int choice,ans;
    char angle[10], sin[20], cos[20], tan[20];
    float temp;
    cin >> choice;
    fstream fout;
    switch(choice)
    {
        case 1:
            send(sock, "1", 1, 0);
            send(sock, hello, strlen(hello), 0);
            valread = read( sock , buffer, 1024);
            cout << buffer << endl;
            break;

        case 2:
            send(sock, "2", 1, 0);
            valread = read( sock , buffer, 1024);
            fout.open("newfile.txt", ios::out);
            fout.write(buffer,strlen(buffer));
            cout << "File received";
            fout.close();
            break;

        case 3:
            send(sock, "3", 1, 0);
            cout << "Angle (in radians): ";
            cin >> temp;
            sprintf(angle, "%f", temp);
            send(sock, (char*)angle, 10, 0);
            valread = read( sock , &sin, 10);
            valread = read( sock , &cos, 10);
            valread = read( sock , &tan, 10);
            cout << "sin: " << sin << endl;
            cout << "cos: " << cos << endl;
            cout << "tan: " << tan << endl;
            break;
    }
	return 0;
}
