import java.util.Scanner;

public class RingElectionMessages {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] ring = new int[n];
        System.out.println("Enter process ids in ring:");
        for (int i = 0; i < n; i++) {
            ring[i] = sc.nextInt();
        }

        System.out.print("Enter process id which starts election: ");
        int initiator = sc.nextInt();

        int startIndex = -1;
        for (int i = 0; i < n; i++) {
            if (ring[i] == initiator) {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            System.out.println("Initiator process not found.");
            return;
        }

        int leader = initiator;
        System.out.println("\nProcess " + initiator + " starts election.");

        int current = startIndex;

        do {
            int next = (current + 1) % n;

            System.out.println("Election message sent from Process "
                    + ring[current] + " to Process " + ring[next]);

            if (ring[next] > leader) {
                leader = ring[next];
            }

            current = next;
        } while (current != startIndex);

        System.out.println("\nElection token returned to initiator.");
        System.out.println("Process " + leader + " is selected as new coordinator.");

        int leaderIndex = -1;
        for (int i = 0; i < n; i++) {
            if (ring[i] == leader) {
                leaderIndex = i;
                break;
            }
        }

        current = leaderIndex;

        do {
            int next = (current + 1) % n;

            System.out.println("Coordinator message sent from Process "
                    + ring[current] + " to Process " + ring[next]);

            current = next;
        } while (current != leaderIndex);

        System.out.println("\nNew Coordinator is Process " + leader);

        sc.close();
    }
}

/*
1. Check Java installation:
   java -version
   javac -version

2. Go to the folder:
   cd path_to_your_folder

3. Compile:
   javac RingElectionMessages.java

4. Run:
   java RingElectionMessages

--------------------------------------------------

🔹 INPUT:

Enter number of processes: 5
Enter process ids in ring:
1 3 5 2 4
Enter process id which starts election: 2

--------------------------------------------------

🔹 OUTPUT:

Process 2 starts election.

Election message sent from Process 2 to Process 4
Election message sent from Process 4 to Process 1
Election message sent from Process 1 to Process 3
Election message sent from Process 3 to Process 5
Election message sent from Process 5 to Process 2

Election token returned to initiator.
Process 5 is selected as new coordinator.

Coordinator message sent from Process 5 to Process 2
Coordinator message sent from Process 2 to Process 4
Coordinator message sent from Process 4 to Process 1
Coordinator message sent from Process 1 to Process 3
Coordinator message sent from Process 3 to Process 5

New Coordinator is Process 5

--------------------------------------------------

🔹 OBSERVATION:

- Election message circulates in ring
- Each process forwards message to next process
- Highest ID becomes coordinator
- Coordinator informs all processes

--------------------------------------------------

🔹 CONCLUSION:

Ring Election Algorithm selects the process with highest ID after one full ring traversal and ensures all processes are informed.
*/
