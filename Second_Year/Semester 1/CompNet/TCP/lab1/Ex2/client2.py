import socket
import struct

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except socket.error as err:
    print("ERROR: " + err.strerror)

s.connect(("127.0.0.1",9999))
print("Connection succesfull!")

a = input("Please enter a sentence: ")

l = len(a)

res = s.send(struct.pack("!H", l+1))

res = s.send(bytes(a, 'ascii'))
    
c = s.recv(2)

c = struct.unpack("!H",c)
print('The number of the space characters is: ' + str(c[0]))

s.close()
