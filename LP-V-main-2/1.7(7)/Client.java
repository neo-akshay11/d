import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            MilesKilometer stub = (MilesKilometer) Naming.lookup("rmi://localhost:1099/MilesKilometerService");

            System.out.print("Enter distance in miles: ");
            double miles = sc.nextDouble();

            double result = stub.convert(miles);
            System.out.println("Distance in kilometers: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
MILES TO KILOMETER RMI - CHECK/RUN/INPUT:
1. Check setup: java -version, javac -version, and rmiregistry.
2. Compile in this folder: javac *.java
3. Start registry if needed: rmiregistry
4. Run server: java Server
5. Run client: java Client
6. Input distance in miles; output shows kilometers.
*/
