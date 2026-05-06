import java.rmi.*;

public interface StringCompare extends Remote {
    public String largest(String first, String second) throws RemoteException;
}
