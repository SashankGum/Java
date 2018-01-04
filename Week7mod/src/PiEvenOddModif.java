/*
 * PiEvenOddModif.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.BufferedInputStream;
import java.io.FileInputStream;
/**
 * This Program counts the number of even and odd digits of pi from a given text file.
 * It improves the execution time of previous program by using buffer.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */

public class PiEvenOddModif {
   /**
    * The method uses BufferedInputStream with a buffer size of 4000 to improve the performance compared to using FileReader for input.
    *
    * @param    args  command  line argument's
    */
    public static void main(String[] args) {
        int even=0,odd=0;
        long ms = 0;
        try{
        ms = System.currentTimeMillis();
        BufferedInputStream br = new BufferedInputStream(new FileInputStream("data.txt"),4000);   //buffer size of 4000
        int i=0;
        char a = '.';
        while((i=br.read())!=((int)a)&&i!=-1);                                      //to take the file pointer till the first decimal place.
        while((i=br.read())!=-1){
            if((char)i==' '||(char)i=='.'||(char)i=='\n')                  //to ignore '\n',' ' and '.'
                continue;
            if(i%2==0)
                even++;
            else
                odd++;
        }
        br.close();                                                        // close file
        System.out.println("Even: "+even+"\nOdd: "+odd);       
        System.out.println((even+odd));
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println("time taken - "+(System.currentTimeMillis()-ms)); //total execution time
    }
}
