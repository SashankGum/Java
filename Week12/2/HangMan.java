/*
 * HangMan.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */

import java.rmi.*;
import java.util.EnumMap;
import java.util.Scanner;

/**
 * This remote interface has  remote method declarations used to show the communication between client and server
 * in the HangMan game.
 *
 * @author Rajkumar Lenin Pillai
 * @author Sashank Gummuluri
 */

public interface HangMan extends Remote{

    /**
     * Structure of Enum map
     */
    public enum Man{
        l1, l2, l3, l4, l5, l6, l7, l8, l9
    };

    /**
     * This method Initialses the enum map
     */
    public void initMan() throws RemoteException;

    /**
     * This method returns the value of enummap in a string array
     */
    public String[] stringMan() throws RemoteException;

    /**
     * This method reduces the size enummap
     */
    public void removeMan(int chances) throws RemoteException;

    /**
     * This method Counts the number of lines in enummap
     */
    public int countLines(Scanner s) throws RemoteException;

    /**
     * This method prints the current word in the game
     */
    public String printCurrentWord() throws RemoteException;

    /**
     * This method returns the current word in the game
     */
    public String returnCorrectWord() throws RemoteException;
    
    /**
     * This method returns the no of chances left for the user
     */
    public int chances() throws RemoteException;
    
    /**
     * This method checks if the player has won the game or not
     */
    public boolean checkResult() throws RemoteException;

    /**
     * To re-initialise the data members for new player
     */
    public void reset() throws RemoteException;

    /**
     * This method checks if the input from user is valid or not and performs the required operation
     */
    public String input(char x) throws RemoteException;
    
    /**
     * This method selects a random word from file for the game
     */
     public void init() throws RemoteException;
}  