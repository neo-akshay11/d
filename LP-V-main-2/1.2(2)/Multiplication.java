import java.rmi.*;

public interface Multiplication extends Remote {
    public int multiply(int a, int b) throws RemoteException;
}