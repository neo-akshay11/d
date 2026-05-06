import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BerkeleyTimeDaemonServer {
    public static void main(String[] args) {
        try {
            try (ServerSocket serverSocket = new ServerSocket(5002)) {
            System.out.println("Berkeley Time Daemon Server started...");
            System.out.println("Waiting for 2 machines...");

            ArrayList<Socket> machines = new ArrayList<>();
            ArrayList<Long> times = new ArrayList<>();

            long daemonTime = System.currentTimeMillis();
            times.add(daemonTime);

            for (int i = 0; i < 2; i++) {
                Socket socket = serverSocket.accept();
                machines.add(socket);

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                long machineTime = dis.readLong();
                times.add(machineTime);

                System.out.println("Machine " + (i + 1) + " time: " + machineTime);
            }

            long sum = 0;
            for (long time : times) {
                sum = sum + time;
            }

            long averageTime = sum / times.size();
            System.out.println("Daemon server time: " + daemonTime);
            System.out.println("Average synchronized time: " + averageTime);

            for (Socket socket : machines) {
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeLong(averageTime);
                socket.close();
            }

            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }
}
