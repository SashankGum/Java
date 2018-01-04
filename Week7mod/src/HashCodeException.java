/*
 * HashCodeException.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */


/**
 * Defines a custom exception called HashCodeException
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class HashCodeException extends Exception{
    /**
     * calls the constructor of Exception class when ever this constructor is called.
     */
    public HashCodeException(){
        super("Oops! Something went wrong...HashCode not equal");
    }
}
