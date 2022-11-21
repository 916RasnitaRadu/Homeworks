#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <errno.h>
#include <netinet/in.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>

int c;

void time_out(int semnal) {
    int32_t r = -1;
    r = htonl(r);
    printf("Time out.\n");
    send(c, &r, sizeof(int32_t), 0);
    close(c);
    exit(1);
}

void tratare() {
    int cod;
    int32_t r;
    uint8_t b;

   // struct sockaddr_in server;

    if (c < 0) { 
        fprintf(stderr, "Eroare la stabilizarea conexiunii cu clientul. \n");
        exit(1);
    }
    else printf("Un nou client s-a conectat cu succes.\n");

    signal(SIGALRM, time_out);
    alarm(10);

    r = 0; // rezultatul - nr de spatii primite de la client
    do {
        cod = recv(c,&b,1,0);
        printf("Am primit %d caractere.\n", cod);

        if (cod == 1) alarm(10);
        if (cod != 1) { r = -1; break; }

        if (b == ' ') {
            if (r == INT32_MAX) { r = -2; break; }
            r++;
        }
    }while(b != 0);
    
    alarm(0);
    
    r = htonl(r);
    send(c, &r, sizeof(int32_t),0);
    r = ntohl(r);
    close(c);
    if (r >= 0) 
    {
        printf("Am inchis cu succes conexiunea cu un client. I-am trimis %d spatii.\n",r);
    }
    else {
        printf("Am inchis cu eroare conexiunea cu un client. Cod de eroare %d.\n", r);
        exit(1);
    }
    exit(0);

}

int main() {
    int s, cod;
    unsigned int l;
    struct sockaddr_in client, server;

    s = socket(PF_INET, SOCK_STREAM, 0);
    if (s < 0) {
        fprintf(stderr, "Eroare la creare socket server.\n");
        return 1;
    }

    memset(&server, 0, sizeof(struct sockaddr_in));
    server.sin_family = AF_INET;
    server.sin_port = htons(4321);
    server.sin_addr.s_addr = INADDR_ANY;

    cod = bind(s, (struct sockaddr *) &server, sizeof(struct sockaddr_in));

    if (cod < 0) {
        fprintf(stderr, "Eroare la bind. Portul este deja folosit.\n");
        return 1;
    }

    listen(s, 5);

    while (1) {
        memset(&client, 0, sizeof(client));
        l = sizeof(client);

        printf("Astept sa se conecteze un client.\n");
        c = accept(s, (struct sockaddr *) &client, &l);
        printf("S-a conectat clientul cu adresa %s si portul %d.\n", inet_ntoa(client.sin_addr),ntohs(client.sin_port));

        if (fork() == 0) { tratare(); }
    }
}
