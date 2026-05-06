import java.rmi.*;

public interface MilesKilometer extends Remote {
    public double convert(double miles) throws RemoteException;
}
