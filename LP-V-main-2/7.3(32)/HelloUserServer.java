import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HelloUserServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7003), 0);

            server.createContext("/hello", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                String response = "Hello " + query.get("name");
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Hello User Web Service started on port 7003.");
        } catch (IOException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }

    static Map<String, String> getQuery(String query) throws java.io.UnsupportedEncodingException {
        Map<String, String> values = new HashMap<>();
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=");
            values.put(parts[0], URLDecoder.decode(parts[1], "UTF-8"));
        }
        return values;
    }
}

/*
7.3 CURRENT JDK 26 SERVER URL / POSTMAN:
1. Compile: javac *.java
2. Run this server: java HelloUserServer
3. Server port: 7003
4. Service path: /hello
5. Show in browser/Postman GET:
   http://localhost:7003/hello?name=Rahul
6. Output returns Hello Rahul.
*/

/*
JAVA 8 SOAP WEB SERVICE VERSION - USE ONLY IF JAVA 8 IS AVAILABLE

FILE 1: HelloUserService.java
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HelloUserService {
    @WebMethod
    String sayHello(String name);
}

FILE 2: HelloUserServiceImpl.java
import javax.jws.WebService;

@WebService(endpointInterface = "HelloUserService")
public class HelloUserServiceImpl implements HelloUserService {
    public String sayHello(String name) {
        return "Hello " + name;
    }
}

FILE 3: Server.java
import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8082/hello", new HelloUserServiceImpl());
        System.out.println("Hello User Web Service is running...");
    }
}

FILE 4: Client.java
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8082/hello?wsdl");
        QName qname = new QName("http://", "HelloUserServiceImplService");
        Service service = Service.create(url, qname);
        HelloUserService hello = service.getPort(HelloUserService.class);
        System.out.println(hello.sayHello("Rahul"));
    }
}

HOW TO RUN IN JAVA 8:
1. Check Java 8: java -version and javac -version.
2. Create the four files with the file names shown above.
3. Compile: javac *.java
4. Run server: java Server
5. Open/check WSDL URL: http://localhost:8082/hello?wsdl
6. Run client in another terminal: java Client
*/
