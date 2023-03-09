#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

void error(char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char* argv[])
{
    int sock, n;
    unsigned int length;
    struct sockaddr_in server, from;
    char* buffer;

    if (argc < 2) { printf("Please enter a word.\n"); exit(1);}

    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (sock < 0)
    {
        error("Socket");
    }

    length = sizeof(struct sockaddr_in);

    server.sin_family = AF_INET;
    server.sin_port = htons(7777);
    server.sin_addr.s_addr = inet_addr("127.0.0.1");


    buffer = argv[1];

    n = sendto(sock,buffer , strlen(buffer), 0, (struct sockaddr*)&server, length);
    if (n < 0) error("Send_to\n");

    bzero(buffer,256);
   // int lungime;
    n = recvfrom(sock,buffer, 1, 0, (struct sockaddr*)&from, &length);
    if (n < 0) error("recvfrom\n");

   // lungime = ntohs(lungime);
    printf("Got an ack: \n");
    printf("%s\n",buffer);

}