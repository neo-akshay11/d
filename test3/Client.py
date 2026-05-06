import time,socket,random;
from datetime import datetime;

PORT = 9999
HOST = input("give host input")

s = socket.socket()
s.connect((HOST,PORT))

while True :
    local = time.time()
    s.send(str(local).encode())

    diff = float(s.recv(1024).decode())
    new = local + diff
    print("Local Time: ", datetime.fromtimestamp(local).strftime("%H:%M:%S"))
    print("Adjusted Time: ", datetime.fromtimestamp(new).strftime("%H:%M:%S"))
    print("\n")

    time.sleep(5)
