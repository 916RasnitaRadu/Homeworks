import socket
import struct

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.connect(('127.0.0.1', 8888))

print("Successfully connected to the server!")



while True:

    number = int(input("Please input your number: "))

    try:
        res = s.send(struct.pack('!H', number))
    except socket.error as err:
        print("ERROR: " + str(err))

    length = s.recv(2)
    length = struct.unpack('!H', length)

    divisors = []
    for i in range(length[0]):
        div = s.recv(2)
        div = struct.unpack('!H', div)
        divisors.append(div[0])

    print("The array of divisors is: ")
    print(divisors)