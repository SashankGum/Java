/*
 * CustomStreamCheck.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 * Implements test sequence which copies a data from one file to another using the Custom Streams.
 * The HashCode is calculated of both the files and checked. 
 * If the HashCode Matches, files are copied without any defect.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */


public class CustomStreamCheck {
    
   /**
    * Main Program
    *
    * @param    args  command  line argument's
    */
    public static void main(String[] args) throws NoSuchAlgorithmException,HashCodeException {
        CustomInputStream inStream = null;
        CustomOutputStream outStream = null;
        MessageDigest inmd = MessageDigest.getInstance("MD5");               // uses MD5 hashing algorithm
        MessageDigest outmd = MessageDigest.getInstance("MD5");              // used MD5 Hasing algorithm
        try{
            inStream = new CustomInputStream(new FileInputStream("inStream.txt"), inmd);
            outStream = new CustomOutputStream(new FileOutputStream("outStream.txt"),outmd);
            int value=0;
            while((value=inStream.read())!=-1){
                outStream.write(value);
            }
        }
        catch(Exception e){
            System.out.println(e);
            System.exit(0);
        }
        String inHash = new BASE64Encoder().encode(inmd.digest());           //Converts byte[] to a Base64 String
        String outHash = new BASE64Encoder().encode(outmd.digest());         //Converts byte[] to a Base64 String
        System.out.println("input Hash: "+inHash+"\noutput Hash: "+outHash);
        if(inHash.equals(outHash))
            System.out.println("Hashcode retrived...File copied successfully!");
        else
            throw new HashCodeException();                                  // throws HashCodeException if hashcodes of both files are not equal.
    }
}
