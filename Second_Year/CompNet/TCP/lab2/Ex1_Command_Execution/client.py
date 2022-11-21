import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.connect(('127.0.0.1', 8888))

print("Successfully connected to the server!\n")

cmd = input("command = ")

res = s.send(bytes(cmd, 'ascii'))



