/*
 * PiEvenOdd.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.*;
/**
 * This Program counts the number of even and odd digits of pi from a given text file.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class PiEvenOdd {
   /**
    * Reads the files and counts the number of even and odd digits of pi.
    *
    * @param  args command line arguments
    */
    public static void main(String[] args) {
        int even=0,odd=0;
        long ms = 0;
        try{
        ms = System.currentTimeMillis();
        FileReader fr = new FileReader("data.txt");
        int i=0;
        char a = '.';
        while((i=fr.read())!=(int)a);                                      //to take the file pointer till the first decimal place.
        while((i=fr.read())!=-1){
            if((char)i==' '||(char)i=='.'||(char)i=='\n')                  //to ignore '\n',' ' and '.'
                continue;
            if(i%2==0)
                even++;
            else
                odd++;
        }
        fr.close();                                                         // close file 
        System.out.println("Even: "+even+"\nOdd: "+odd);
        System.out.println((even+odd));
        }
        catch(Exception e){
            System.out.println(e);
        }
        System.out.println("time taken - "+(System.currentTimeMillis()-ms));  //total execution time
    }
    
}
