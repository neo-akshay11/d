import java.rmi.*;
import java.rmi.server.*;

public class SubtractImpl extends UnicastRemoteObject implements Subtract {
    public SubtractImpl() throws RemoteException {
        super();
    }

    @Override
    public int subtract(int a, int b) throws RemoteException {
        System.out.println("Client requested subtraction of " + a + " and " + b);
        return a - b;
    }
}
