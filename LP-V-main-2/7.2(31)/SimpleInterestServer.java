import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimpleInterestServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7002), 0);

            server.createContext("/interest", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                double principal = Double.parseDouble(query.get("principal"));
                double rate = Double.parseDouble(query.get("rate"));
                double time = Double.parseDouble(query.get("time"));
                double interest = (principal * rate * time) / 100;

                String response = "Simple interest: " + interest;
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Simple Interest Web Service started on port 7002.");
        } catch (IOException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }

    static Map<String, String> getQuery(String query) {
        Map<String, String> values = new HashMap<>();
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            values.put(parts[0], parts[1]);
        }
        return values;
    }
}

/*
7.2 CURRENT JDK 26 SERVER URL / POSTMAN:
1. Compile: javac *.java
2. Run this server: java SimpleInterestServer
3. Server port: 7002
4. Service path: /interest
5. Show in browser/Postman GET:
   http://localhost:7002/interest?principal=1000&rate=5&time=2
6. Output returns simple interest.
*/

/*
JAVA 8 SOAP WEB SERVICE VERSION - USE ONLY IF JAVA 8 IS AVAILABLE

FILE 1: SimpleInterestService.java
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SimpleInterestService {
    @WebMethod
    double calculate(double principal, double rate, double time);
}

FILE 2: SimpleInterestServiceImpl.java
import javax.jws.WebService;

@WebService(endpointInterface = "SimpleInterestService")
public class SimpleInterestServiceImpl implements SimpleInterestService {
    public double calculate(double principal, double rate, double time) {
        return (principal * rate * time) / 100;
    }
}

FILE 3: Server.java
import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8081/interest", new SimpleInterestServiceImpl());
        System.out.println("Simple Interest Web Service is running...");
    }
}

FILE 4: Client.java
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8081/interest?wsdl");
        QName qname = new QName("http://", "SimpleInterestServiceImplService");
        Service service = Service.create(url, qname);
        SimpleInterestService si = service.getPort(SimpleInterestService.class);
        System.out.println("Simple Interest: " + si.calculate(1000, 5, 2));
    }
}

HOW TO RUN IN JAVA 8:
1. Check Java 8: java -version and javac -version.
2. Create the four files with the file names shown above.
3. Compile: javac *.java
4. Run server: java Server
5. Open/check WSDL URL: http://localhost:8081/interest?wsdl
6. Run client in another terminal: java Client
*/
