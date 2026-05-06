import java.rmi.*;
import java.rmi.server.*;

public class AdditionImpl extends UnicastRemoteObject implements Addition {
    public AdditionImpl() throws RemoteException {
        super();
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        System.out.println("Client request received: " + a + " and " + b);
        return a + b;
    }
}
