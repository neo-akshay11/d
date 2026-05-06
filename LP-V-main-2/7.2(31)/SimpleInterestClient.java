import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

public class SimpleInterestClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter principal: ");
            double principal = sc.nextDouble();

            System.out.print("Enter rate: ");
            double rate = sc.nextDouble();

            System.out.print("Enter time: ");
            double time = sc.nextDouble();

            String address = "http://" + serverIP + ":7002/interest?principal=" + principal
                    + "&rate=" + rate + "&time=" + time;
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
7.2 SIMPLE INTEREST WEB SERVICE - JDK 26 RUN/INPUT:
1. Check: java -version and javac -version.
2. Compile in this folder: javac *.java
3. Run server first: java SimpleInterestServer
4. Run client: java SimpleInterestClient
5. Input principal, rate, and time.
6. Browser/Postman test URL: http://localhost:7002/interest?principal=1000&rate=5&time=2
7. Service runs on port 7002 and returns simple interest.
*/
