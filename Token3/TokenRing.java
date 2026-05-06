import java.net.*;
import java.io.*;

public class TokenRing {

    static boolean hasToken = false;

    public static void main(String[] args) throws Exception {

        int myPort = Integer.parseInt(args[0]);
        String nextHost = args[1];
        int nextPort = Integer.parseInt(args[2]);
        hasToken = Boolean.parseBoolean(args[3]);

        // Receiver thread
        new Thread(() -> {
            try {
                ServerSocket ss = new ServerSocket(myPort);
                while(true) {
                    Socket s = ss.accept();
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    String msg = in.readUTF();
                    if(msg.equals("TOKEN")) {
                        System.out.println("Token Received");
                        hasToken = true;
                    }
                    s.close();
                }
            } catch(Exception e) {}
        }).start();

        // Token circulation
        while(true) {

            if(hasToken) {
       // Critical Section
                System.out.println("ENTERED CS");
                Thread.sleep(3000);
                System.out.println("EXITED CS");
                // Send token
                Socket s = new Socket(nextHost, nextPort);
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                out.writeUTF("TOKEN");
                System.out.println("Token Sent");

                hasToken = false;
                s.close();
            }

            Thread.sleep(5000);
        }
    }
}