import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SimpleCalculatorServer {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(7001), 0);

            server.createContext("/calculator", exchange -> {
                Map<String, String> query = getQuery(exchange.getRequestURI().getQuery());
                double a = Double.parseDouble(query.get("a"));
                double b = Double.parseDouble(query.get("b"));
                String operation = query.get("operation");

                double result;
                if (operation.equalsIgnoreCase("add")) {
                    result = a + b;
                } else if (operation.equalsIgnoreCase("sub")) {
                    result = a - b;
                } else if (operation.equalsIgnoreCase("mul")) {
                    result = a * b;
                } else if (operation.equalsIgnoreCase("div")) {
                    result = a / b;
                } else {
                    result = 0;
                }

                String response = "Calculator result: " + result;
                byte[] data = response.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(data);
                }
            });

            server.start();
            System.out.println("Simple Calculator Web Service started on port 7001.");
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
7.1 CURRENT JDK 26 SERVER URL / POSTMAN:
1. Compile: javac *.java
2. Run this server: java SimpleCalculatorServer
3. Server port: 7001
4. Service path: /calculator
5. Show in browser/Postman GET:
   http://localhost:7001/calculator?a=10&b=5&operation=add
6. Change operation to add, sub, mul, or div.
*/

/*
JAVA 8 SOAP WEB SERVICE VERSION - USE ONLY IF JAVA 8 IS AVAILABLE

FILE 1: CalculatorService.java
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CalculatorService {
    @WebMethod int add(int a, int b);
    @WebMethod int subtract(int a, int b);
    @WebMethod int multiply(int a, int b);
    @WebMethod int divide(int a, int b);
}

FILE 2: CalculatorServiceImpl.java
import javax.jws.WebService;

@WebService(endpointInterface = "CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }
    public int divide(int a, int b) { return a / b; }
}

FILE 3: Server.java
import javax.xml.ws.Endpoint;

public class Server {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/calculator", new CalculatorServiceImpl());
        System.out.println("Calculator Web Service is running...");
    }
}

FILE 4: Client.java
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Client {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080/calculator?wsdl");
        QName qname = new QName("http://", "CalculatorServiceImplService");
        Service service = Service.create(url, qname);
        CalculatorService calc = service.getPort(CalculatorService.class);
        System.out.println("Addition: " + calc.add(10, 5));
        System.out.println("Subtraction: " + calc.subtract(10, 5));
        System.out.println("Multiplication: " + calc.multiply(10, 5));
        System.out.println("Division: " + calc.divide(10, 5));
    }
}

HOW TO RUN IN JAVA 8:
1. Check Java 8: java -version and javac -version.
2. Create the four files with the file names shown above.
3. Compile: javac *.java
4. Run server: java Server
5. Open/check WSDL URL: http://localhost:8080/calculator?wsdl
6. Run client in another terminal: java Client
*/
