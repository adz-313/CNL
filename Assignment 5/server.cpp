#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <iostream>
#include <fstream>
using namespace std;

#define PORT	 8080
#define MAXLINE 1024

int main() {
    //Reading file
    fstream fout;
    long long int file_size;
    char *file_text;
    fout.open("file_name.txt", ios::in);
    fout.seekg(0,ios::end);
    file_size = fout.tellg();
    fout.clear();
    fout.seekg(0,ios::beg);
    file_text = new char[file_size];
    fout.read(file_text,file_size);
    cout << file_text;
    fout.close();

	int sockfd;
	char buffer[MAXLINE];
	struct sockaddr_in	 servaddr;

	// Creating socket file descriptor
	if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
		perror("socket creation failed");
		exit(EXIT_FAILURE);
	}

	memset(&servaddr, 0, sizeof(servaddr));

	// Filling server information
	servaddr.sin_family = AF_INET;
	servaddr.sin_port = htons(PORT);
	servaddr.sin_addr.s_addr = INADDR_ANY;

	int n, len;

	sendto(sockfd, (const char *)file_text, file_size, MSG_CONFIRM, (const struct sockaddr *) &servaddr, sizeof(servaddr));
	cout << "File sent";

	close(sockfd);
	return 0;
}


