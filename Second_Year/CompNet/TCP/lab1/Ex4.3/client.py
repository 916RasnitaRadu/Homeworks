# Calculeaza suma cifrelor IP-ului
import socket
import pickle

if __name__ == '__main__':
    name = input("Input something: ")

    soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    soc.connect(('127.0.0.1', 9888))
    print("Connected to the server!")
    soc.send(pickle.dumps(name))

    sum = soc.recv(20)
    sum = pickle.loads(sum)
    print("The sum is ", sum)
    soc.close()