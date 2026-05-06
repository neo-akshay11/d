import java.util.Random;
import java.util.Scanner;

public class MPIAverage {
    public static void main(String[] args) throws InterruptedException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter number of processors: ");
            int processors = sc.nextInt();

            System.out.print("Enter elements per processor: ");
            int elementsPerProcessor = sc.nextInt();

            int totalElements = processors * elementsPerProcessor;
            int[] numbers = new int[totalElements];
            Random random = new Random(10);

            System.out.println("Random array generated at root process:");
            for (int i = 0; i < totalElements; i++) {
                numbers[i] = random.nextInt(50) + 1;
                System.out.print(numbers[i] + " ");
            }
            System.out.println();

            Worker[] workers = new Worker[processors];

            System.out.println("Root process scattering equal parts to workers...");
            for (int i = 0; i < processors; i++) {
                int[] part = new int[elementsPerProcessor];
                for (int j = 0; j < elementsPerProcessor; j++) {
                    part[j] = numbers[(i * elementsPerProcessor) + j];
                }

                workers[i] = new Worker(i, part);
                workers[i].start();
            }

            double averageSum = 0;
            for (int i = 0; i < processors; i++) {
                workers[i].join();
                averageSum = averageSum + workers[i].getAverage();
            }

            double finalAverage = averageSum / processors;
            System.out.println("Final average at root process: " + finalAverage);
        }
    }

    static class Worker extends Thread {
        private final int processId;
        private final int[] values;
        private double average;

        Worker(int processId, int[] values) {
            this.processId = processId;
            this.values = values;
        }

        @Override
        public void run() {
            int sum = 0;
            for (int value : values) {
                sum = sum + value;
            }

            average = (double) sum / values.length;
            System.out.println("Process " + processId + " computed local average: " + average);
        }

        double getAverage() {
            return average;
        }
    }
}

/*
PRACTICAL 2.3 - MPI AVERAGE

This file keeps the Java thread simulation above for normal classroom running.
The real MPI / MPJ Express version is kept below as a reference. To run real
MPI, copy only the REAL MPI CODE block into MPIAverage.java, replacing the
simulation code above.

IMPORTANT FILE NAME RULE
1. The public class name is MPIAverage.
2. Java requires the file name to be exactly MPIAverage.java.
3. Do not rename it to mpi.java, average.java, MPIAvg.java, or any other name.
4. Compile and run from inside folder 2.3(14).

HOW TO COPY THE REAL MPI CODE
1. Open this file: MPIAverage.java.
2. Keep a backup if needed.
3. Select the code between:
   ---------------- REAL MPI CODE START ----------------
   and
   ---------------- REAL MPI CODE END ----------------
4. Copy that code.
5. Replace the full simulation code at the top of this file with that copied
   real MPI code.
6. Save the file with the same name: MPIAverage.java.
7. Run the compile and run commands given below.

SIMULATION ALGORITHM
1. User enters number of processors.
2. User enters elements per processor.
3. Root creates a random array.
4. Root gives equal parts of the array to worker threads.
5. Each worker computes a local average.
6. Root averages all local averages and prints final average.

SIMULATION RUN
Linux:
   javac MPIAverage.java
   java MPIAverage

Windows:
   javac MPIAverage.java
   java MPIAverage

Sample input:
   4
   2

Expected output:
   Final average at root process: 33.875

REAL MPI ALGORITHM
1. MPI starts 4 real processes using mpjrun.
2. Root creates 8 random numbers when each process receives 2 numbers.
3. Scatter gives 2 numbers to each MPI process.
4. Each process computes its local average.
5. Gather sends all local averages to root.
6. Root averages those local averages and prints final average.

---------------- REAL MPI CODE START ----------------
import java.util.Random;
import mpi.*;

public class MPIAverage {
    private static final int ELEMENTS_PER_PROCESS = 2;

    public static void main(String[] args) throws Exception {
        MPI.Init(args);

        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int root = 0;
        int totalElements = size * ELEMENTS_PER_PROCESS;

        int[] sendBuffer = new int[totalElements];
        int[] recvBuffer = new int[ELEMENTS_PER_PROCESS];

        if (rank == root) {
            Random random = new Random(10);
            System.out.println("Root process created random array:");
            for (int i = 0; i < totalElements; i++) {
                sendBuffer[i] = random.nextInt(50) + 1;
                System.out.print(sendBuffer[i] + " ");
            }
            System.out.println();
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, ELEMENTS_PER_PROCESS, MPI.INT,
                recvBuffer, 0, ELEMENTS_PER_PROCESS, MPI.INT, root);

        int localSum = 0;
        for (int value : recvBuffer) {
            localSum = localSum + value;
        }

        double localResult = (double) localSum / ELEMENTS_PER_PROCESS;
        System.out.println("Process " + rank + " local average is " + localResult);

        double[] localBuffer = { localResult };
        double[] gatheredResults = new double[size];

        MPI.COMM_WORLD.Gather(localBuffer, 0, 1, MPI.DOUBLE,
                gatheredResults, 0, 1, MPI.DOUBLE, root);

        if (rank == root) {
            double sumOfAverages = 0.0;
            for (double average : gatheredResults) {
                sumOfAverages = sumOfAverages + average;
            }

            double finalResult = sumOfAverages / size;
            System.out.println("Final average at root process: " + finalResult);
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
      mpjrun.sh -np 4 MPIAverage

2. For two machines or a lab cluster, MPJ needs daemon/server setup.
   Create a machines file in the practical folder:
      nano machines

   Example machines file:
      192.168.1.10
      192.168.1.11

3. Start MPJ daemons from Linux before cluster run:
      mpjboot machines

4. Run on cluster using niodev:
      mpjrun.sh -np 4 -dev niodev MPIAverage

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
   find . -name "MPIAverage.java"
   cd "2.3(14)"
   javac -cp .:$MPJ_HOME/lib/mpj.jar MPIAverage.java
   mpjrun.sh -np 4 MPIAverage

If your file is in another folder, cd to that folder first. Example:
   cd ~/LP-V/TEMP
   javac -cp .:$MPJ_HOME/lib/mpj.jar MPIAverage.java
   mpjrun.sh -np 4 MPIAverage

Command meaning:
1. find . -name "MPIAverage.java" searches the file location.
2. cd "2.3(14)" opens the practical folder.
3. javac compiles MPIAverage.java with the MPJ library.
4. mpjrun.sh -np 4 MPIAverage runs class MPIAverage with 4 real MPI processes.
5. MPIAverage in the run command is the class name, not the file name. Do not
   write MPIAverage.java in the mpjrun command.

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
      & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 MPIAverage

2. For multiple Windows machines, start the daemon on every machine:
      & "$env:MPJ_HOME\bin\mpjdaemon.bat" -boot

3. Run using niodev:
      & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 -dev niodev MPIAverage

4. Stop the daemon on every machine:
      & "$env:MPJ_HOME\bin\mpjdaemon.bat" -halt

5. MPJ server port settings are in:
      $env:MPJ_HOME\conf\mpjexpress.conf

WINDOWS POWERSHELL REAL MPI RUN
   cd "D:\LP-V\2.3(14)"
   $env:MPJ_HOME="C:\mpj-v0_44"
   javac -cp ".;$env:MPJ_HOME\lib\mpj.jar" MPIAverage.java
   & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 MPIAverage

If your file is in another folder, cd to that folder first. Example:
   cd "D:\LP-V\TEMP"
   $env:MPJ_HOME="C:\mpj-v0_44"
   javac -cp ".;$env:MPJ_HOME\lib\mpj.jar" MPIAverage.java
   & "$env:MPJ_HOME\bin\mpjrun.bat" -np 4 MPIAverage

Optional Windows CMD version:
   cd /d D:\LP-V\2.3(14)
   set MPJ_HOME=C:\mpj-v0_44
   javac -cp ".;%MPJ_HOME%\lib\mpj.jar" MPIAverage.java
   "%MPJ_HOME%\bin\mpjrun.bat" -np 4 MPIAverage

Windows command meaning:
1. cd opens the folder where MPIAverage.java is saved.
2. $env:MPJ_HOME tells PowerShell where MPJ Express is installed.
3. javac compiles the file named MPIAverage.java with mpj.jar.
4. mpjrun.bat starts real MPI processes.
5. -np 4 means run 4 MPI processes.
6. MPIAverage is the public class name to run.
*/
