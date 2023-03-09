import socket
import struct

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

a = int(input('a = '))
b = int(input('b = '))

s.connect(("127.0.0.1",8888))

res = s.send(struct.pack("!H", a))
res = s.send(struct.pack("!H", b))
c = s.recv(2)

c = struct.unpack('!H',c)
print('a + b = ' + str(c[0]))

s.close()
