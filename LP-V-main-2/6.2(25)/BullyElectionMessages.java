import java.util.*;

public class BullyElectionMessages {

    static int[] processes;
    static int n;
    static int failed;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        n = sc.nextInt();

        processes = new int[n];

        System.out.println("Enter process IDs:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        // Sort IDs (important for correct Bully behavior)
        Arrays.sort(processes);

        System.out.print("Enter failed coordinator ID: ");
        failed = sc.nextInt();

        System.out.print("Enter process starting election: ");
        int initiator = sc.nextInt();

        System.out.println("\nCoordinator " + failed + " failed.");

        int leader = election(initiator);

        System.out.println("\nNew coordinator is process " + leader);

        sc.close();
    }

    static int election(int initiator) {

        System.out.println("\nProcess " + initiator + " starts election");

        boolean higherExists = false;
        int leader = initiator;

        for (int p : processes) {
            if (p > initiator && p != failed) {

                System.out.println("Process " + initiator + " sends ELECTION to " + p);
                System.out.println("Process " + p + " sends OK to " + initiator);

                higherExists = true;

                leader = election(p);
            }
        }

        if (!higherExists) {
            System.out.println("\nProcess " + initiator + " becomes COORDINATOR");

            for (int p : processes) {
                if (p != initiator && p != failed) {
                    System.out.println("Process " + initiator + " sends COORDINATOR to " + p);
                }
            }
            return initiator;
        }

        return leader;
    }
}

/*
BULLY ELECTION MESSAGES - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile in this folder: javac BullyElectionMessages.java
3. Run: java BullyElectionMessages
4. Input number of processes, then all process IDs.
5. Input failed coordinator ID and process starting election.
6. Output shows ELECTION, OK, COORDINATOR messages.

1. Check Java:
   java -version
   javac -version

2. Compile:
   javac BullyElectionMessages.java

3. Run:
   java BullyElectionMessages

4. Input:
   - Number of processes
   - Process IDs
   - Failed coordinator ID
   - Initiator process

5. Output:
   - ELECTION messages
   - OK messages
   - COORDINATOR messages

Enter number of processes: 4
Enter process IDs:
1 2 3 4
Enter failed coordinator ID: 4
Enter process starting election: 2

Coordinator 4 failed.

Process 2 starts election
Process 2 sends ELECTION to 3
Process 3 sends OK to 2

Process 3 starts election

Process 3 becomes COORDINATOR
Process 3 sends COORDINATOR to 1
Process 3 sends COORDINATOR to 2

New coordinator is process 3
*/
