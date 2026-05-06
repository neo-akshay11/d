import java.util.Scanner;

public class BullyElection {

    static boolean[] processes;
    static int n;
    static int messageCount = 0;
    static boolean[] electionStarted;
    static int coordinator = -1;

    static void election(int initiator) {
        if (electionStarted[initiator]) {
            return;
        }

        electionStarted[initiator] = true;

        System.out.println("\nProcess " + initiator + " initiates election");

        boolean higherExists = false;

        for (int i = initiator + 1; i < n; i++) {
            if (processes[i]) {
                System.out.println("Process " + initiator + " sends ELECTION message to Process " + i);
                messageCount++;

                System.out.println("Process " + i + " responds OK to Process " + initiator);
                messageCount++;

                higherExists = true;
            }
        }

        if (!higherExists) {
            coordinator = initiator;
            return;
        }

        for (int i = initiator + 1; i < n; i++) {
            if (processes[i]) {
                election(i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        processes = new boolean[n];

        for (int i = 0; i < n; i++) {
            processes[i] = true;
        }
        electionStarted = new boolean[n];

        System.out.print("Enter process to crash (0 to " + (n - 1) + "): ");
        int crash = sc.nextInt();
        processes[crash] = false;

        System.out.println("Process " + crash + " has crashed.");

        System.out.print("Enter process that initiates election: ");
        int initiator = sc.nextInt();

        if (!processes[initiator]) {
            System.out.println("Initiator process is down. Cannot start election.");
        } else {
            election(initiator);

            System.out.println("\nProcess " + coordinator + " becomes COORDINATOR");
            for (int i = 0; i < n; i++) {
                if (i != coordinator && processes[i]) {
                    System.out.println("Process " + coordinator + " sends COORDINATOR message to Process " + i);
                    messageCount++;
                }
            }

            System.out.println("\nTotal messages exchanged = " + messageCount);
            System.out.println("Time Complexity = O(n^2)");
        }

        sc.close();
    }
}

/*
1. Check Java installation:
   java -version
   javac -version

2. Go to your project folder:
   cd path_to_your_folder

3. Compile:
   javac BullyElection.java

4. Run:
   java BullyElection

5. Enter inputs:
   - Number of processes
   - Process to crash
   - Initiator process

Enter number of processes: 5
Enter process to crash (0 to 4): 4
Enter process that initiates election: 1

Process 4 has crashed.

Process 1 initiates election
Process 1 sends ELECTION message to Process 2
Process 2 responds OK to Process 1
Process 1 sends ELECTION message to Process 3
Process 3 responds OK to Process 1

Process 2 initiates election
Process 2 sends ELECTION message to Process 3
Process 3 responds OK to Process 2

Process 3 initiates election

Process 3 becomes COORDINATOR
Process 3 sends COORDINATOR message to Process 0
Process 3 sends COORDINATOR message to Process 1
Process 3 sends COORDINATOR message to Process 2

Total messages exchanged = 9
Time Complexity = O(n^2)
*/
