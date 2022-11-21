import socket
import struct

try:
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('127.0.0.1', 8888))
    server_socket.listen(5)
except socket.error as err:
    print("ERROR: " + err.strerror)



while True:
    print("Server is listening...")

    client_socket, addr = server_socket.accept()
    nr = client_socket.recv(2)
    nr = struct.unpack('!H', nr)

    print("The received number is " + str(nr))

    divisors = []

    for div in range(2, nr[0]):
        if nr[0] % div  == 0:
            divisors.append(div)

    res = client_socket.send(struct.pack('!H', len(divisors)))

    for elem in divisors:
        res = client_socket.send(struct.pack('!H', elem))
    
    print("The array was sent!")
