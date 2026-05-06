import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BerkeleyTimeDaemonClient {
    public static void main(String[] args) {
        try {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            try (Socket socket = new Socket(serverIP, 5002)) {
                long localTime = System.currentTimeMillis();

                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(localTime);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long synchronizedTime = dis.readLong();

                System.out.println("Local machine time: " + localTime);
                System.out.println("Synchronized time from daemon: " + synchronizedTime);
                System.out.println("Clock adjustment: " + (synchronizedTime - localTime));
            }
        } catch (IOException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
BERKELEY CLOCK SYNCHRONIZATION - CHECK / RUN / INPUT / OUTPUT

1. Check Java installation:
   java -version
   javac -version

2. Go to folder:
   cd path_to_your_folder

3. Compile:
   javac BerkeleyTimeDaemonServer.java
   javac BerkeleyTimeDaemonClient.java

4. Run server first:
   java BerkeleyTimeDaemonServer

5. Run clients in separate terminals:
   java BerkeleyTimeDaemonClient
   java BerkeleyTimeDaemonClient

   (Optional: java BerkeleyTimeDaemonClient <server_ip>)

--------------------------------------------------

🔹 INPUT:
- No manual input required
- System time is taken automatically

--------------------------------------------------

🔹 OUTPUT:

📌 Server Output:
Berkeley Time Daemon Server started...
Waiting for 2 machines...
Machine 1 time: 1714821000000
Machine 2 time: 1714821005000
Daemon server time: 1714820998000
Average synchronized time: 1714821001000

--------------------------------------------------

📌 Client Output (Example):

Local machine time: 1714821000000
Synchronized time from daemon: 1714821001000
Clock adjustment: +1000

--------------------------------------------------

🔹 OBSERVATION:

- Server collects time from all machines
- Computes average time
- Sends synchronized time to all clients
- Each client adjusts its clock

--------------------------------------------------

🔹 CONCLUSION:

Berkeley Algorithm synchronizes clocks by calculating average system time and adjusting each machine accordingly.
*/
