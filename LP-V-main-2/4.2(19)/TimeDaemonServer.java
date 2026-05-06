import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TimeDaemonServer {
    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Time Daemon Server started...");
            System.out.println("Waiting for 2 clients...");

            ArrayList<Socket> clients = new ArrayList<>();
            ArrayList<Long> times = new ArrayList<>();

            long daemonTime = System.currentTimeMillis();
            times.add(daemonTime);

            for (int i = 0; i < 2; i++) {
                Socket socket = serverSocket.accept();
                clients.add(socket);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long clientTime = dis.readLong();
                times.add(clientTime);

                System.out.println("Client " + (i + 1) + " time: " + clientTime);
            }

            long sum = 0;
            for (long time : times) {
                sum = sum + time;
            }

            long synchronizedTime = sum / times.size();
            System.out.println("Daemon time: " + daemonTime);
            System.out.println("Synchronized time calculated by daemon: " + synchronizedTime);

            for (Socket socket : clients) {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(synchronizedTime);
                socket.close();
            }

            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }
}
