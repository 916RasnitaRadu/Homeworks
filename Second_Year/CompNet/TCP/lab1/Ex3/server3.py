import socket
import struct

try:
    rs = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    rs.bind( ('127.0.0.1',8888) )
 #   rs.bind( ('127.0.0.1',8889) )
    rs.listen(5)
except socket.error as err:
    print("ERROR: " + err.strerror)

print("Server is listening...")

while True:
    client_socket, addr = rs.accept()
    msg = client_socket.recv(256).decode('ascii')

    print("Mesajul este: " + msg)

    if msg == "gata":
        break

    result = msg[::-1]
    
    res = client_socket.send(bytes(result, 'ascii'))



