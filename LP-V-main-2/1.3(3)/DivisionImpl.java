import java.rmi.*;
import java.rmi.server.*;

public class DivisionImpl extends UnicastRemoteObject implements Division {
    public DivisionImpl() throws RemoteException {
        super();
    }

    @Override
    public double divide(double a, double b) throws RemoteException {
        System.out.println("Client requested division of " + a + " and " + b);
        if (b == 0) {
            throw new RemoteException("Cannot divide by zero");
        }
        return a / b;
    }
}
