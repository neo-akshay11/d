import java.util.Scanner;
import mpi.*;

public class MPI_Sum{
    public static void main(String []args) throws Exception{
        MPI.Init(args);
        int size = MPI.COMM_WORLD.Size();
        int rank = MPI.COMM_WORLD.Rank();

        int [] send = new int[size];
        int [] recv = new int[1];
         int [] res = new int[1];

        if (rank == 0){
            for(int i =0;i<size;i++){
                send[i] = (i+1)*10;
            }
        }

        MPI.COMM_WORLD.Scatter(send, 0,1,MPI.INT,
                                recv,0,1,MPI.INT,
                                0                );

        //  MPI.COMM_WORLD.Scan(recv,0,res,0,1,MPI.INT,MPI.SUM);
        MPI.COMM_WORLD.Scan(recv,0,res,0,1,MPI.INT,MPI.PROD);
        
                System.out.println("Process " + rank + " received " + recv[0] + " | intermdiate sum = " + res[0]);

        MPI.Finalize();

    }
}

/**
 * 
 * 

akshay@MacBook-Air MPI % clear
akshay@MacBook-Air MPI % nano ~/.zshrc
akshay@MacBook-Air MPI % export MPJ_HOME=/Users/akshay/Downloads/mpj-v0_44
export PATH=$MPJ_HOME/bin:$PATH
akshay@MacBook-Air MPI % javac -cp .:$MPJ_HOME/lib/mpj.jar MPI_Sum.java
akshay@MacBook-Air MPI % mpjrun.sh -np 4 MPI_Sum
MPJ Express (0.44) is started in the multicore configuration
Process 3 received 40 | intermdiate sum = 100
Process 0 received 10 | intermdiate sum = 10
Process 2 received 30 | intermdiate sum = 60
Process 1 received 20 | intermdiate sum = 30
akshay@MacBook-Air MPI % 
 */



