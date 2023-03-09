__author__ = 'dadi'
import socket, struct, random,sys, time


if __name__ == '__main__':
    try:
        s = socket.create_connection( ('127.0.0.1',8888))
    except socket.error as msg:
        print("Error: ",msg.strerror)
        exit(-1)

    finished=False
    sr = 1; er=2**17-1
    random.seed()

    data=s.recv(1024)
    print(data.decode('ascii'))
    step_count=0
    while not finished:
        my_num= int(input("Please enter a number: "))
        try:
            s.sendall( struct.pack('!I',my_num) )
            answer=s.recv(1)
        except socket.error as msg:
            print('Error: ',msg.strerror)
            s.close()
            exit(-2)
        step_count+=1
        print('Sent ',my_num,' Answer ',answer.decode('ascii'))
        if answer==b'H':
            print("Send a higher number")
        if answer==b'S':
            print("Send a smaller number")
        if answer==b'G' or answer==b'L':
            finished=True
        time.sleep(0.25)

    s.close()
    if answer==b'G':
        print("I am the winner with",my_num,"in", step_count,"steps")
    else:
        print("I lost !!!")
#    input("Press Enter")
        