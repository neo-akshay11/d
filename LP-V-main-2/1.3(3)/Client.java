import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Division stub = (Division) Naming.lookup("rmi://localhost:1099/DivisionService");

            System.out.print("Enter first number: ");
            double a = sc.nextDouble();
            System.out.print("Enter second number: ");
            double b = sc.nextDouble();

            double result = stub.divide(a, b);
            System.out.println("Result: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
DIVISION RMI - CHECK/RUN/INPUT:
1. Check setup: java -version, javac -version, and rmiregistry.
2. Compile in this folder: javac *.java
3. Start registry if needed: rmiregistry
4. Run server: java Server
5. Run client: java Client
6. Input dividend and divisor as numbers; divisor should not be 0.
*/
