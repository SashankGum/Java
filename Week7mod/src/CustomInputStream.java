/*
 * CustomInputStream.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
/**
 * A custom class which calculates and updates the Hashvalue, whenever data is read from the file.
 * It uses MessageDigest to calculate and update the HashValue.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class CustomInputStream extends FilterInputStream{
    private MessageDigest md;
    
    /**
     * This constructor calls the constructor of super class and initializes the MessageDigest.
     * 
     * @param io OutputStream object referring to a file
     * @param md  MessageDigest Instance
     */
    public CustomInputStream (InputStream io,MessageDigest md){
        super(io);                                  //calls FillterInputStream constructor
        this.md = md;
    } 

    /**
     * Reads a byte of data from the file and updates the MessageDigest
     * 
     * @return          the next byte of data, or -1 if the end of the stream is reached.
     */
    @Override
    public int read(){
        int byt = 0;
        try{
        byt = in.read();
        if(byt!=-1){
            md.update((byte)byt);
        }
        }
        catch(IOException e){
            System.out.println(e);
        }
        return byt;
    }
    
    /**
     * Reads an array of bytes from the file and updates the MessageDigest.
     * 
     * @param byt
     * @return         the total number of bytes read into the buffer, or -1 if end of file is reached
     */
    @Override
    public int read(byte byt[]){
        int length = 0;
        try{
            length = in.read(byt);
            if(length!=-1){
                md.update(byt);
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        return length;
    }
    
    /**
     * Reads up to length bytes of data from the input stream and updates the MessageDigest
     * 
     * @param byt  buffer to read data into
     * @param off   offset
     * @param length    maximum number of bytes to read
     * @return          the total number of bytes read into the buffer, or -1 if end of the stream has been reached.
     */
    @Override
    public int read(byte byt[],int off,int length){
        int l;
        try{
            l = in.read(byt,off, length);
            if(l!=-1){
                md.update(byt, off, length);
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        return 0;
    }
    
}
