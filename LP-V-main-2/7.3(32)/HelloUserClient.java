import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Scanner;

public class HelloUserClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String serverIP = "localhost";
            if (args.length > 0) {
                serverIP = args[0];
            }

            System.out.print("Enter user name: ");
            String name = sc.nextLine();

            String address = "http://" + serverIP + ":7003/hello?name="
                    + URLEncoder.encode(name, "UTF-8");
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
7.3 HELLO USER WEB SERVICE - JDK 26 RUN/INPUT:
1. Check: java -version and javac -version.
2. Compile in this folder: javac *.java
3. Run server first: java HelloUserServer
4. Run client: java HelloUserClient
5. Input user name.
6. Browser/Postman test URL: http://localhost:7003/hello?name=Rahul
7. Service runs on port 7003 and returns Hello User_Name.
*/
