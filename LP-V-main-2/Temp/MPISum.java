import mpi.*;

public class MPISum {

    public static void main(String[] args) throws Exception {

        // Start MPI environment
        MPI.Init(args);

        // Get process ID
        int rank = MPI.COMM_WORLD.Rank();

        // Get total number of processes
        int size = MPI.COMM_WORLD.Size();

        // Root process
        int root = 0;

        // Array for sending data
        int[] sendBuffer = new int[size];

        // Array for receiving one value
        int[] recvBuffer = new int[1];

        // Root process creates array
        if (rank == root) {

            System.out.println("Root process created array:");

            for (int i = 0; i < size; i++) {

                sendBuffer[i] = i + 1;

                System.out.print(sendBuffer[i] + " ");
            }

            System.out.println();
        }

        // Scatter operation
        MPI.COMM_WORLD.Scatter(
                sendBuffer, 0, 1, MPI.INT,
                recvBuffer, 0, 1, MPI.INT, root
        );

        // Each process receives one value
        int localResult = recvBuffer[0];

        System.out.println(
                "Process " + rank +
                " received " + recvBuffer[0] +
                " and local sum is " + localResult
        );

        // Local buffer for Reduce
        int[] localBuffer = { localResult };

        // Final result array
        int[] finalResult = new int[1];

        // Reduce operation using SUM
        MPI.COMM_WORLD.Reduce(
                localBuffer, 0,
                finalResult, 0,
                1, MPI.INT,
                MPI.SUM, root
        );

        // Root process prints final result
        if (rank == root) {

            System.out.println(
                    "Final sum at root process: "
                    + finalResult[0]
            );
        }

        // End MPI environment
        MPI.Finalize();
    }
}