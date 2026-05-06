import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;

public class Server {
    public static void main(String[] args) {
        try {
            VowelCountImpl obj = new VowelCountImpl();
            try {
                LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                System.out.println("RMI registry already running.");
            }
            Naming.rebind("rmi://localhost:1099/VowelCountService", obj);
            System.out.println("Vowel Count Server is ready.");
        } catch (MalformedURLException | RemoteException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }
}
