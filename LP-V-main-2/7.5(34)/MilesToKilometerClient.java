import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class MilesToKilometerClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter miles: ");
            double miles = sc.nextDouble();

            String address = "http://" + serverIP + ":7005/miles?miles=" + miles;
            HttpURLConnection connection = (HttpURLConnection) URI.create(address).toURL().openConnection();

            try (Scanner responseScanner = new Scanner(connection.getInputStream())) {
                System.out.println(responseScanner.nextLine());
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
7.5 MILES TO KILOMETER WEB SERVICE - JDK 26 RUN/INPUT:
1. Check: java -version and javac -version.
2. Compile in this folder: javac *.java
3. Run server first: java MilesToKilometerServer
4. Run client: java MilesToKilometerClient
5. Input miles value.
6. Browser/Postman test URL: http://localhost:7005/miles?miles=10
7. Service runs on port 7005 and returns kilometers.
*/
