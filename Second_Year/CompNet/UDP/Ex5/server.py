# Intoarce suma cifrelor din Portul serverului adunate cu un numar primt de la client

import socket, struct

try:
    soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    soc.bind(('127.0.0.1', 7777))
except socket.error as err:
    print("ERROR: " + err.strerror)

while True:
    buff, addr = soc.recvfrom(256)
    
    c = struct.unpack('!I',buff)[0]
    s = 0
    port = addr[1]
    print("The port is: ", port)

    while port:
        s += port % 10
        port = port // 10

    s += c

    soc.sendto(struct.pack('!I', s), addr)