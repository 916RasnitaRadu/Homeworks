// Se transmite o litera de la client la server, serveru trimite inapoi litera dublata

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <strings.h>
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
    char buff[1024];

    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (sock < 0) error("Opening socket.\n");

    length = sizeof(struct sockaddr_in);

    server.sin_family = AF_INET;
    server.sin_port = htons(7777);
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(sock, (struct sockaddr *)& server, length) < 0) error("Binding.\n");


    n = recvfrom(sock, buff, 1, 0, (struct sockaddr*)&from , &length);

    if (n < 0) error("Recvfrom\n");

    printf("Received a datagram: \n");
    printf("%c\n", buff[0]);

    //n = sendto(sock, "Got your message\n",17,0,(struct sockaddr*)&from, length);
    //if (n  < 0) error("sendto");

    buff[1] = buff[0];

    n = sendto(sock, buff, 2, 0, (struct sockaddr*)&from, length);
    if (n  < 0) error("sendto");
    
}