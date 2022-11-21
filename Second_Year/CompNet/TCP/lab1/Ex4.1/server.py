import socket
import pickle

if __name__ == '__main__':
    soc = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    soc.bind(("127.0.0.1",7777))
    soc.listen(5)
    print("Server is listening...")
    cs,addr = soc.accept()

    name=cs.recv(20)
    print(pickle.loads(name))
    port=addr[1]
    s=0
    print("The port is ", port)
    while port:
        s+=port%10
        port=port//10
    cs.send(pickle.dumps(s))
    cs.close()

