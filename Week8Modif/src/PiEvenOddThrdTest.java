/*
 * PiEvenOddThrd.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This Program counts the number of even and odd digits of pi from a given text file.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class PiEvenOddThrdTest extends Thread {
    
    int even = 0;
    int odd = 0;
    private BufferedInputStream br;
    private  static File f = new File("data.txt");
    private long fileLength = f.length();
    int info;
    CountDownLatch cd;
    
    /**
     * This method is used to provide the input stream and countdownlatch for the threads that will execute
     * 
     * @param i
     * @param bis
     * @param cd
     * @throws FileNotFoundException 
     */
    public PiEvenOddThrdTest(int i,BufferedInputStream bis, CountDownLatch cd )throws FileNotFoundException{
        this.info=i;
        this.br = bis;
        this.cd = cd;
    }
     
    
    // this function not used
    public void countEven(){
        even++;
    }
    
    //this function not used
    public void countOdd(){
        odd++;
    }
    
    /*public static void display(PiEvenOddThrdTest[] pi){
        double oddC=0,evenC=0;
        for (int i = 0; i < pi.length; i++) {
            oddC = oddC + pi[i].odd;
            evenC= evenC +pi[i].even;
        }
        System.out.println("Odd: " +oddC);
        System.out.println("Even: "+ evenC);
        System.out.println("Even/Odd: "+(double)(oddC/evenC));
        System.out.println("File Length: "+ f.length());
    }*/
    
    /**
     * This method executes when a thread is started and counts the no of odd and even digits in the file
     */
    @Override
     synchronized public void run(){
        long ms = System.currentTimeMillis();
        try{
        ms = System.currentTimeMillis();                                 //buffer size of 4000
        int i=0;    
        char a = '.';
        while((i=br.read())!=-1){
            if((char)i==' '||(char)i=='.'||(char)i=='\n')                 
                continue;
            if(i%2==0)
                even++;//countEven();
            else
                odd++;//countOdd();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        cd.countDown();

    }
    
   /**
    * Reads the files and counts the number of even and odd digits of pi.
    *
    * @param  args command line arguments
    */
    public static void main(String[] args) {
        int n = 1;
        long Mintime = 10000;
        int minCore = 1;
        String min = "";
        for(n=1;n<10;n++){
            long threadTime;
            long ms = System.currentTimeMillis();
            try{
             BufferedInputStream br = new BufferedInputStream(new FileInputStream(f),40);
             CountDownLatch cdl = new CountDownLatch(n);

                ExecutorService exec = Executors.newFixedThreadPool(n);
                PiEvenOddThrdTest thrt[]= new PiEvenOddThrdTest[n+1];

                for(int i=0;i<n;i++){
                    thrt[i] = new PiEvenOddThrdTest(i,br,cdl);
            }
                for(int j=0;j<n;j++){
                    exec.execute(thrt[j]);
                }
                            //display();
                exec.shutdown();
                cdl.await();
            double oddC=0,evenC=0;
            for (int i = 0; i < n; i++) {
                oddC = oddC + thrt[i].odd;
                evenC= evenC + thrt[i].even;
            }
            if(n==1){
                System.out.println("Odd: " +oddC);
                System.out.println("Even: "+ evenC);
                System.out.println("Odd/Even: "+(double)(oddC/evenC));
                System.out.println("File Length: "+ f.length());
                    //display(thrt);
            }
            System.out.print("With "+n+" Threads - ");
            System.out.println("\t\t\t\t\t"+(System.currentTimeMillis()-ms));
            threadTime = (System.currentTimeMillis()-ms);
            if(threadTime <= Mintime){
                minCore = n;
                Mintime = threadTime;
                min = "With "+n+" Threads ";
            }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        System.out.println("We encounter most efficient solution - ");
        System.out.println(""+min+" With Execution time of "+Mintime+"ms\n\n" );
        int cores = Runtime.getRuntime().availableProcessors();
        if(minCore == cores){
            System.out.println("System Core count: "+cores);
            System.out.println("Theoretical Best performance achieved! \nThread count = core count");
        }
        else{
            System.out.println("System Core count: "+cores);
            System.out.println("Since the program involves file i/o using multiple threads, we cannot see much speed\nimprovements as, creating threads has its own overhead");
        }
    }
    
}
