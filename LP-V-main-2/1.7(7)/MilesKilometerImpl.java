import java.rmi.*;
import java.rmi.server.*;

public class MilesKilometerImpl extends UnicastRemoteObject implements MilesKilometer {
    public MilesKilometerImpl() throws RemoteException {
        super();
    }

    @Override
    public double convert(double miles) throws RemoteException {
        System.out.println("Client requested miles to kilometer conversion for " + miles);
        return miles * 1.60934;
    }
}
