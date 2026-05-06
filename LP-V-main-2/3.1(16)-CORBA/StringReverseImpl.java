import StringReverseModule.StringReversePOA;

public class StringReverseImpl extends StringReversePOA {
    @Override
    public String reverseString(String input) {
        System.out.println("Client sent: " + input);
        return new StringBuilder(input).reverse().toString();
    }
}

/*
STRING REVERSE CORBA IMPLEMENTATION

The client sends one string to reverseString().
The server prints the received string.
The method returns the reversed string to the client.

Input : hello
Output: olleh
*/
