import java.util.*;

public class Ringg{
   static int n ;
   static int coordinator ;
   static boolean[] p = null;


    public static  void init(int num){
        n = num;
        coordinator = n;
        p = new boolean[n+1];

        for(int i=1 ;i<=n;i++){
        p[i] = true;
       }
    } 

    public static void up(int id){
        if(p[id])System.out.print(id + " : up");
        else{
            System.out.print(id + " : down");
            p[id] = true;
        }
    }
    public static void down(int id){
        if(!p[id])System.out.print(id + " : down");
        else{
            System.out.print(id + " : up");
            p[id] = false;
        }
    }

    public static void display(){
        for(int id = 1 ;id <= n;id++){
             if(p[id])System.out.print(id + " : up");
        else{
            System.out.print(id + " : down");
        }
        }
       
    }

    public static void Election(int id){
       if (!p[id]) {
    System.out.println(id + " : down, cannot start election");
    return;

}
        
        ArrayList<Integer> token = new ArrayList <>();
        int i = id;
        do{
            if(p[i])token.add(i);
            i= (i % n) + 1;

        }while(i != id);

         coordinator = Collections.max(token);
           System.out.println("Processes in token: " + token);
        System.out.println("Elected Leader: P" + coordinator + "\n");
    }


    public static void main(String [] args){
         System.out.print("Enter number of processes: ");
       Scanner sc = new Scanner(System.in);
       Ringg.init(sc.nextInt());

       while(true){
            System.out.println("1. Up process");
            System.out.println("2. Down process");
            System.out.println("3. Conduct election");
            System.out.println("4. Display status");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();
            switch(ch){
                case 1 : 
                System.out.print("Enter process id: ");
                 up(sc.nextInt());
                 break;
                case 2 : System.out.print("Enter process id: ");
                 down(sc.nextInt());
                 break;
                case 3 : System.out.print("Enter process id: ");
                 Election(sc.nextInt());
                break;
                case 4 : 
                
                 display();
                 break;
                case 5 : return;
                default : break;
            }

       }
    }
}