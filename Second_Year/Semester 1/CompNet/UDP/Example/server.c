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
    /*
        sock = the socket
        length - size of the server
        n - number of the received / sent bytes to the client
        we use a sockaddr_in from because we do not have a end-to-end
    */

    int sock, length, n;
    unsigned int fromlen;
    struct sockaddr_in server, from;
    char buff[1024];

    if (argc < 2) // we have to provide the port as command line arg
    {
        fprintf(stderr, "ERROR: No port provided\n");
    }
    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (sock < 0) error("Opening socket");

    length = sizeof(server);

    bzero(&server, length); // umplem cu 0 length bytes pornind de la locatia de memorie a lui server

    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(atoi(argv[1]));

    if (bind(sock, (struct sockaddr *) &server, length) < 0)
    {
        error("Binding");
    }

    fromlen = sizeof(struct sockaddr_in);

    while (1) {

       n = recvfrom(sock,buff,1024,0,(struct sockaddr *)&from, &fromlen);

       if (n < 0) error("recvfrom");

       write(1,"Received a datagram: ",21);

       write(1,buff,n);

       n = sendto(sock,"Got your message\n",17, 0,(struct sockaddr *)&from,fromlen);

       if (n  < 0) error("sendto");

   }
}