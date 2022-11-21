import socket
import struct
from threading import Thread

should_terminate = False

def read_input(s):
    global should_terminate
    while not should_terminate:
        print("Client reading...")
        data = s.recv(256)
        print(data.decode('ascii'))
    

def send_input(s):
    global should_terminate
    while not should_terminate:
        print("Client sending: ")
        msg = input()

        if (msg ==  "x"):
            should_terminate = True
            break

        try:
            s.send(bytes(msg, 'ascii'))
        except socket.error as err:
            print("ERROR: " + err)
            exit(-1)
        
        

try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect(('127.0.0.1', 8889))
except socket.error as msg:
    print("ERROR: " + msg.strerror)
    exit(-1)

read = Thread(target=read_input, args=(s,))
send = Thread(target=send_input, args=(s,))

# threads start
read.start()
send.start()

# threads are done
read.join()
send.join()


