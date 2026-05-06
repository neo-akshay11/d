import java.rmi.*;

public interface Addition extends Remote {
    public int add(int a, int b) throws RemoteException;
}