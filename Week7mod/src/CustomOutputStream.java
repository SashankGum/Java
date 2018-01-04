/*
 * CustomOutputStream.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

/**
 * A custom class which calculates and updates the Hashvalue of the file, whenever data is written into the file.
 * It uses MessageDigest to calculate and update the HashValue.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class CustomOutputStream extends FilterOutputStream{
    private MessageDigest md;

    /**
     * This constructor calls the constructor of super class and initializes the MessageDigest.
     * 
     * @param out OutputStream object referring to a file
     * @param md  MessageDigest Instance
     */
    public CustomOutputStream(OutputStream out, MessageDigest md) {
        super(out);
        this.md = md;
    }
    
    /**
     * Writes a byte of data to the file and updates the MessageDigest.
     * 
     * @param byt byte of data to be written.
     * @throws IOException 
     */
    @Override
    public void write(int byt) throws IOException{
        out.write(byt);
        md.update((byte)byt);
    }
    
    /**
     * Writes array of bytes[] to the file and updates the MessageDigest.
     * 
     * @param byt array of byte[] to be written.
     * @throws IOException 
     */
    @Override
    public void write(byte[] byt)throws IOException{
        out.write(byt);
        md.update(byt);
    }
    
    /**
     * Writes length bytes from the specified byte array starting at offset off to this output stream and updates the MessageDigest.
     * 
     * @param byt byte of data to be written.
     * @param off offset
     * @param length length of bytes
     * @throws IOException 
     */
    @Override
    public void write(byte[] byt, int off, int length) throws IOException{
            out.write(byt, off, length);
             md.update(byt, off, length);
        
    }
    
}
