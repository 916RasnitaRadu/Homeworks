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

int main(int argc, char** argv)
{
    int sock, n;
    unsigned int length;
    struct sockaddr_in server, from;
    struct hostent *hp;
    char buffer[256];
    
    if (argc != 3) { // you provide the IP and the port
        printf("Usage: %s <server_name> <port>\n",argv[0]);
        exit(1);
    }

    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (sock < 0)
    {
        error("Socket");
    }

    server.sin_family = AF_INET;
    hp = gethostbyname(argv[1]); // IP
    if (hp == 0) error("Unknown host.\n");

    bcopy((char*) hp->h_addr, (char *)&server.sin_addr, hp->h_length);
    server.sin_port = htons(atoi(argv[2]));
    length = sizeof(struct  sockaddr_in);

    printf("Please enter the message: ");
    bzero(buffer,256); // umple cu 0

    fgets(buffer,255,stdin); // si citeste

    n = sendto(sock, buffer, strlen(buffer), 0, (struct sockaddr*)&server, length);
    if (n < 0) error("Sendto\n");

    n = recvfrom(sock,buffer,256,0,(struct sockaddr*)&from, &length);

   if (n < 0) error("recvfrom");

   write(1,"Got an ack: ",12);
   write(1,buffer,n);
}