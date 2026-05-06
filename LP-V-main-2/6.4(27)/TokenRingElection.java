import java.util.*;

public class TokenRingElection {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] processes = new int[n];

        System.out.println("Enter process IDs in ring order:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        System.out.print("Enter process ID that starts election: ");
        int initiator = sc.nextInt();

        int index = -1;
        for (int i = 0; i < n; i++) {
            if (processes[i] == initiator) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Invalid initiator!");
            return;
        }

        System.out.println("\nProcess " + initiator + " starts election");

        List<Integer> token = new ArrayList<>();
        token.add(initiator);

        int i = (index + 1) % n;

        while (i != index) {
            System.out.println("Process " + processes[i] +
                    " receives TOKEN from Process " + processes[(i - 1 + n) % n]);

            token.add(processes[i]);

            System.out.println("Process " + processes[i] +
                    " passes TOKEN to Process " + processes[(i + 1) % n]);

            i = (i + 1) % n;
        }

        System.out.println("\nToken completed one full ring.");

        int coordinator = Collections.max(token);

        System.out.println("Processes in election: " + token);
        System.out.println("Process " + coordinator + " becomes COORDINATOR");

        int coordinatorIndex = -1;
        for (int j = 0; j < n; j++) {
            if (processes[j] == coordinator) {
                coordinatorIndex = j;
                break;
            }
        }

        System.out.println("\nCoordinator message passing:");

        int current = coordinatorIndex;
        do {
            int next = (current + 1) % n;
            System.out.println("Process " + processes[current]
                    + " sends COORDINATOR message to Process " + processes[next]);
            current = next;
        } while (current != coordinatorIndex);

        sc.close();
    }
}

/*
1. Check Java installation:
   java -version
   javac -version

2. Go to your project folder (where TokenRingElection.java is saved):
   cd path_to_your_folder

3. Compile the program:
   javac TokenRingElection.java

4. Run the program:
   java TokenRingElection

5. Enter inputs when prompted:
   - Enter number of processes
   - Enter process IDs in ring order
   - Enter process ID that starts election

6. Program will display:
   - TOKEN passing between processes
   - IDs collected in token
   - New coordinator (highest ID)

----------------------------------------------------

Enter number of processes: 5
Enter process IDs in ring order:
1 3 5 2 4
Enter process ID that starts election: 2

Process 2 starts election

Process 4 receives TOKEN from Process 2
Process 4 passes TOKEN to Process 1
Process 1 receives TOKEN from Process 4
Process 1 passes TOKEN to Process 3
Process 3 receives TOKEN from Process 1
Process 3 passes TOKEN to Process 5
Process 5 receives TOKEN from Process 3
Process 5 passes TOKEN to Process 2

Token completed one full ring.

Processes in election: [2, 4, 1, 3, 5]

Process 5 becomes COORDINATOR

Coordinator message passing:
Process 5 sends COORDINATOR message to Process 2
Process 2 sends COORDINATOR message to Process 4
Process 4 sends COORDINATOR message to Process 1
Process 1 sends COORDINATOR message to Process 3
Process 3 sends COORDINATOR message to Process 5
*/
