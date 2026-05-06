import java.util.*;
import mpi.*;

public class Mpi{
    public static void main(String [] args){
        MPI.Init(args);

       int  size = MPI.COMM_WORLD.Size();
       int  rank = MPI.COMM_WORLD.Rank();

        int n = 12;
        int chunksize = n/size;
        int [] send = new int[n];
        int [] recv = new int[chunksize];
        double [] l_avg = new double[1];
        double [] g_avg = new double[size];


    if(rank == 0){
        for(int i=0;i<n;i++){
            send[i] = (i+1)*10;
        }
    }

    MPI.COMM_WORLD.Scatter(send, 0, chunksize,MPI.INT,
                            recv,0,chunksize,MPI.INT,
                            0);

    int sum = 0;
    for(int i=0;i<chunksize;i++){
        sum += recv[i];
    }

    l_avg[0] = (double)(sum/chunksize);

    MPI.COMM_WORLD.Gather(l_avg,0,1,MPI.DOUBLE,
                        g_avg,0,1,MPI.DOUBLE,
                        0);
    
    

    if(rank == 0){
        double sum2 = 0;
        for(int i = 0;i<size;i++){
            sum2 += g_avg[i];
        }
         double avg = sum2 / size;
        System.out.print(avg);
    }

   

    MPI.Finalize();
    }
}