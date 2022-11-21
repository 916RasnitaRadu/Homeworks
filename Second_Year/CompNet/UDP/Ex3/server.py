# Intoarce numarul vocalelor

import socket

vocale = ['a', 'e', 'i', 'o', 'u', 'A','E','I','O','U']

try:
    soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    soc.bind(('127.0.0.1', 7777))
except socket.error as err:
    print("ERROR: " + err.strerror)

while True:
    buff, addr = soc.recvfrom(256)
    print("Received datagram: ", buff, " from ", addr)

    msg = buff.decode('ascii')
    res = 0
    for i in msg:
        if i in vocale:
            res += 1

    soc.sendto(bytes(str(res), 'ascii'), addr)
