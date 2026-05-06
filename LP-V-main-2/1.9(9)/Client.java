import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StringCompare stub = (StringCompare) Naming.lookup("rmi://localhost:1099/StringCompareService");

            System.out.print("Enter first string: ");
            String first = sc.nextLine();
            System.out.print("Enter second string: ");
            String second = sc.nextLine();

            String result = stub.largest(first, second);
            System.out.println("Lexicographically largest string: " + result);
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
STRING COMPARE RMI - CHECK/RUN/INPUT:
1. Check setup: java -version, javac -version, and rmiregistry.
2. Compile in this folder: javac *.java
3. Start registry if needed: rmiregistry
4. Run server: java Server
5. Run client: java Client
6. Input first string, then second string; output shows lexicographically larger string.
*/
