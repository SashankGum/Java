/*
 * countHelper.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 * Contains the main logic of the even and odd counting.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */

 public class countHelper extends UnicastRemoteObject implements RMIInterfacetesting{
    long even = 0;
    long odd = 0;
   /**
    * Calls the constructors of the UnicastRemoteObject
    * 
    * @throws RemoteException 
    */
   countHelper() throws RemoteException{
        super();
    }
    
   /**
    * Counts the total even and odd count of elements in the buffer.
    * 
    * @param buffer  Character array 
    * @param l       length of the character array
    * @return 
    */
    public int reading(char[] buffer,int l){
            for ( int index = 0; index < l; index ++ )   {
            if ( ( '0' <= buffer[index] ) && ( buffer[index] <= '9' ) )   {
                    if ( buffer[index] % 2 == 0 ){
                            even++;
                    }
                    else{
                            odd++;
                    }
            }
    }
     
         return 0;
     }
    
    /**
     * resets the even and odd count.
     */
    public void reset(){
        even = 0;
        odd = 0;
    }
    
    /**
     * returns even count
     * 
     * @return Even value
     */
    public long getEven(){
        System.out.println("Even "+even);
        return even;
    }
    
    /**
     * returns odd count
     * 
     * @return Odd value
     */
    public long getOdd(){
        System.out.println("Odd - "+odd);
        return odd;
    }
}
