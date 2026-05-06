import java.rmi.*;
import java.rmi.server.*;

public class PowerImpl extends UnicastRemoteObject implements Power {
    public PowerImpl() throws RemoteException {
        super();
    }

    @Override
    public double calculatePower(double exponent) throws RemoteException {
        System.out.println("Client requested power calculation: 2 ^ " + exponent);
        return Math.pow(2, exponent);
    }
}
