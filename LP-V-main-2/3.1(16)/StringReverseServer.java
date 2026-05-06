import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class StringReverseServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("String Reverse Server is ready.");

            try (Socket socket = serverSocket.accept();
                 BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

                String message = input.readLine();
                String reversed = new StringBuilder(message).reverse().toString();

                System.out.println("Client sent: " + message);
                output.println(reversed);
            }
        } catch (IOException e) {
            System.out.println("Server exception: " + e.toString());
        }
    }
}

/*
CORBA version note:

In old Java versions with CORBA support, this problem normally uses:
1. StringReverse.idl
2. idlj -fall StringReverse.idl
3. Server.java
4. Client.java
5. orbd naming service

Modern JDK versions removed CORBA tools such as idlj and orbd, so the runnable
code above uses socket-based client/server communication to demonstrate the
same distributed string reversing operation.
*/
