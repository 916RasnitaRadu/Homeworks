# Clientul trimite serverului un sir de caractere (de exemplu numele utilizatorului citit de la tastatura). Serverul afiseaza pe ecran sirul primit si portul clientului si ii raspunde acestuia cu suma cifrelor din Portul clientului. Clientul va afisa pe ecran numarul primit.

import socket
import struct

try:
    soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    soc.bind(('127.0.0.1', 8888))
except socket.error as err:
    print("ERROR: " + err.strerror)

while True:
    buff, addr = soc.recvfrom(256)

    msg = buff.decode('ascii')
    print("Message: ", msg, " received from ", addr)

    port = addr[1]
    print("PORT CLIENT: ", port)
     
    s = 0
    while port:
        s += port % 10
        port = port // 10

    soc.sendto(struct.pack('!I', s), addr)
