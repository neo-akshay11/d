import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try(Scanner sc=new Scanner(System.in)) {

            Multiplication stub=(Multiplication) Naming.lookup("rmi://localhost:1099/MultiplicationService");
            
            System.out.print("Enter first number: ");
            int a=sc.nextInt();
            System.out.print("Enter second number: ");
            int b=sc.nextInt();
            int result=stub.multiply(a, b);
            System.out.println("Result: " + result);

        }
        catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}

/*
MULTIPLICATION RMI - CHECK/RUN/INPUT:
1. Check setup: java -version, javac -version, and rmiregistry.
2. Compile in this folder: javac *.java
3. Start registry if needed: rmiregistry
4. Run server: java Server
5. Run client: java Client
6. Input first integer, then second integer; output shows multiplication result.
*/
