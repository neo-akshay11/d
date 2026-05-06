import java.util.*;

public class TokenRing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // User se total number of nodes (processes) lena
        System.out.print("Enter Number Of Nodes: ");
        int n = sc.nextInt();

        // Ring structure print karna
        System.out.println("Ring:");
        for (int i = 0; i < n; i++)
            System.out.print(i + " -> ");
        System.out.println("0"); // last node se fir 0 pe aata hai (circular)

        int choice;
        int token = 0; // starting me token process 0 ke paas hai

        do {
            // abhi token kis process ke paas hai wo dikhana
            System.out.println("\nCurrent Token at Process: " + token);

            // sender, receiver aur data input lena
            System.out.print("Enter Sender: ");
            int sender = sc.nextInt();

            System.out.print("Enter Receiver: ");
            int receiver = sc.nextInt();

            sc.nextLine(); // buffer clear karne ke liye
            System.out.print("Enter Data: ");
            String data = sc.nextLine();

            // token ko move karna jab tak wo sender tak na pahunch jaye
            System.out.println("\nToken Passing:");
            while (token != sender) {
                System.out.print(token + " -> ");
                token = (token + 1) % n; // circular movement
            }
            System.out.println(sender); // token ab sender ke paas aa gaya

            // Critical Section (sirf token holder hi enter kar sakta hai)
            System.out.println("\nProcess " + sender + " ENTERS Critical Section");
            System.out.println("Sending Data: " + data);

            // data ko sender se receiver tak forward karna (ring me)
            int i = sender;
            while (i != receiver) {
                System.out.println("Data forwarded By " + i + " To " + (i+1)%n);
                i = (i + 1) % n; // next process pe jana
            }

            // receiver ko data mil gaya
            System.out.println("Receiver " + receiver + " received data: " + data);

            // sender critical section se bahar aata hai
            System.out.println("Process " + sender + " EXITS Critical Section");

            // token next process ko de diya jata hai
            token = (sender + 1) % n;

            // user se poochna continue karna hai ya nahi
            System.out.print("\nEnter 1 to continue, 0 to stop: ");
            choice = sc.nextInt();

        } while (choice == 1);
    }
}