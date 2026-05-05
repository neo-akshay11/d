import java.util.Scanner;

public class BullyElection {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] processes = new int[n];
        System.out.println("Enter process ids:");
        for (int i = 0; i < n; i++) {
            processes[i] = sc.nextInt();
        }

        System.out.print("Enter failed coordinator id: ");
        int failedCoordinator = sc.nextInt();

        System.out.print("Enter election initiator id: ");
        int initiator = sc.nextInt();

        int coordinator = initiator;
        System.out.println("Coordinator " + failedCoordinator + " failed.");
        System.out.println("Process " + initiator + " starts election.");

        for (int process : processes) {
            if (process > initiator && process != failedCoordinator) {
                System.out.println("Election message from " + initiator + " to " + process);
                System.out.println("OK message from " + process + " to " + initiator);

                if (process > coordinator) {
                    coordinator = process;
                }
            }
        }

        for (int process : processes) {
            if (process != coordinator && process != failedCoordinator) {
                System.out.println("Coordinator message from " + coordinator + " to " + process);
            }
        }

        System.out.println("New coordinator is process " + coordinator);
        }
    }
}

/*
BULLY ELECTION - CHECK/RUN/INPUT:
1. Check setup: java -version and javac -version.
2. Compile in this folder: javac BullyElection.java
3. Run: java BullyElection
4. Input number of processes, then all process IDs.
5. Input failed coordinator ID and election initiator ID.
6. Output shows election/OK messages and new coordinator.
*/
