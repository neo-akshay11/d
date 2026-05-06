import UppercaseModule.Uppercase;
import UppercaseModule.UppercaseHelper;
import java.util.Scanner;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class UppercaseClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            Uppercase uppercaseObj =
                    UppercaseHelper.narrow(ncRef.resolve_str("UppercaseService"));

            System.out.print("Enter string: ");
            String input = sc.nextLine();

            String result = uppercaseObj.changeToUppercase(input);
            System.out.println("Uppercase string: " + result);
        } catch (Exception e) {
            System.out.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}

/*
UPPERCASE CORBA CLIENT

Use Java 8 because idlj, orbd, and the old CORBA APIs are available there.

Generate stubs:
   idlj -fall Uppercase.idl

Compile:
   javac *.java UppercaseModule/*.java

Run order:
1. Start naming service:
   orbd -ORBInitialPort 1050

2. Start server:
   java UppercaseServer -ORBInitialPort 1050 -ORBInitialHost localhost

3. Start client:
   java UppercaseClient -ORBInitialPort 1050 -ORBInitialHost localhost

Input:
   hello

Expected client output:
   Uppercase string: HELLO
*/
