import socket
import struct

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.connect(("127.0.0.1",8888))
print("Connection succesfull!")

print("Please input an array of numbers (ends with 0).")
l = []
ok = True

while ok != 0:
    a = int(input(("Please enter a number: ")))
    if a == 0:
        ok = False
    res = s.send(struct.pack("!H", a))


c = s.recv(2)

c = struct.unpack('!H',c)
print('The sum of the numbers is: ' + str(c[0]))

s.close()