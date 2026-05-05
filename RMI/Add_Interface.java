import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
import java.rmi.server.*;

public interface Add_Interface extends Remote{
    public int add(int a , int b) throws RemoteException;
}

