import time, random,socket;
from datetime import datetime;

PORT = 9999
HOST = ''

s= socket.socket()
s.bind((HOST,PORT))
s.listen(10)

n = int(input("no of clients: "))
client = [s.accept()[0]for _ in range(n)]

while True:
    times = []
    for c in  client:
        times.append(float(c.recv(1024).decode()))
    
    servertime = time.time()

    avg = (sum(times) + servertime)/ (1 + len(times))

    print("time: ", datetime.fromtimestamp(avg).time())


    for i,c in enumerate(client):
        c.send(str(avg - times[i]).encode())

    