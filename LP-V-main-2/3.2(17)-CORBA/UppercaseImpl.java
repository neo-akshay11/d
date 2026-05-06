import UppercaseModule.UppercasePOA;

public class UppercaseImpl extends UppercasePOA {
    @Override
    public String changeToUppercase(String input) {
        System.out.println("Client sent: " + input);
        return input.toUpperCase();
    }
}

/*
UPPERCASE CORBA IMPLEMENTATION

The client sends one string to changeToUppercase().
The server prints the received string.
The method returns the uppercase string to the client.

Input : hello
Output: HELLO
*/
