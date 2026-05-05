import java.util.*;
import java.rmi.*;
import java.rmi.server.*;

public class Server{
    public static void main(String [] args){
        try{
             addImplement obj2 =  new addImplement();
        Naming.rebind("rmi://localhost:1099/fact",obj2);
        }
        catch(Exception e){
             System.out.println(e);
        }
       
        
        
    }
}