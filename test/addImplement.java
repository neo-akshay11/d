import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class addImplement extends UnicastRemoteObject implements addInterface {
    public addImplement() throws RemoteException {
        super();
    }

    public int fact(int n) throws RemoteException{
        if(n<= 0){
            throw new RemoteException("no way");
        }
        int ans = 1;
        for(int i= 1;i<=n;i++){
            ans *=i;
        }
        return ans;
    }
}