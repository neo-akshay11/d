import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BerkeleyClient {
    public static void main(String[] args) {
        try {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }
            try (Socket socket = new Socket(serverIP, 5000)) {
                long localTime = System.currentTimeMillis();
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(localTime);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long synchronizedTime = dis.readLong();

                System.out.println("Local time: " + localTime);
                System.out.println("Synchronized time received from server: " + synchronizedTime);
                System.out.println("Time difference adjusted: " + (synchronizedTime - localTime));
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
BERKELEY TIME SYNC - CHECK / RUN / INPUT / OUTPUT

1. Check Java installation:
   java -version
   javac -version

2. Go to the folder:
   cd path_to_your_folder

3. Compile:
   javac BerkeleyServer.java
   javac BerkeleyClient.java

4. Run server on Machine 1:
   java BerkeleyServer

5. Run clients on Machine 2 (or multiple terminals):
   java BerkeleyClient
   java BerkeleyClient

   (Optional: java BerkeleyClient <server_ip>)

--------------------------------------------------

🔹 INPUT:
- No manual input required
- Each client sends its system time automatically

--------------------------------------------------

🔹 OUTPUT:

📌 Server Output:
Berkeley Time Server started...
Waiting for 2 clients...
Client 1 time: 1714823000000
Client 2 time: 1714823004000
Server time: 1714822998000
Average synchronized time: 1714823000666

--------------------------------------------------

📌 Client Output (Example):

Local time: 1714823000000
Synchronized time received from server: 1714823000666
Time difference adjusted: +666

--------------------------------------------------

🔹 OBSERVATION:

- Server collects time from all clients
- Computes average time
- Sends synchronized time to all clients
- Clients adjust their clocks

--------------------------------------------------

🔹 CONCLUSION:

Berkeley Algorithm synchronizes clocks by computing the average system time and adjusting all machines accordingly.
*/
