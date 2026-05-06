import java.rmi.*;
import java.rmi.server.*;

public class MultiplicationImpl extends UnicastRemoteObject implements Multiplication {
    public MultiplicationImpl() throws RemoteException {
        super();
    }

    @Override
    public int multiply(int a, int b) throws RemoteException {
        System.out.println("Client requested multiplication of " + a + " and " + b);
        return a * b;
    }
}