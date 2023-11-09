import socket
import struct
import select

clients = []
nrClients = 2


rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
rs.bind( ('127.0.0.1',8889) )
rs.listen(nrClients)

# tratare primeste de la client

# tratare verifica si scriie pe ecran


for i in range(nrClients):
    client_socket, addr = rs.accept()
    clients.append(client_socket)

while True:
    readable, _, _ = select.select(clients, [], clients)
    if len(readable) > 0:
        msg=readable[0].recv(256)
        print(msg)