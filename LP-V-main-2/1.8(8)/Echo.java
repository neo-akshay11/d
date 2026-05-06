import java.rmi.*;

public interface Echo extends Remote {
    public String sayHello(String name) throws RemoteException;
}
