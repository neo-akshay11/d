import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public interface addInterface extends Remote {
    public int fact(int a) throws RemoteException;
}