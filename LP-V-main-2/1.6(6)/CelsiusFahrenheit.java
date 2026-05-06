import java.rmi.*;

public interface CelsiusFahrenheit extends Remote {
    public double convert(double celsius) throws RemoteException;
}
