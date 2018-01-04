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
public class NBEvenOdd extends Thread {
    
    int even = 0;
    int odd = 0;
    static final Integer inn = new Integer(1);
    private CustomInputStr br;
    private static final File f = new File("data.txt");
    private long fileLength = f.length();
    int info;
    CountDownLatch cd;
    
    /**
     * This method is used to provide the inputstream and countdownlatch for the threads that will execute
     * 
     * @param i             id of thread
     * @param bis           custom input stream object
     * @param cd            countdownlatch object
     * @throws FileNotFoundException 
     */
    public NBEvenOdd(int i,CustomInputStr bis, CountDownLatch cd )throws FileNotFoundException{
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
    
    /*public static void display(NBEvenOdd[] pi){
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
        //System.out.println(getState());
       // Thread Thr = new Thread();
        //synchronized(inn){
        while((i=br.read())!=-1){
            //System.out.println(i);
           // System.out.println(getName());
            //notifyAll();
            if((char)i==' '||(char)i=='.'||(char)i=='\n')                 
                continue;
            if(i%2==0){
                even++;//countEven();
                //notify();
                //wait();
            }
            else{
                odd++;//countOdd();
                //notify();
                //wait();
            }
        }
      //  }
        }
        catch(Exception e){
            e.printStackTrace();
        }
       // System.out.println("------------------------Thread - "+getName()+" Ending ---------------------");
        cd.countDown();

    }
    
   /**
    * Reads the files and counts the number of even and odd digits of pi.
    *
    * @param  args command line arguments
    */
    public static void main(String[] args) {
        int n = 1;    
            long ms = System.currentTimeMillis();
            try{
             CustomInputStr br = new CustomInputStr(new FileInputStream(f),n,4000);
             CountDownLatch cdl = new CountDownLatch(n);

                ExecutorService exec = Executors.newFixedThreadPool(n);
                NBEvenOdd thrt[]= new NBEvenOdd[n+1];

                for(int i=0;i<n;i++){
                    thrt[i] = new NBEvenOdd(i,br,cdl);
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

                System.out.println("Odd: " +oddC);
                System.out.println("Even: "+ evenC);
                System.out.println("Odd/Even: "+(double)(oddC/evenC));
                System.out.println("File Length: "+ f.length());
                    //display(thrt);

            System.out.print("Time taken : ");
            System.out.println(" "+(System.currentTimeMillis()-ms));
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }
    
}
