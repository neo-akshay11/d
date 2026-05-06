import java.rmi.*;
import java.rmi.server.*;

public class VowelCountImpl extends UnicastRemoteObject implements VowelCount {
    public VowelCountImpl() throws RemoteException {
        super();
    }

    @Override
    public int countVowels(String word) throws RemoteException {
        System.out.println("Client requested vowel count for " + word);
        int count = 0;
        String lowerWord = word.toLowerCase();

        for (int i = 0; i < lowerWord.length(); i++) {
            char ch = lowerWord.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }

        return count;
    }
}
