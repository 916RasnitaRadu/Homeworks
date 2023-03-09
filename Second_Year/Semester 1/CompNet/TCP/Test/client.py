import socket
import struct
from threading import Thread


def tcp_send_int(s, value : int):
    s.send(struct.pack('!H', value))

def tcp_receive_string(s, size=1024):
    return s.recv(size).decode('ascii')

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('127.0.0.1', 8889))
    print("The client has connected to the server!")
except socket.error as err:
    print("ERROR: " + err.strerror)
    exit(1)

xCoord = int(input("Please enter x-coord: "))
yCoord = int(input("Please enter y-coord: "))

try:
    tcp_send_int(s, xCoord)
    tcp_send_int(s, yCoord)
except socket.error as err:
    print("ERROR: " + err.strerror)
    exit(-1)

msg = tcp_receive_string(s)
print(msg)
