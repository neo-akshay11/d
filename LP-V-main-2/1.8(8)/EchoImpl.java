import java.rmi.*;
import java.rmi.server.*;

public class EchoImpl extends UnicastRemoteObject implements Echo {
    public EchoImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        System.out.println("Client requested hello message for " + name);
        return "Hello " + name;
    }
}
