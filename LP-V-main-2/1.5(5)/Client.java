import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Power stub = (Power) Naming.lookup("rmi://localhost:1099/PowerService");

            System.out.print("Enter exponent: ");
            double exponent = sc.nextDouble();

            double result = stub.calculatePower(exponent);
            System.out.println("Result: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
POWER RMI - CHECK/RUN/INPUT:
1. Check setup: java -version, javac -version, and rmiregistry.
2. Compile in this folder: javac *.java
3. Start registry if needed: rmiregistry
4. Run server: java Server
5. Run client: java Client
6. Input exponent value; output shows 2 raised to that exponent.
*/
