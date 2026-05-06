
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class Server {
    public static void main(String[] args) {
        try {
            AdditionImpl obj=new AdditionImpl();
            
            try {
                LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                System.out.println("RMI registry already running.");
            }
            Naming.rebind("rmi://localhost:1099/AddService", obj);
            System.out.println(" Addition Server is ready.");
        } catch (MalformedURLException | RemoteException e) {
            System.out.println("Server exception: " + e.toString());
        }
    }
}
