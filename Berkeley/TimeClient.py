import time, socket, random
from datetime import datetime

HOST = input("Enter server IP: ")
PORT = 9999

c = socket.socket()
c.connect((HOST, PORT))

while True:
    local = time.time() + random.uniform(-5, 5)
    c.send(str(local).encode())
    
    diff = float(c.recv(1024).decode())
    new = local + diff
    
    print("Local Time: ", datetime.fromtimestamp(local).strftime("%H:%M:%S"))
    print("Adjusted Time: ", datetime.fromtimestamp(new).strftime("%H:%M:%S"))
    print("\n")
    
    time.sleep(5)