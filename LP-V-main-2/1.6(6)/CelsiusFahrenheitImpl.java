import java.rmi.*;
import java.rmi.server.*;

public class CelsiusFahrenheitImpl extends UnicastRemoteObject implements CelsiusFahrenheit {
    public CelsiusFahrenheitImpl() throws RemoteException {
        super();
    }

    @Override
    public double convert(double celsius) throws RemoteException {
        System.out.println("Client requested Celsius to Fahrenheit conversion for " + celsius);
        return (celsius * 9 / 5) + 32;
    }
}
