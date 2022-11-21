#define _WINSOCK_DEPRECATED_NO_WARNINGS 1

// exists on all platforms

#include <stdio.h>

// this section will only be compiled on NON Windows platforms

#ifndef _WIN32
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <errno.h>
#include <stdint.h>

 

#define closesocket close

typedef int SOCKET;

#else

// on Windows include and link these things

#include <WinSock2.h>

// for uint16_t an so

#include <cstdint>

// this is how we can link a library directly from the source code with the VC++ compiler – otherwise got o project settings and link to it explicitly

//#pragma comment(lib,"Ws2_32.lib")

#endif

 

int main() {

    SOCKET s;

    struct sockaddr_in server, client;

    int c, err;
    int unsigned lungime;
 

// initialize the Windows Sockets LIbrary only when compiled on Windows

#ifdef _WIN32

    WSADATA wsaData;

    if (WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {

            printf("Error initializing the Windows Sockets LIbrary");

            return -1;

    }

#endif

    s = socket(AF_INET, SOCK_STREAM, 0);

    if (s < 0) {
        printf("Eroare la crearea socketului server\n");
        return 1;
    }



    memset(&server, 0, sizeof(server));

    server.sin_port = htons(9999);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if (bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
        perror("Bind error:");
        return 1;
    }

    listen(s, 5);
    lungime = sizeof(client);
    memset(&client, 0, sizeof(client));

    while (1) {

        uint16_t count=0; 
        short int len;
        uint8_t caracter_primit;
        printf("Listening for incomming connections\n");
        c = accept(s, (struct sockaddr *) &client, &lungime);
        err = errno;

#ifdef _WIN32

    err = WSAGetLastError();

#endif

    if (c < 0) {
        printf("accept error: %d", err);
        continue;
    }

    printf("Incomming connected client from: %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

    // serving the connected client

    int res;
    
    res = recv(c,&len, 2, 0);
    len = ntohs(len); // l + 1
    printf("Am primit lungimea %d \n", len);

    for (int i = 0; i < len-1 ; i++) 
    {
        res = recv(c, &caracter_primit, 1, 0);
        printf("Am primit %d caractere de la client.\n", res);
        if (res != 1) {
            count = -1;
            break;
        }

        if (caracter_primit == ' ') {
            count++;
        }
    }
    printf("%d\n", count2);
    count = htons(count);

    res = send(c, (char*)&count, sizeof(count), 0);

    if (res != sizeof(count)) printf("Error sending result\n");

    //on Linux closesocket does not exist but was defined above as a define to close

    closesocket(c);

}

 

       // never reached

       // Release the Windows Sockets Library

#ifdef _WIN32

       WSACleanup();

#endif

}
