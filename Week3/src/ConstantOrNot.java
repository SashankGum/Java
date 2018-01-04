/*
 * ConstantOrNot.java
 *
 * Version :   1.0
 *
 * Revision:
 *     $Log$
 */

/**
 * This program does not compile and the reasons for it has been mentioned in the commenets below
 *
 *  @author      Rajkumar Pillai
 *  @author      Sashank Gummuluri
 *
 */
class ConstantOrNot {

    private final int aInt = 1;
    private final String aString = "abc";
    private       String bString = "abc";
    private final String[] aArray = new String[10];
    // Once a final variable has been assigned, it always contains the same value. 
    public void doTheJob() {
        aInt = 3;                      // Value '3' cannot be assigned to final integer variable aint
        aString = aString + "abc";     // Value cannot be assigned to final String named aString
        aString = aString;             // Value cannot be assigned to final String named aString
        aArray = new String[10];       // Final variable aArray which is a String Array cannot be reinitialised
        bString = aString;
        bString = aString + "def";
        aArray[0] = "abc";
    }

    public static void main( String args[] ) {
        new ConstantOrNot().doTheJob();
    }
}