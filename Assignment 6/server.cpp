#include <unistd.h>
#include <stdio.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <string.h>
#include <fstream>
#include <iostream>
#include <math.h>
#define PORT 8080
using namespace std;

int main()
{
    int server_fd, new_socket, valread;
    struct sockaddr_in address;
    int opt = 1;
    int addrlen = sizeof(address);
    char buffer[1024] = {0};
    char *hello = "Hello from server";

    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0)
    {
        perror("socket failed");
        exit(EXIT_FAILURE);
    }

    if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT,
                                                  &opt, sizeof(opt)))
    {
        perror("setsockopt");
        exit(EXIT_FAILURE);
    }
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons( PORT );

    if (bind(server_fd, (struct sockaddr *)&address,
                                 sizeof(address))<0)
    {
        perror("bind failed");
        exit(EXIT_FAILURE);
    }
    if (listen(server_fd, 3) < 0)
    {
        perror("listen");
        exit(EXIT_FAILURE);
    }
    if ((new_socket = accept(server_fd, (struct sockaddr *)&address,
                       (socklen_t*)&addrlen))<0)
    {
        perror("accept");
        exit(EXIT_FAILURE);
    }
    int choice;
    char ch[5];
    valread = read( new_socket , ch, 1);
    choice = atoi(ch);
    float angle;
    float temp;
    fstream fout;
    long long int file_size;
    char *file_text;
    char sinAns[20], cosAns[20], tanAns[20], ip[20];
    switch(choice)
    {
        case 1:
            valread = read( new_socket , buffer, 1024);
            cout << buffer << endl;
            send(new_socket, hello, strlen(hello), 0);
            break;

        case 2:
            fout.open("file_name.txt", ios::in);
            fout.seekg(0,ios::end);
            file_size = fout.tellg();
            fout.clear();
            fout.seekg(0,ios::beg);
            file_text = new char[file_size];
            fout.read(file_text,file_size);
            fout.close();
            send(new_socket , file_text , file_size , 0 );
            cout << "File sent";
            break;

        case 3:
            valread = read( new_socket , (char*)ip, 10);
            angle = atof(ip);
            cout << "Angle: " << angle;
            temp = sin(angle);
            sprintf(sinAns, "%f", temp);
            temp = cos(angle);
            sprintf(cosAns, "%f", temp);
            temp = tan(angle);
            sprintf(tanAns, "%f", temp);
            send(new_socket , sinAns , 10 , 0 );
            send(new_socket , cosAns , 10 , 0 );
            send(new_socket , tanAns , 10 , 0 );
            break;
    }
    return 0;
}
