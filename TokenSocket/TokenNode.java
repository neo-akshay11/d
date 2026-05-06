import java.io.*;
import java.util.*;
import java.net.*;

public class TokenNode {
    private int myPort;
    private String nextHost;
    private int nextPort;
    private boolean hasToken;

    public TokenNode(int myPort, String nextHost, int nextPort, boolean hasToken)
    {
        this.myPort = myPort;
        this.nextHost = nextHost;
        this.nextPort = nextPort;
        this.hasToken = hasToken;
    }

    public void start() throws Exception {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(myPort)) {
                while(true) {
                    Socket socket = serverSocket.accept();
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    String token = in.readUTF();

                    if(token.equals("TOKEN"))
                    {
                        System.out.println("Received Token\n");
                        hasToken = true;
                    }
                    socket.close();
                }
            }   catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        while(true)
        {
            if(hasToken)
            {
                enterCriticalSection();
                sendToken();
                hasToken = false;
            }
            Thread.sleep(2000);
        }
    }

    private void enterCriticalSection() throws Exception {
        System.out.println(">>>> ENTERED CRIT\n");
        Thread.sleep(3000); // simulate work
        System.out.println("<<<< EXITED  CRIT\n");
    }

    private void sendToken() throws Exception {
        try(Socket socket = new Socket(nextHost, nextPort);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                out.writeUTF("TOKEN");
                System.out.println("Token Sent to next node\n");
            } catch (Exception e)
            {
                System.out.println("Failed to send token. Retrying ...\n");
            }
        }
    public static void main(String[] args) throws Exception{
        // myPort, nextHost, nextPort, hasToken
        int myPort = Integer.parseInt(args[0]);
        String nextHost = args[1];
        int nextPort = Integer.parseInt(args[2]);
        boolean hasToken = Boolean.parseBoolean(args[3]);

        TokenNode node = new TokenNode(myPort, nextHost, nextPort, hasToken);
        node.start();
    }
}



/*
in 3 diff terminals
javac *.java

java TokenNode 5000 localhost 5001 true
java TokenNode 5001 localhost 5002 false
java TokenNode 5002 localhost 5000 false

*/