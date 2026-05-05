import java.util.*;

public class Ring {
    static boolean[] processes = null;
    static int n;
    static int coordinator;

    public static void init(int num) {
        n = num;
        processes = new boolean[n + 1];
        Arrays.fill(processes, true);
        System.out.println("\nInitializing processes.............");
        System.out.println(n + " processes created: P1 to P" + n);
        coordinator = n;
        System.out.println("Initial coordinator: P" + coordinator);
    }

    public static void up(int id) {
        if (processes[id]) System.out.println("P" + id + " is already up and running\n");
        else {
            processes[id] = true;
            System.out.println("P" + id + " is now up and running\n");
        }
    }

    public static void down(int id) {
        if (!processes[id]) System.out.println("P" + id + " is already down\n");
        else {
            processes[id] = false;
            System.out.println("P" + id + " is now down\n");
        }
    }

    public static void displayProcesses() {
        for (int i = 1; i <= n; i++) {
            if (processes[i]) System.out.println("P" + i + ": up");
            else System.out.println("P" + i + ": down");
        }
        System.out.println("Coordinator: P" + coordinator + "\n");
    }

    public static void conductElection(int id) {
        if (!processes[id]) {
            System.out.println("P" + id + " is down, it can't initiate election\n");
            return;
        }
        else if (coordinator == id) {
            System.out.println("P" + id + " is already the coordinator, no need to initiate election\n");
            return;
        }

        System.out.println("P" + id + " initiates election\n");
        ArrayList<Integer> token = new ArrayList<>();

        int current = id;
        do { 
            if (processes[current]) {
                System.out.println("P" + current + " adds its ID to token");
                token.add(current);
            }
            else {
                System.out.println("P" + current + " is down, skipping......");
            }
            current = (current % n) + 1;
        } while (current != id);

        coordinator = Collections.max(token);

        System.out.println("Processes in token: " + token);
        System.out.println("Elected Leader: P" + coordinator + "\n");
    }
public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int num = scanner.nextInt();
        Ring.init(num);
        System.out.println("");
        while (true) {
            System.out.println("1. Up process");
            System.out.println("2. Down process");
            System.out.println("3. Conduct election");
            System.out.println("4. Display status");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            System.out.println("");
            switch (choice) {
                case 1:
                    System.out.print("Enter process id: ");
                    up(scanner.nextInt());
                    break;
                case 2:
                    System.out.print("Enter process id: ");
                    down(scanner.nextInt());
                    break;
                case 3:
                    System.out.print("Enter process id that will initiate election: ");
                    conductElection(scanner.nextInt());
                    break;
                case 4:
                    displayProcesses();
                    break;
                case 5:
                    System.out.println("Exiting...\n");
                    return;
                default:
                    System.out.println("Invalid choice\n");
                    break;
            }
        }
    }
}