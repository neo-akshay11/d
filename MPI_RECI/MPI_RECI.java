import java.util.Scanner;
import mpi.*;

public class MPI_RECI{
    public static void main(String []args) throws Exception{
        MPI.Init(args);
        int size = MPI.COMM_WORLD.Size();
        int rank = MPI.COMM_WORLD.Rank();

    int n = size;
    int chunksize = 1;
        int [] send = new int[n];
        int [] recv = new int[chunksize];

        double[] loc_rec = new double[1];
        double[] g_rec = new double[size];



        if (rank == 0){
            for(int i =0;i<n;i++){
                send[i] = (i+1)*10;
            }
        }

        MPI.COMM_WORLD.Scatter(
    send, 0, 1, MPI.INT,
    recv, 0, 1, MPI.INT,
    0
);

       loc_rec[0] = 1.0 / recv[0];

   
   
         MPI.COMM_WORLD.Gather(
                loc_rec, 0, 1, MPI.DOUBLE,
                g_rec, 0, 1, MPI.DOUBLE,
                0

        );

        // Display at root

        if (rank == 0) {
            System.out.println("Reciprocal Array:");
            for (int i = 0; i < size; i++) {
                System.out.print(g_rec[i] + " ");
                  //  System.out.flush();         // FORCE OUTPUT
            }

        }

   

        MPI.Finalize();

    }
}
