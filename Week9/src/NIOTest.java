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
public class NIOTest extends Thread {
    
    int even = 0;
    int odd = 0;
    static final Integer inn = new Integer(1);
    private  static String fileName = "data.txt";
    BufferedReader input ;
    int MAX = 1024;
    char [] buf = new char[MAX];
    int info;
    CountDownLatch cd;

    public NIOTest() throws FileNotFoundException{
        try{
        this.input = new BufferedReader(new InputStreamReader (new FileInputStream(fileName)) );
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    

      private int nextBlock() throws IOException  {
        return input.read(buf, 0, buf.length); // this is the marked line
    }
    public void countEvenOdd() throws IOException  {
        long ms = System.currentTimeMillis();
        long even = 0;
        long odd  = 0;
        int soManyCharactersRead;
        try     {
                while (  ( soManyCharactersRead = nextBlock() ) != -1 ) {
                    //System.out.println("After Read");
                        for ( int index = 0; index < soManyCharactersRead; index ++ )   {
                                if ( ( '0' <= buf[index] ) && ( buf[index] <= '9' ) )   {
                                        if ( buf[index] % 2 == 0 ){
                                                even++;
                                                //System.out.println("Even");
                                        }
                                        else{
                                                //System.out.println("Out");
                                                odd++;
                                        }
                                }
                        }
                }
        }
        catch(Exception ee){
            System.out.println(ee);
        }
        System.out.println(even+"---"+odd);
    }
   /**
    * Reads the files and counts the number of even and odd digits of pi.
    *
    * @param  args command line arguments
    */
    public static void main(String[] args) {
            long ms = System.currentTimeMillis();
            try{
                NIOTest nio = new NIOTest();
                nio.countEvenOdd();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        System.out.println("Time Taken"+(System.currentTimeMillis()-ms));
    }
    
}
