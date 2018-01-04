/*
 * RMIInterfacetesting.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This remote interface has  remote method declarations used to show the communication between client and server
 * 
 * @author Rajkumar Lenin Pillai
 * @author Sashank Gummuluri
 */
public interface RMIInterfacetesting extends Remote {
    /**
     * Resets the values of even and odd
     */
    public void reset() throws RemoteException;
    
    /**
     * Reads a character array and counts the even and off numbers from it.
     */
    public int reading(char[] buf,int l) throws RemoteException;                 // To perform reading from file
    
    /**
     * Returns the even count
     */
    public long getEven() throws RemoteException;
    
    /**
     * returns the odd count
     */
    public long getOdd() throws RemoteException;

}