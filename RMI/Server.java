import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
import java.rmi.server.*;


public class Server{
    public static void main(String[] args) throws Exception{
        Add_Imp obj2 = new Add_Imp();
        
        Naming.rebind("rmi://localhost/add",obj2);
    }
}