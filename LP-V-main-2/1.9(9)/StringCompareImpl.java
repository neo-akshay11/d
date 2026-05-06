import java.rmi.*;
import java.rmi.server.*;

public class StringCompareImpl extends UnicastRemoteObject implements StringCompare {
    public StringCompareImpl() throws RemoteException {
        super();
    }

    @Override
    public String largest(String first, String second) throws RemoteException {
        System.out.println("Client requested string comparison");
        if (first.compareTo(second) >= 0) {
            return first;
        }
        return second;
    }
}
