import java.util.*;

public class Token{
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int s = sc.nextInt();
        int r = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        String data = sc.nextLine();

        int token = 0;
     
            int i = 0;
            while(i != s){
                    System.out.print(i + " -> ");
                i = (i+1)%n;
            }                 
             System.out.print("token reached sender");
            System.out.print("sender enter critical");

            int j = s;
            while(j != r){
                j = (j+1)%n;
            } 

             System.out.print("data reached receiver");
            System.out.print("sender exit critical");

            token = (s + 1)%10;


            
            
        

    }
}