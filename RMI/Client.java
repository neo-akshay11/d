import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.Naming;
import java.util.Scanner;


public class Client{
    public static void main(String[] args) throws Exception{
       Scanner sc = new Scanner(System.in);

       Add_Interface obj1 =  (Add_Interface)Naming.lookup("rmi://localhost/add");
        int a = sc.nextInt();
        int b = sc.nextInt();
         int res = obj1.add(a,b);
         System.out.println("Result: " + res);
    }
}