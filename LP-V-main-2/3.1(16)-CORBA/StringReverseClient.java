import StringReverseModule.StringReverse;
import StringReverseModule.StringReverseHelper;
import java.util.Scanner;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

public class StringReverseClient {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            ORB orb = ORB.init(args, null);

            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            StringReverse reverseObj =
                    StringReverseHelper.narrow(ncRef.resolve_str("StringReverseService"));

            System.out.print("Enter string: ");
            String input = sc.nextLine();

            String result = reverseObj.reverseString(input);
            System.out.println("Reversed string: " + result);
        } catch (Exception e) {
            System.out.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}

/*
STRING REVERSE CORBA CLIENT

Use Java 8 because idlj, orbd, and the old CORBA APIs are available there.

Generate stubs:
   idlj -fall StringReverse.idl

Compile:
   javac *.java StringReverseModule/*.java

Run order:
1. Start naming service:
   orbd -ORBInitialPort 1050

2. Start server:
   java StringReverseServer -ORBInitialPort 1050 -ORBInitialHost localhost

3. Start client:
   java StringReverseClient -ORBInitialPort 1050 -ORBInitialHost localhost

Input:
   hello

Expected client output:
   Reversed string: olleh
*/
