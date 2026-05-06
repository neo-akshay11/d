import java.rmi.*;

public interface Subtract extends Remote {
    public int subtract(int a, int b) throws RemoteException;
}
