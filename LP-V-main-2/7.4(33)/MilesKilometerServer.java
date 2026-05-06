import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MilesKilometerServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7004), 0);

            server.createContext("/convert", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                double miles = Double.parseDouble(query.get("miles"));
                double kilometers = miles * 1.60934;

                String response = "Kilometers: " + kilometers;
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Miles to Kilometer Web Service started on port 7004.");
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
7.4 CURRENT JDK 26 SERVER URL / POSTMAN:
1. Compile: javac *.java
2. Run this server: java MilesKilometerServer
3. Server port: 7004
4. Service path: /convert
5. Show in browser/Postman GET:
   http://localhost:7004/convert?miles=10
6. Output returns kilometers.
*/

/*
JAVA 8 SOAP WEB SERVICE VERSION - USE ONLY IF JAVA 8 IS AVAILABLE

FILE 1: MilesKilometerService.java
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface MilesKilometerService {
    @WebMethod
    double convert(double miles);
}

FILE 2: MilesKilometerServiceImpl.java
import javax.jws.WebService;

@WebService(endpointInterface = "MilesKilometerService")
public class MilesKilometerServiceImpl implements MilesKilometerService {
    public double convert(double miles) {
        return miles * 1.60934;
    }
}

FILE 3: Server.java
import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8083/miles", new MilesKilometerServiceImpl());
        System.out.println("Miles to Kilometer Web Service is running...");
    }
}

FILE 4: Client.java
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8083/miles?wsdl");
        QName qname = new QName("http://", "MilesKilometerServiceImplService");
        Service service = Service.create(url, qname);
        MilesKilometerService converter = service.getPort(MilesKilometerService.class);
        System.out.println("Kilometers: " + converter.convert(10));
    }
}

HOW TO RUN IN JAVA 8:
1. Check Java 8: java -version and javac -version.
2. Create the four files with the file names shown above.
3. Compile: javac *.java
4. Run server: java Server
5. Open/check WSDL URL: http://localhost:8083/miles?wsdl
6. Run client in another terminal: java Client
*/
