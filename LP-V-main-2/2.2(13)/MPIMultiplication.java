import java.util.Scanner;

public class MPIMultiplication {
    public static void main(String[] args) throws InterruptedException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of processors: ");
            int processors = sc.nextInt();

            int[] numbers = new int[processors];
            System.out.println("Enter " + processors + " numbers:");
            for (int i = 0; i < processors; i++) {
                numbers[i] = sc.nextInt();
            }

            Worker[] workers = new Worker[processors];

            System.out.println("Root process distributing array elements...");
            for (int i = 0; i < processors; i++) {
                workers[i] = new Worker(i, numbers[i]);
                workers[i].start();
            }

            int finalMultiplication = 1;
            for (int i = 0; i < processors; i++) {
                workers[i].join();
                finalMultiplication = finalMultiplication * workers[i].getIntermediateMultiplication();
            }

            System.out.println("Final multiplication at root process: " + finalMultiplication);
        }
    }

    static class Worker extends Thread {
        private final int processId;
        private final int value;
        private int intermediateMultiplication;

        Worker(int processId, int value) {
            this.processId = processId;
            this.value = value;
        }

        @Override
        public void run() {
            intermediateMultiplication = value;
            System.out.println("Process " + processId + " received " + value
                    + " and intermediate multiplication is " + intermediateMultiplication);
        }

        int getIntermediateMultiplication() {
            return intermediateMultiplication;
        }
    }
}

/*
PRACTICAL 2.2 - MPI INTERMEDIATE MULTIPLICATION

This file keeps the Java thread simulation above for normal classroom running.
The real MPI / MPJ Express version is kept below as a reference. To run real
MPI, copy only the REAL MPI CODE block into MPIMultiplication.java, replacing
the simulation code above.

IMPORTANT FILE NAME RULE
1. The public class name is MPIMultiplication.
2. Java requires the file name to be exactly MPIMultiplication.java.
3. Do not rename it to mpi.java, multiplication.java, MPIMul.java, or any other
   name.
4. Compile and run from inside folder 2.2(13).

HOW TO COPY THE REAL MPI CODE
1. Open this file: MPIMultiplication.java.
2. Keep a backup if needed.
3. Select the code between:
   ---------------- REAL MPI CODE START ----------------
   and
   ---------------- REAL MPI CODE END ----------------
4. Copy that code.
5. Replace the full simulation code at the top of this file with that copied
   real MPI code.
6. Save the file with the same name: MPIMultiplication.java.
7. Run the compile and run commands given below.

SIMULATION ALGORITHM
1. User enters number of processors.
2. User enters one value for each simulated processor.
3. Root creates one worker thread per value.
4. Each worker prints its intermediate multiplication.
5. Root joins all workers and prints final multiplication.

SIMULATION RUN
Linux:
   javac MPIMultiplication.java
   java MPIMultiplication

Windows:
   javac MPIMultiplication.java
   java MPIMultiplication

Sample input:
   4
   1 2 3 4

Expected output:
   Final multiplication at root process: 24

REAL MPI ALGORITHM
1. MPI starts 4 real processes using mpjrun.
2. Root process creates array 1 2 3 4.
3. Scatter gives one value to each MPI process.
4. Each process keeps its received value as local multiplication.
5. Reduce with MPI.PROD multiplies all local values at root.
6. Root prints final multiplication.

---------------- REAL MPI CODE START ----------------
import mpi.*;

public class MPIMultiplication {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int root = 0;

        int[] sendBuffer = new int[size];
        int[] recvBuffer = new int[1];

        if (rank == root) {
            System.out.println("Root process created array:");
            for (int i = 0; i < size; i++) {
                sendBuffer[i] = i + 1;
                System.out.print(sendBuffer[i] + " ");
            }
            System.out.println();
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, 1, MPI.INT,
                recvBuffer, 0, 1, MPI.INT, root);

        int localResult = recvBuffer[0];
        System.out.println("Process " + rank + " received " + recvBuffer[0]
                + " and local multiplication is " + localResult);

        int[] localBuffer = { localResult };
        int[] finalResult = new int[1];

        MPI.COMM_WORLD.Reduce(localBuffer, 0,
                finalResult, 0, 1, MPI.INT, MPI.PROD, root);

        if (rank == root) {
            System.out.println("Final multiplication at root process: " + finalResult[0]);
        }

        MPI.Finalize();
    }
}
---------------- REAL MPI CODE END ----------------

LINUX MPJ EXPRESS SETUP
1. Install Java and tools:
   sudo apt update
   sudo apt install openjdk-8-jdk wget tar findutils -y

2. Download and extract MPJ Express:
   cd ~
   wget https://downloads.sourceforge.net/project/mpjexpress/mpj-v0_44.tar.gz
   tar -xzf mpj-v0_44.tar.gz

3. Set MPJ_HOME and PATH. If MPJ is installed somewhere else, change only
   this path: $HOME/mpj-v0_44
   echo 'export MPJ_HOME=$HOME/mpj-v0_44' >> ~/.bashrc
   echo 'export PATH=$MPJ_HOME/bin:$PATH' >> ~/.bashrc
   source ~/.bashrc

4. Verify:
   java -version
   javac -version
   echo $MPJ_HOME
   ls "$MPJ_HOME/lib/mpj.jar"
   which mpjrun.sh
   mpjrun.sh -help

MPJ SERVER / DAEMON PATH NOTES
1. For one laptop or one desktop, use multicore mode. No MPJ server/daemon is
   needed:
      mpjrun.sh -np 4 MPIMultiplication

2. For two machines or a lab cluster, MPJ needs daemon/server setup.
   Create a machines file in the practical folder:
      nano machines

   Example machines file:
      192.168.1.10
      192.168.1.11

3. Start MPJ daemons from Linux before cluster run:
      mpjboot machines

4. Run on cluster using niodev:
      mpjrun.sh -np 4 -dev niodev MPIMultiplication

5. Stop MPJ daemons after run:
      mpjhalt machines

6. MPJ server port settings are in:
      $MPJ_HOME/conf/mpjexpress.conf

   Important default ports:
      mpjexpress.mpjdaemon.port.1=40055
      mpjexpress.mpjdaemon.port.2=40052
      mpjexpress.mpjrun.port.1=40002

LINUX REAL MPI RUN
   cd ~/LP-V
   find . -name "MPIMultiplication.java"
   cd "2.2(13)"
   javac -cp .:$MPJ_HOME/lib/mpj.jar MPIMultiplication.java
   mpjrun.sh -np 4 MPIMultiplication

If your file is in another folder, cd to that folder first. Example:
   cd ~/LP-V/TEMP
   javac -cp .:$MPJ_HOME/lib/mpj.jar MPIMultiplication.java
   mpjrun.sh -np 4 MPIMultiplication

Command meaning:
1. find . -name "MPIMultiplication.java" searches the file location.
2. cd "2.2(13)" opens the practical folder.
3. javac compiles MPIMultiplication.java with the MPJ library.
4. mpjrun.sh -np 4 MPIMultiplication runs class MPIMultiplication with 4 real
   MPI processes.
5. MPIMultiplication in the run command is the class name, not the file name. Do
   not write MPIMultiplication.java in the mpjrun command.

WINDOWS POWERSHELL MPJ EXPRESS SETUP
1. Install JDK 8.
2. Extract MPJ Express. Example installed path:
   C:\mpj-v0_44
3. In PowerShell, set MPJ_HOME for the current terminal. If MPJ is installed
   somewhere else, change only this path:
   $env:MPJ_HOME="C:\mpj-v0_44"
4. Verify:
   java -version
   javac -version
   echo $env:MPJ_HOME
   ls "$env:MPJ_HOME\lib\mpj.jar"
   where.exe mpjrun.bat
   & "$env:MPJ_HOME\bin\mpjrun.bat" -help

WINDOWS MPJ SERVER / DAEMON PATH NOTES
1. For one laptop or one desktop, use multicore mode. No MPJ server/daemon is
   needed:
      & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 MPIMultiplication

2. For multiple Windows machines, start the daemon on every machine:
      & "$env:MPJ_HOME\bin\mpjdaemon.bat" -boot

3. Run using niodev:
      & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 -dev niodev MPIMultiplication

4. Stop the daemon on every machine:
      & "$env:MPJ_HOME\bin\mpjdaemon.bat" -halt

5. MPJ server port settings are in:
      $env:MPJ_HOME\conf\mpjexpress.conf

WINDOWS POWERSHELL REAL MPI RUN
   cd "D:\LP-V\2.2(13)"
   $env:MPJ_HOME="C:\mpj-v0_44"
   javac -cp ".;$env:MPJ_HOME\lib\mpj.jar" MPIMultiplication.java
   & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 MPIMultiplication

If your file is in another folder, cd to that folder first. Example:
   cd "D:\LP-V\TEMP"
   $env:MPJ_HOME="C:\mpj-v0_44"
   javac -cp ".;$env:MPJ_HOME\lib\mpj.jar" MPIMultiplication.java
   & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 MPIMultiplication

Optional Windows CMD version:
   cd /d D:\LP-V\2.2(13)
   set MPJ_HOME=C:\mpj-v0_44
   javac -cp ".;%MPJ_HOME%\lib\mpj.jar" MPIMultiplication.java
   "%MPJ_HOME%\bin\mpjrun.bat" -np 4 MPIMultiplication

Windows command meaning:
1. cd opens the folder where MPIMultiplication.java is saved.
2. $env:MPJ_HOME tells PowerShell where MPJ Express is installed.
3. javac compiles the file named MPIMultiplication.java with mpj.jar.
4. mpjrun.bat starts real MPI processes.
5. -np 4 means run 4 MPI processes.
6. MPIMultiplication is the public class name to run.
*/
