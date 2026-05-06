import java.rmi.*;
import java.rmi.server.*;

public class FactorialImpl extends UnicastRemoteObject implements Factorial {
    public FactorialImpl() throws RemoteException {
        super();
    }

    @Override
    public long findFactorial(int number) throws RemoteException {
        System.out.println("Client requested factorial of " + number);
        if (number < 0) {
            throw new RemoteException("Factorial is not defined for negative numbers");
        }

        long fact = 1;
        for (int i = 2; i <= number; i++) {
            fact = fact * i;
        }

        return fact;
    }
}
