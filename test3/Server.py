import time, random,socket;
from datetime import datetime;

PORT = 9999
HOST = ''

s = socket.socket()
s.bind((HOST, PORT))
s.listen(10)

n = int(input("no of clients: "))
clients = [s.accept()[0] for _ in range(n)]

while True:
    times = []
    for c in clients:
        times.append(float(c.recv(1024).decode()))
    
    servertime = time.time()
    avg = (sum(times) + servertime) / (1 + len(times))


    for i, t in enumerate(times):
        print(f"Client {i+1} Time: ", datetime.fromtimestamp(t).strftime("%H:%M:%S"))
        
    servertime = time.time()
    print("Server Time: ", datetime.fromtimestamp(servertime).strftime("%H:%M:%S"))
    print("Average Time: ", datetime.fromtimestamp(avg).strftime("%H:%M:%S"))
    print("\n")
    

    for i,c in enumerate(clients):
        c.send(str((avg - times[i])).encode())

    