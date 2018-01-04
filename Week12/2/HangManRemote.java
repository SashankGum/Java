/*
 * HangManRemote.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.File;
import java.rmi.*;  
import java.rmi.server.*;  
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class HangManRemote extends UnicastRemoteObject implements HangMan{  
    int x;
    String DictWord = "";
    char[] currentWord;
    EnumMap<Man,String> map;
    int chances = 1;
    int print = 0;
    public static final String FILE_NAME = "HangManWords.txt";
    
    /**
     * calls the constructor of the super class
     * 
     * @throws RemoteException 
     */
    HangManRemote()throws RemoteException{  
        super();
    }
    
    /**
     * Resets the values of the Data Members
     */
    public void reset(){
        DictWord = "";
        currentWord = null;
        map = null;
        chances = 1;
        print = 0;
    }
    
   /**
    * The method init is used to give the initial values to the EnumMap, which is 
    * the complete structure of hangman.
    *
    * @param    map    object of EnumMap, where the structure of hangman will be stored
    */
    public void initMan(){
        map.put(Man.l1   ,"                ###    ");
        map.put(Man.l2   ,"                ###    ");
        map.put(Man.l3   ,"                 #     ");
        map.put(Man.l4   ,"               #####   ");
        map.put(Man.l5   ,"              # ### #  ");
        map.put(Man.l6   ,"                ###    ");
        map.put(Man.l7   ,"               #   #   ");
        map.put(Man.l8   ,"              ##  ##   ");
        map.put(Man.l9   ,"#######################\n##                   ##\n##                   ##");
    }
    
    /**
     * fetches a word at random file, and calls the initMan() method to initialize the EnumMap
     */
    public void init(){
        try{
            Random ran = new Random();
            int line;
            Scanner sc = new Scanner(new File(FILE_NAME));
            int randWord = countLines(sc);
            sc = new Scanner(new File(FILE_NAME));
            line = ran.nextInt(randWord)+1;
            for(int i=1;i<=line;i++){
                if(i==line){
                    DictWord = sc.nextLine();
                }
                else{
                    sc.nextLine();
                }
            }
            currentWord = new char[DictWord.length()];
            for(int i=0;i<currentWord.length;i++){
                currentWord[i]=' ';
            }
            map = new EnumMap<>(Man.class);
            initMan();
        }
        catch(Exception e){
            System.out.println(e);
        }
    
    }
    
   /**
    * The method countLines, counts the number of lines in the dictionary file and returns the value
    *
    * @param    s   is  a scanner object with refers to the dictionary
    * @return       the total number of lines in the dictionary
    */    
    public int countLines(Scanner s){
       int count=0;
       while(s.hasNextLine()){
           count++;
           s.nextLine();
       }
        return count;
    }
    
    /**
     * Returns the string representation of values in the EnumMap
     * 
     * @return String of values
     */
    public String[] stringMan(){
        Object[] m = map.values().toArray();
        return Arrays.copyOf(m, m.length, String[].class); 
    }
    
    /**
     * removes some part of EnumMap, with key specified by chances
     * 
     * @param chances the key in EnumMap, whose corresponding value should be removed.
     */
    public void removeMan(int chances){
        Iterator<Man> hangman = map.keySet().iterator();
        String l = "l"+chances;
        while(hangman.hasNext()){
          Man m = hangman.next();
          if(l.equals(m.name()))
            hangman.remove();
          if(map.get(m)!=null)
              System.out.print("");
        }
    }
    
   /**
    * The method printCurrentWord, prints the current word after the user inputs his guess.
    *
    * @param    word   is  the word which contains all the letters which guessed correctly,
    *                  and ' ' where there the letters are not yet guessed
    */ 
    public String printCurrentWord() {
        String x ="";
        for(int i=0;i<currentWord.length;i++){
            if(currentWord[i]!=' '){
                x = x+currentWord[i];
            }
            else{
                x = x+"_";
            }
        }
        return x;
    }
    
    /**
     * Updates the value of the curentWord, if character input by user is present in the DictWord
     * and calls removeMan if the character is not present in DictWord.
     * 
     * @param input alphabet
     * @return 
     */
    public String input(char input){
        int printcount = 0;
        if(chances <= 9){
            for(int j=0;j<DictWord.length();j++){                                  
                if(DictWord.charAt(j)==input){
                    currentWord[j] = input;
                    printcount++;
                } 
            }
                if(printcount==0){
                    print++;
                    removeMan(print);
                }
        }
        chances++;    
        return printCurrentWord();
    }
    
    /**
     * Returns true if currentWord is equal to DictWord. (ie. Player has won the game) 
     * 
     * @return true if player has won the game and false otherwise
     */
    public boolean checkResult(){
        if(DictWord.equals(String.valueOf(currentWord)))
            return true;
        else
            return false;
    }
    
    /**
     * Returns the DictWord after the player has completed playing
     * 
     * @return DictWord 
     */
    public String returnCorrectWord(){
        if(chances>9)
            return DictWord;
        else
            return "Ummmm...No Cheating!";                                   
    }
    
    /**
     * Returns the chances remaining
     * 
     * @return chances remaining
     */
    public int chances(){
        return chances;
    }

}