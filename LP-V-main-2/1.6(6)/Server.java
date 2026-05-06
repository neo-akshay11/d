import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;

public class Server {
    public static void main(String[] args) {
        try {
            CelsiusFahrenheitImpl obj = new CelsiusFahrenheitImpl();
            try {
                LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                System.out.println("RMI registry already running.");
            }
            Naming.rebind("rmi://localhost:1099/CelsiusFahrenheitService", obj);
            System.out.println("Celsius to Fahrenheit Server is ready.");
        } catch (MalformedURLException | RemoteException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }
}
