import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TokenRingNode {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TokenRingNode <0|1>");
            return;
        }

        int nodeId = Integer.parseInt(args[0]);

        if (nodeId == 0) {
            runNode0();
        } else {
            runNode1();
        }
    }

    static void runNode0() {
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Site 0 started with unique token.");
            enterCriticalSection(0);

            try (Socket sendSocket = new Socket("localhost", 6001)) {
                DataOutputStream dos = new DataOutputStream(sendSocket.getOutputStream());
                dos.writeUTF("TOKEN");
                System.out.println("Site 0 passed token to Site 1.");
            }

            try (Socket receiveSocket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
                String token = dis.readUTF();
                System.out.println("Site 0 received " + token + " back from Site 1.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Node 0 exception: " + e.toString());
        }
    }

    static void runNode1() {
        try (ServerSocket serverSocket = new ServerSocket(6001)) {
            System.out.println("Site 1 waiting for token...");

            try (Socket receiveSocket = serverSocket.accept()) {
                DataInputStream dis = new DataInputStream(receiveSocket.getInputStream());
                String token = dis.readUTF();
                System.out.println("Site 1 received " + token + ".");
            }

            enterCriticalSection(1);

            try (Socket sendSocket = new Socket("localhost", 6000)) {
                DataOutputStream dos = new DataOutputStream(sendSocket.getOutputStream());
                dos.writeUTF("TOKEN");
                System.out.println("Site 1 passed token to Site 0.");
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Node 1 exception: " + e.toString());
        }
    }

    static void enterCriticalSection(int site) throws InterruptedException {
        System.out.println("Site " + site + " has token.");
        System.out.println("Site " + site + " enters critical section.");
        Thread.sleep(1000);
        System.out.println("Site " + site + " exits critical section.");
    }
}

/*
TOKEN RING NODE - CHECK / RUN / INPUT / OUTPUT:

1. Check Java installation:
   java -version
   javac -version

2. Go to the folder:
   cd path_to_your_folder

3. Compile:
   javac TokenRingNode.java

4. Open TWO terminals (or machines)

5. Run Site 1 first:
   java TokenRingNode 1

6. Run Site 0 in another terminal:
   java TokenRingNode 0

--------------------------------------------------

🔹 INPUT:
- No manual input required
- Argument:
   0 → Site 0
   1 → Site 1

--------------------------------------------------

🔹 OUTPUT:

📌 Terminal 1 (Site 1)
Site 1 waiting for token...
Site 1 received TOKEN.
Site 1 has token.
Site 1 enters critical section.
Site 1 exits critical section.
Site 1 passed token to Site 0.

--------------------------------------------------

📌 Terminal 2 (Site 0)
Site 0 started with unique token.
Site 0 has token.
Site 0 enters critical section.
Site 0 exits critical section.
Site 0 passed token to Site 1.
Site 0 received TOKEN back from Site 1.

--------------------------------------------------

🔹 OBSERVATION:

- Only one site enters critical section at a time
- Token is passed in ring (0 → 1 → 0)
- No simultaneous access occurs

--------------------------------------------------

🔹 CONCLUSION:

Token Ring Mutual Exclusion ensures that only the node holding the unique token can enter the critical section, thereby guaranteeing mutual exclusion.
*/
