/*
 * primeThreadModified.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program finds the primes in range 0 - 8*MAX and stores them in a byte[] array, by stting the bit corresponding to the prime number as 1.
 * The program later uses these prime values to calculate the value of ( ( ( p_1 - p_2 ) % MAX + p_3 ) % MAX - p_4 ) % MAX ...
 * 
 * The calculation is done by multiple threads running in parallel. The sub-solution of each thread is combined to attain the values of all the primes the the given range.
 * 
 * The program is tested for better performance with different number of threads, and the best count of threads is found.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class primeThreadModified extends Thread{
    static int max = 10;
    static int primeCount = 0;
    int num = 1;
    volatile byte[] allThePrimeNumbers;                    
    static volatile ArrayList exp = new ArrayList();
    int info;
    int start;
    int end;
    CountDownLatch cd;
    
    /**
     * Constructor which initializes the data values.
     * 
     * @param i      id of thread
     * @param start  start point of each thread
     * @param end    end point of each thread
     * @param cdl    count down latch
     */
    public primeThreadModified(int i,int start,int end, CountDownLatch cd1) {
        this.info = i;
        this.cd = cd1;
        this.start = start;
        this.end = end;
        if(this.start==0)
            this.num = 1;
        else
            this.num = this.start*8;
        allThePrimeNumbers = new byte[end-start+1];
    }
    
    /**
     * Resets all the parameters to their initial value.
     * This method is called every-time before we Test with a different count of threads.
     */
    public static void reset(){
        exp.clear();
    }
    
    
    /**
     * Checks whether a given number is prime or not
     * 
     * @param num     number to check if prime
     * @return        true if prime and false otherwise
     */
   synchronized public static boolean checkPrime(int num){
        int count=0;
        for(int i=1;i<=num/2;i++){
        if(num%i==0)
         count++;
        }
        if(num==1)
            return false;
        if(count==1)
            return true;
        else
            return false;
    }
    
    
   /**
    * calculates the exponents of 2
    * 
    * @param i     exponent value
    * @return      2^i value
    */
    synchronized public static int twoPower(int i){
        if(i==0)
            return 1;
        else
            return 2*twoPower(i-1);
    }
    
    
    /**
     * Calculates the  value of the expression - ( ( ( p_1 - p_2 ) % MAX + p_3 ) % MAX - p_4 ) % MAX ... Recursively.
     * 
     * @param a          object of ArrayList
     * @param depth      depth of recursion
     * @param max        global MAX
     * @param sum        expression value at each depth.
     * @return 
     */
    public static double recursiveCal(ArrayList a,int depth,int max,int sum){
     try{
        int x,y,z;
        if(a.isEmpty())                                // base case
            return sum;
        if(depth==1){                                  // for depth 1
            x = (int)a.remove(0);
            y = (int)a.remove(0);
            sum = ((x-y)%max);
            //System.out.println(sum);                   //--remove these to trace calculations of the expression
            return recursiveCal(a, depth+1, max, sum);
        }
        else
        if(depth%2==0){
            z=(int)a.remove(0);
            sum = ((sum+z)%max);
            //System.out.println(sum);                   //--remove these to trace calculations of the expression
            return recursiveCal(a, depth+1, max, sum);
        }
        else{
            z=(int)a.remove(0);
            sum = ((sum-z)%max);
            //System.out.println(sum);
            return recursiveCal(a, depth+1, max, sum);
        }
    
    }
    catch(Exception e){
        System.out.println("Oops Something went wrong...try again!\n"+e);
    }
     return 0;
}   
    
    /**
     * Reads the prime numbers from byte[] array and adds them to ArrayList.
     */
    public void buildArray(){
        int p = start;
        for(int j=0;j<(end-start);j++){
            for(int i=0;i<8;i++){
                byte a = (byte)((allThePrimeNumbers[j] >> i) & 1);
                if(a==1){
                    exp.add((int)(8*p)+(i+1));
                }
            }
            p++;
        }
    }
    
    
    /**
     * Stores all the prime numbers in a bit array, by setting the bits at their designated positions as 1 and the rest as 0.
     * 
     * Example-
     * Bit 2 in allThePrimeNumbers should have the value 1, bit 3 in allThePrimeNumbers should have the value 1,
     * bit 4 in allThePrimeNumbers should have the value 0, and so on.
     * 
     * This constructions is done by different thread parallelly, and each thread operates in a different range (start - end)
     * 
     */
    public synchronized void buildBit(){
      //try{
          if(isInterrupted()){
          }
       try{
        int p = start;
        for(int j=0;j<(end-start);j++){
            for(int i=0;i<8;i++){
                if(checkPrime((int)(8*p)+(i+1))){                                                   // checks if number is prime
                    primeCount++;
                    allThePrimeNumbers[j] = (byte) (allThePrimeNumbers[j] + (byte)twoPower(i));      // sets the bit as one if number is prime
                }
                //System.out.println("BYTE:  "+ Integer.toBinaryString(allThePrimeNumbers[j]));
                num++;
            }
            p++;
          //  System.err.println(primeCount);
         //System.out.println("BYTE:  "+ Integer.toBinaryString(allThePrimeNumbers[j]));
        }
       }
       catch(Exception ee){
           System.out.println(""+ee+"\nOOps! something went wrong...try again");
       }
    }
    
    /**
     * displays the BYTE Array value calculated by each thread.
     */
    public void display(){
    for(int j=0;j<(end-start);j++){
         System.out.println("BYTE:  "+ Integer.toBinaryString(allThePrimeNumbers[j]));
        }
    
    }
    
    /**
     * Every thread executes the run method once it starts.
     */
    @Override
   synchronized public void run(){
        buildBit();
        cd.countDown();
    
    }
    
   /**
    * Tests the logic for different number of threads. and the best solution among them is displayed.
    * 
    * @param args
    * @throws InterruptedException 
    */
    public static void main(String[] args) throws InterruptedException {
        int cores = 1;
        long Mintime = 1000;
        int minCore = 1;
        String min = "";
        for(cores = 0;cores<=10;cores+=2){
            //reset();
            long threadTime;
            
            if(cores == 6||cores == 0)
                continue;
            long ms = System.currentTimeMillis();

            CountDownLatch cd1 = new CountDownLatch(cores);

            ExecutorService exec = Executors.newFixedThreadPool(cores);

            primeThreadModified pt[] = new primeThreadModified[cores+1];

                int ini = 0;
                int step = max/cores;
                int fin = step;

                for(int i=0;i<cores;i++){
                    pt[i] = new primeThreadModified(i,ini,fin,cd1);
                    //System.out.println("Thread "+(i+1)+" executes in interval : "+ini+" - "+fin);
                    ini = ini + step;
                    fin = fin+step;
                }
                for(int j=0;j<cores;j++){
                    exec.execute(pt[j]);
                }
                            //display();
                exec.shutdown();
                cd1.await();
                 for(int i=0;i<cores;i++){
                        pt[i].buildArray();
                    }
                if(cores==2){
                    for(int i=0;i<cores;i++){
                        pt[i].display();
                    }
                 System.out.println("Primes - "+exp.toString());
                 System.out.printf("%-65s %s","\t\tNo ofThreads","Time\n");
                }
            try{
                Collections.sort(exp);
            }
            catch(Exception ex){
                System.out.println(ex);
            }
            // System.out.println("Sorted - "+exp.toString());
            System.out.print("With "+cores+" Threads - ");
            System.out.print("Expression value = "+recursiveCal(exp, 1,max, 0));
            System.out.println("\t\t\t\t\t"+(System.currentTimeMillis()-ms));
            threadTime = (System.currentTimeMillis()-ms);
            if(threadTime <= Mintime){
                minCore = cores;
                Mintime = threadTime;
                min = "With "+cores+" Threads ";
            }
        }
        System.out.println("\nWe encounter most efficient solution - ");
        System.out.println(""+min+" With Execution time of "+Mintime+"ms\n" );
        
        int core = Runtime.getRuntime().availableProcessors();
        if(minCore == core){
            System.out.println("System Core count: "+core);
            System.out.println("Theoretical Best performance achieved! \nThread count = core count");
        }
        else{
            System.out.println("System Core count: "+core);
            System.out.println("This program gives better performance with more threads!");
        }
    }
}
