/*
 * HangmanThreadServer.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.net.*;

/**
 * This Program acts as a server, which contains the logic for the hangman game, the multiple client programs can establish a connection
 * to the server, and depending upon the input of client user, the server program runs the hangman game, and communicates the result 
 * with the client program.
 * 
 * @author Sashank Gummuuri
 * @author Rajkumar Pillai
 */

public class HangmanThreadServer extends Thread implements Serializable{
    
    public static final String FILE_NAME = "HangManWords.txt";
    
    ServerSocket server;
    ObjectOutputStream ous;
    Socket pipe;
    ObjectInputStream ois;
    int id;
    /**
     * Constructor which initializes the server object and id of each server thread associated to a particular client
     * 
     * @param port
     * @param i 
     */
    public HangmanThreadServer(int port,int i){
        try{
           this.server = new ServerSocket(port);
           this.pipe = server.accept();
           this.id = i;
           System.out.println("Player "+id+" connected at port "+port);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public enum Man{
    l1, l2, l3, l4, l5, l6, l7, l8, l9
    };
    
   /**
    * The method init is used to give the initial values to the EnumMap, which is 
    * the complete structure of hangman.
    *
    * @param    map    object of EnumMap, where the structure of hangman will be stored
    */
    public void init(EnumMap<Man,String> map){
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
    * The method printMan is used to print the structure of the hangman after the user completes a turn.
    *
    * @param    map         object of EnumMap
    * @param    chances     is used to reduce the parts of hangman, depending upon the answer given by user
    */
    public void printMan(EnumMap<Man,String> map,int chances) throws IOException{
        Iterator<Man> hangman = map.keySet().iterator();
        String l = "l"+chances;
        System.out.println("----------------------------HangMan for Player - "+id+"--------------------------");
        while(hangman.hasNext()){
          Man m = hangman.next();
          if(l.equals(m.name()))
            hangman.remove();
          if(map.get(m)!=null)
              System.out.print("");
              //System.out.println(map.get(m));
        }
        ous.writeObject(map.values().toArray());
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
    * The method printCurrentWord, prints the current word after the user inputs his guess.
    *
    * @param    word   is  the word which contains all the letters which guessed correctly,
    *                  and ' ' where there the letters are not yet guessed
    */ 
    public void printCurrentWord(char[] word) throws IOException{
        System.out.println("--Word--");
        String x ="";
        for(int i=0;i<word.length;i++){
            if(word[i]!=' '){
                x = x+word[i];
                System.out.print(word[i]);
            }
            else{
                x = x+"_";
                System.out.print("_");
            }
        }
        ous.writeObject(x);
    }
    
   /**
    * The method startGame, contains the base logic of the game. All the above methods are used in this method.
    * It is also responsible to convey the end result to the user.
    *
    * @param    word   is  the word which is randomly selected from the dictionary, which the user has to guess.
    */     
    public void startGame(String word){
        try{
        char[] currentWord = new char[word.length()];                         //word.toCharArray();
        for(int i=0;i<currentWord.length;i++){
            currentWord[i]=' ';
        }
        char input;
        int count=0;
        int chances=1;
        int print = 0;
        int printcount=0;
        EnumMap<Man,String> map = new EnumMap<>(Man.class); 
        init(map);
        printMan(map,print);
        //ous.writeObject(chances);
        //Collection t = map.values();
        //ous.writeObject(t.toArray());
        Scanner s = new Scanner(System.in);
        //System.out.println("Guess the Word");
        //the for loop runs for 9 chances
        outerloop:
        for(chances=1;chances<=9;chances++){
            ous.writeObject(chances);
            printcount=0;
            if(word.equals(String.valueOf(currentWord))){
                ous.writeObject(true);
                System.out.println("\nPlayer "+id+" won!");
                break outerloop;
            }
            else
                ous.writeObject(false);
            do{
             //System.out.print("\nEnter Character : ");
             input = (char)ois.readObject();
             System.out.println("\nPlayer "+id+" Entered - "+input);
             //input = s.next().trim().charAt(0);                               //takes the first character as input even if a string is entered
             input = Character.toLowerCase(input);                            //converting to lowercase to avoid confusion and missmatch
             if(!Character.isLetter(input))
                 System.out.println("Error! Enter a letter");
             else 
                 count++;
            }
            while(!Character.isLetter(input));
    //if the entered caracter is present in the word, currentWord is updated
            for(int j=0;j<word.length();j++){                                  
                if(word.charAt(j)==input){
                    currentWord[j] = input;
                    printcount++;
                }
                    
            }
                if(printcount==0){
                    print++;
                    printMan(map, print);
                }
                else{
                printMan(map,print);
                }
                printCurrentWord(currentWord);
                System.out.println("\nChances Remaining for player "+id+" : "+(9-chances));
        }
        ous.writeObject(chances);
        if(word.equals(String.valueOf(currentWord))&&chances>9){
            
            ous.writeObject(true);
            System.out.println("\nPlayer "+id+" won!");
        }
        else
        if(chances>9){
            ous.writeObject(false);
            ous.writeObject(word);
            System.out.println("\nPlayer "+id+" Lost!\nThe word was : "+word);
        }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void run(){
        try {
            ous = new ObjectOutputStream(pipe.getOutputStream());
            ois = new ObjectInputStream(pipe.getInputStream());
            Random ran = new Random();
            int line;
            String Word = "";
            Scanner sc = new Scanner(new File(FILE_NAME));
            int randWord = countLines(sc);
            sc = new Scanner(new File(FILE_NAME));
            line = ran.nextInt(randWord)+1;
            for(int i=1;i<=line;i++){
            if(i==line){
                Word = sc.nextLine();
            }
            else{
                sc.nextLine();
            }
            }
            startGame(Word);
            System.out.println("Player "+id+" dicconnected...");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void init() throws IOException{
            server = new ServerSocket(6000);
            pipe = server.accept();
    }
    
   /**
    * In the main method, a word is randomly selected from the dictionary file, which is then passed to the startGame method
    * where the game begins.
    *
    * @param       args    command line arguments (ignored)
    * 
    * @exception    e      FileNotFoundException
    */ 
    public static void main(String args[]){
     try{
         int i=0;
         while(true){
             new HangmanThreadServer(6000+i,i).start();
             i++;
         }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
