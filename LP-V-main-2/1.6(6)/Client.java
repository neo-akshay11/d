import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            CelsiusFahrenheit stub = (CelsiusFahrenheit) Naming.lookup("rmi://localhost:1099/CelsiusFahrenheitService");

            System.out.print("Enter temperature in Celsius: ");
            double celsius = sc.nextDouble();

            double result = stub.convert(celsius);
            System.out.println("Temperature in Fahrenheit: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
CELSIUS TO FAHRENHEIT RMI - CHECK/RUN/INPUT:
1. Check setup: java -version, javac -version, and rmiregistry.
2. Compile in this folder: javac *.java
3. Start registry if needed: rmiregistry
4. Run server: java Server
5. Run client: java Client
6. Input Celsius temperature; output shows Fahrenheit value.
*/
