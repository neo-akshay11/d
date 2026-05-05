import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;

public class Add_Imp extends UnicastRemoteObject implements Add_Interface{
    public Add_Imp() throws RemoteException{
        super();
    }
    public int add(int a , int b) throws RemoteException{
        return a+b;
    }

}