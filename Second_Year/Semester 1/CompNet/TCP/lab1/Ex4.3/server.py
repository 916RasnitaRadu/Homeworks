import socket
import pickle

if __name__ == '__main__':
    soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    soc.bind(('127.0.0.1', 9888))
    soc.listen(5)
    print("Server is listening...")
    cs, addr = soc.accept()

    name = cs.recv(20)
    name = pickle.loads(name)
    print("The message is", name)
    ip = addr[0]
    s = 0
    print("The ip is ", ip)
    digits = {'0': 0, '1':1, '2':2, '3': 3, '4' : 4, '5' : 5, '6' : 6, '7' : 7, '8': 8, '9': 9}

    for c in ip:
        if c in digits:
            s += digits[c]
    
    cs.send(pickle.dumps(s))
    cs.close()