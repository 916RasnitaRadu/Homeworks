import socket 
import pickle

if __name__ == '__main__':
    name = input("Input something: ")

    soc = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    soc.connect(("127.0.0.1",7777))
    print("Connected to the server!")

    soc.send(pickle.dumps(name))
    sum = soc.recv(20)
    print(pickle.loads(sum))
    soc.close()
