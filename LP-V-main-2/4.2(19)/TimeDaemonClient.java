import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TimeDaemonClient {
    public static void main(String[] args) {
        try {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            try (Socket socket = new Socket(serverIP, 5001)) {
                long localTime = System.currentTimeMillis();

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(localTime);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long synchronizedTime = dis.readLong();

                System.out.println("Local time: " + localTime);
                System.out.println("Synchronized time received from daemon: " + synchronizedTime);
                System.out.println("Time adjustment: " + (synchronizedTime - localTime));
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
TIME DAEMON SYNCHRONIZATION - CHECK / RUN / INPUT / OUTPUT

1. Check Java installation:
   java -version
   javac -version

2. Go to the folder:
   cd path_to_your_folder

3. Compile:
   javac TimeDaemonServer.java
   javac TimeDaemonClient.java

4. Run server first:
   java TimeDaemonServer

5. Run clients in separate terminals:
   java TimeDaemonClient
   java TimeDaemonClient

   (Optional: java TimeDaemonClient <server_ip>)

--------------------------------------------------

🔹 INPUT:
- No manual input required
- System time is taken automatically

--------------------------------------------------

🔹 OUTPUT:

📌 Server Output:
Time Daemon Server started...
Waiting for 2 clients...
Client 1 time: 1714822000000
Client 2 time: 1714822005000
Daemon time: 1714821998000
Synchronized time calculated by daemon: 1714822001000

--------------------------------------------------

📌 Client Output (Example):

Local time: 1714822000000
Synchronized time received from daemon: 1714822001000
Time adjustment: +1000

--------------------------------------------------

🔹 OBSERVATION:

- Server collects time from all clients
- Computes average time
- Sends synchronized time to all clients
- Clients adjust their clocks

--------------------------------------------------

🔹 CONCLUSION:

Berkeley Algorithm synchronizes clocks by calculating average system time and adjusting each client accordingly.
*/
