import java.util.Scanner;

public class TokenRingElection {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] processes = new int[n];
        System.out.println("Enter process ids:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        System.out.print("Enter process id which starts election: ");
        int initiator = sc.nextInt();

        int maxId = initiator;
        int startIndex = -1;

        for (int i = 0; i < n; i++) {
            if (processes[i] == initiator) {
                startIndex = i;
                break;
            }
        }

        if (startIndex == -1) {
            System.out.println("Initiator process not found.");
            return;
        }

        System.out.println("\nProcess " + initiator + " starts election.");

        int current = startIndex;

        do {
            int next = (current + 1) % n;

            System.out.println("Election message sent from Process "
                    + processes[current] + " to Process " + processes[next]);

            if (processes[next] > maxId) {
                maxId = processes[next];
            }

            current = next;
        } while (current != startIndex);

        System.out.println("\nElection message returned to initiator.");
        System.out.println("Process " + maxId + " is selected as new coordinator.");

        int coordIndex = -1;
        for (int i = 0; i < n; i++) {
            if (processes[i] == maxId) {
                coordIndex = i;
                break;
            }
        }

        current = coordIndex;
        do {
            int next = (current + 1) % n;

            System.out.println("Coordinator message sent from Process "
                    + processes[current] + " to Process " + processes[next]);

            current = next;
        } while (current != coordIndex);

        System.out.println("\nNew Coordinator is Process " + maxId);

        sc.close();
    }
}

/*
1. Check Java installation:
   java -version
   javac -version

2. Go to the folder where TokenRingElection.java is saved:
   cd path_to_your_folder

3. Compile the program:
   javac TokenRingElection.java

   👉 If no error → compilation successful

4. Run the program:
   java TokenRingElection

--------------------------------------------------

🔹 INPUT:

Enter number of processes: 5
Enter process ids:
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

Election message returned to initiator.
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
- Highest process ID becomes coordinator
- Coordinator message is circulated to all processes

--------------------------------------------------

🔹 CONCLUSION:

Token Ring Election Algorithm selects the process with the highest ID as coordinator after circulating the election message once around the ring.
*/
