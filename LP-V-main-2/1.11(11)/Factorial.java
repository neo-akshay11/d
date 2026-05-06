import java.rmi.*;

public interface Factorial extends Remote {
    public long findFactorial(int number) throws RemoteException;
}
