#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <signal.h>
#include <unistd.h>
#include <stdlib.h>

int c;

void time_out(int semnal)
{
    int32_t r = -1;
    r = htonl(r);
    printf("Time out.\n");
    send(c, &r, sizeof(int32_t),0);
    close(c);
    exit(1);
}

void tratare()
{
    int cod;
    uint8_t b;
    char cmd[256];
    int pos = 0;
    int r = 0;
    if (c < 0)
    {
        fprintf(stderr, "Eroare la stabilirea conexiunii cu clientul.\n");
		exit(1);
    }
    else printf("Un nou client s-a conectat cu succes!\n");

    signal(SIGALRM, time_out); // pornim timerul
    alarm(10); // adaugam 10 sec la timer

    do {
        cod = recv(c, &b, 1, 0);
        printf("Am primit %d caractere.\n", cod);

        if (cod == 1) { alarm(10);}

        if (cod != 1) break;

        cmd[pos++] = b;

    }while (b);

    alarm(0); // oprim timerul cica
    FILE *fp = popen(cmd, "r");
    while(1) {
        char q;
        fscanf(fp, "%c", &q);

        if (feof(fp)) 
        { // if end of file la descriptorul fp
            q = 0;
            send(c, &q, 1, 0);
            break;
        }
        else send(c, &q, 1, 0);
    }

    int exit_code = htonl(WEXITSTATUS(pclose(fp)));
    send(c, &exit_code, 4, 0);

    close(c);
    if (exit_code >= 0) printf("Am inchis cu succes conexiunea cu un client.\n");
    else {
        printf("Am inchis cu eroare conexiunea cu un client. Cod de eroare %d.\n", r);
        exit(1);
    }

    exit(0);
}


int main() 
{
    int socket_sv, cod;
    unsigned l;
    struct sockaddr_in client, server;

    socket_sv = socket(PF_INET, SOCK_STREAM, 0);
    if (socket_sv < 0) {
        fprintf(stderr, "Eroare la creare socket server.\n");
		return 1;
    }

    memset(&server, 0, sizeof(struct sockaddr_in));
    server.sin_family = AF_INET;
    server.sin_port = htons(8888);
    server.sin_addr.s_addr = INADDR_ANY;

    cod = bind(socket_sv, (struct sockaddr *) &server, sizeof(struct sockaddr_in));
    if (cod < 0) {
        fprintf(stderr, "Eroare la bind. Portul este deja folosit.\n");
		return 1;
    }

    listen(socket_sv, 5);
    printf("Server is listening...\n");

    while(1) 
    {
        memset(&client, 0, sizeof(client));
        l = sizeof(client);

        printf("Waiting for clients...\n");
        c = accept(socket_sv, (struct sockaddr * restrict) &client, &l);
        printf("The client with the address %s and the port %d has connected.\n",inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        if (fork() == 0) 
        {
            tratare();
        }

    }

}