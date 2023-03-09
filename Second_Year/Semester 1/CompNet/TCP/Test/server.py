import socket
import struct 
import select
import random
from math import sqrt

def tcp_receive_int(s):
    res = s.recv(2)
    res = struct.unpack('!H', res)[0]
    return res

def tcp_send_string(s, msg):
    s.send(msg.encode('ascii'))

def compute_distance(t1, t2):
    return sqrt((t2[0] - t1[0])*(t2[0] - t1[0]) + (t2[1] - t1[1])*(t2[1] - t1[1]))

random.seed()
clients = []
nrClients = 2
distances = dict()
xCoord = random.randint(0, 101)
yCoord = random.randint(0, 101)
coordinate = (xCoord, yCoord)

print("The generated coordinate is: ", coordinate)

try:
    soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    soc.bind(('127.0.0.1', 8889))
except socket.error as err:
    print("ERROR: " + err.strerror)
    exit(1)
    
soc.listen(nrClients)
clients.append(soc)
    
    
while len(distances.keys()) < nrClients:
    readable, _, _  = select.select(clients, [], [])

    for connection in readable:
        if connection == soc:
            client_socket, addr = soc.accept()
            clients.append(client_socket)
        else:

            clientXCoord = tcp_receive_int(connection)
            clientYCoord = tcp_receive_int(connection)

            clientCoord = (clientXCoord, clientYCoord)
            dist = compute_distance(coordinate, clientCoord)
            distances[connection] = dist

        

min = 9999
for c in distances:
    if distances[c] < min:
        min = distances[c]    

for c in distances:
    if distances[c] == min:
        tcp_send_string(c, "YOU WON!")
    else:
        tcp_send_string(c, "YOU LOST!")

soc.close()

