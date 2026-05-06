import java.rmi.*;

public interface VowelCount extends Remote {
    public int countVowels(String word) throws RemoteException;
}
