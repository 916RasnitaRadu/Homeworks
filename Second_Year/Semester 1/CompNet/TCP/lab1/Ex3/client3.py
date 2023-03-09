# A client sends to the server a string. The server returns the reversed string to the client (characters from the end to begging)

import socket
import struct

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.connect(("127.0.0.1",8888))
#s.connect(("127.0.0.1",8889))
print("Client connected!")

while True:
    msg = input("Please enter a sentence: ")

    try:
        res = s.send(bytes(msg, 'ascii'))
    except socket.error as err:

        if err.strerror == "Broken pipe":
            break
        
        print("ERROR: " + err.strerror)

    result = s.recv(256).decode('ascii')

    print("The reversed string is: " + result)


