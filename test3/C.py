import time ,random,socket;
from datetime import datetime;

PORT = 9999
HOST = input("give host input")

s = socket.socket()
s.connect((HOST,PORT))

while True:
    local = time.time() + random.uniform(-5,5)
    s.send((str(local)).encode())

    diff = float(s.recv(1024).decode())

    newtime = local + diff 

    print("Local Time:", datetime.fromtimestamp(local).time())
    print("newtime :", datetime.fromtimestamp(newtime).time())

    time.sleep(10)