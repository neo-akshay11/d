import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class Client{
    public static void main(String [] args){
      try{
         Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        addInterface obj1 = (addInterface)Naming.lookup("rmi://localhost:1099/fact");
        int res = obj1.fact(n);
        System.out.print(res);
      } 
      catch(Exception e){
        System.out.print(e);
      }
    }
}