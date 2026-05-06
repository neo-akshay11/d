import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class StringReverseClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in);
             Socket socket = new Socket("localhost", 5001);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.print("Enter string: ");
            String message = sc.nextLine();

            output.println(message);
            String result = input.readLine();

            System.out.println("Reversed string: " + result);
        } catch (IOException e) {
            System.out.println("Client exception: " + e.toString());
        }
    }
}


/*
STRING REVERSE SOCKET - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile in this folder: javac *.java
3. Run server first: java StringReverseServer
4. Run client in another terminal: java StringReverseClient
5. Input one string in client.
6. Output shows the reversed string from server.
*/
