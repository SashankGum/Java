/*
 * IntegrationThreads.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program calculates the approximate value of the the volume under the function x^2 + y^2 by taking its upper sum and lower sum
 * where x and y both range from -4 to 4. 
 * 
 * The calculation is done by multiple threads running in parallel. The sub-solution of each thread is combined to attain the final approximation.
 * 
 * The program is tested for better performance with different number of threads, and the best count of threads is found.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class IntegrationThreads extends Thread{
    
    static double sum = 0;                            // initial sum
    static double xcor=-4,ycor=-4;                    // initial points of x and y
    static double yreset = ycor;
    static double n = 8;                              // range length
    static double divide=2;                           // initial number of divisions
    static double deltaArea = (n/divide)*(n/divide);  // difference between yellow and green area.
    static int calculate_flag = 0;
    CountDownLatch cdl;
    double info;
    double start,end;                                 // start and end points --- these are different for differnt threads
    

    /**
     * Constructor which initializes the data values.
     * 
     * @param i      id of thread
     * @param start  start point of each thread
     * @param end    end point of each thread
     * @param cdl    count down latch
     */
    public IntegrationThreads(int i,double start,double end, CountDownLatch cdl) {
        this.info = i;
        this.start = start;
        this.end = end;
        this.cdl = cdl;
    }
    
    /**
     * Resets all the parameters to their initial value.
     * This method is called every-time before we Test with a different count of threads.
     */
    public static void reset() {
        sum = 0;
        xcor=-4;
        ycor=-4;
        yreset = ycor;
        n = 8;
        divide=2;
        deltaArea = (n/divide)*(n/divide);
        calculate_flag = 0;
       
    }
    
    /**
     * Calculates the value of function x^2 = y^2, for a given value of x = a and y = b
     * 
     * @param a       value of x
     * @param b       value of y
     * @return        value of x^2 + y^2
     */
    public static double f(double a,double b){
        return (a*a)+(b*b);
    }
    
    /**
     * Displays the final calculated approximation of the volume.
     */
    public static void display(){
        System.out.print("Final Apprximation -" + sum);
    }
    
    /**
     * This method divides the co-ordinate plane 'divide' times. and calls the f (x^2+y^2) method for the top right x,y points 
     * of each sub division.
     * 
     * This methhod is run in parallel by different threads at a single time and each thread operates on a different range of sub-divisions
     * specified by start and end.
     * 
     * @param start   start of a sub-division
     * @param end     end of a sub-division
     * @param name    name of thread which is executing the current calculate      
     */
    synchronized public static void calculate(double start,double end,String name){
            double i=0,j=0;
            double sum1=0;
            for(i=start;i<end;i++){  // i<divide
                xcor = xcor +(n/divide);            // calculates the x co-ordinate for each subdivison
                ycor = yreset;     // -4            // resets the y co-ordinate
                for(j=0;j<divide;j++){     //j<divide
                    ycor = ycor + (n/divide);       // calculates the y co-ordinates for each subdivision.
                    //System.out.println(f( "+xcor+" , "+(ycor)+" )*"+(deltaArea)+" = " + (deltaArea)*f(xcor,ycor));
                    sum1 = sum1 + deltaArea*f(xcor,ycor);         // sum1 is the sub solution of each thread.
                }
            }
            sum = sum +sum1;                       // sub-solution of each thread is added to final soluton
            //System.out.println("Sum computed by "+name+" is: "+sum1+"\n current sum = "+sum);
    }
    
    /**
     * Calculates the difference between green area and yellow area. and continues to divide the graph until the difference is 
     * less than 0.0001
     */
    public static void calDelta(){
            while(deltaArea>=0.0001){
            divide = divide*2; 
            deltaArea = (n/divide)*(n/divide);
        }
    }
    
    /**
     * Each thread executes the run method
     */
    @Override
    public  void run(){
        calculate(start, end,getName());
        cdl.countDown();
    }
    
    /**
     * Tests the logic for different number of threads. and the best solution among them is displayed.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        int x = 1;
        int minCore = 1;
        long Mintime = 1000;
        String min = "";
        for(x=1;x<=16;x*=2){
            long threadTime = 0;
            reset();
            long ms = System.currentTimeMillis();
            try{
                calDelta();
                double ini = 0;
                double step = divide/x;
                double fin = step;
                CountDownLatch cdl = new CountDownLatch(x);

                ExecutorService exec = Executors.newFixedThreadPool(x);

                IntegrationThreads in[]=new IntegrationThreads[x+1];
                for(int i=0;i<x;i++){
                    in[i] = new IntegrationThreads(i,ini,fin,cdl);
                    ini = ini + step;
                    fin = fin+step;
                }
                for(int j=0;j<x;j++){
                    exec.execute(in[j]);
                }
                            //display();
                exec.shutdown();
                cdl.await();
                System.out.print("With "+x+" Threads - ");
                display();

            }
            catch(Exception e){
                System.out.println(e);
            }
            threadTime = (System.currentTimeMillis()-ms);
            if(threadTime <= Mintime){
                minCore= x;
                Mintime = threadTime;
                min = "With "+x+" Threads ";
            }
            String str = ""+(System.currentTimeMillis()-ms);
            System.out.printf("%-30s %s","\t\ttime taken - ",str+"\n\n");
        }
        System.out.println("We encounter most efficient solution - ");
        System.out.println(""+min+" With Execution time of "+Mintime+"ms\n\n" );
        
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
