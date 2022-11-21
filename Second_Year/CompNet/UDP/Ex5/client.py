import socket
import struct

soc = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

address = ('127.0.0.1', 7777)

num = int(input("Please enter a number: "))

soc.sendto(struct.pack("!I", num), address)

result, addr = soc.recvfrom(256)

print("The result is: ", struct.unpack('!I', result)[0])