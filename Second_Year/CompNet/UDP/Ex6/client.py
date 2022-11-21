import struct
import socket

try:
    soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
except socket.error as err:
    print("ERROR: " + err.strerror)

address = ('127.0.0.1', 8888)

msg = input("Please enter your message: ")

soc.sendto(bytes(msg, 'ascii'), address)

result, addr = soc.recvfrom(256)
result = struct.unpack('!I', result)[0]

print("The sum of the port digits is: ", result)