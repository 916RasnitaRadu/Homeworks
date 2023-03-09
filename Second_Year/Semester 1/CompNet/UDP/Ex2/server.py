# Intoarce lungimea cuvantului

import socket

try:
    soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    soc.bind(('127.0.0.1', 7777))
except socket.error as err:
    print("ERROR: " + err.strerror)

while True:
    buff, addr = soc.recvfrom(256)
    msg = buff.decode('ascii')

    soc.sendto(bytes(str(len(buff)), 'ascii'), addr)
