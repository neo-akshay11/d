import java.rmi.*;

public interface Division extends Remote {
    public double divide(double a, double b) throws RemoteException;
}
