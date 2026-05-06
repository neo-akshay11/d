import java.rmi.*;

public interface Power extends Remote {
    public double calculatePower(double exponent) throws RemoteException;
}
