/*
 * HangmanClient.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This is a client program which establishes the connection with the HangmanServer and communicated with it.
 * It sends the user input from client to the HangmanServer and accepts back the processed input from it and displays it 
 * to the user.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class HangmanClient implements Serializable{
    
    /**
     * Prints the HangmanScene generated at the Server.
     * 
     * @param map 
     */
    public static void printMap(String[] map){
        for(int i=0;i<map.length;i++){
            System.out.println(map[i]);
        }
    }
    
    /**
     * Establishes a connection with the server at the specified port number. 
     * It accepts user input and sends it to the server and accepts the result and the Hangman Map from the server.
     * 
     * @param args  Not used in this program.
     */
    public static void main(String[] args) {
               boolean result = true;
        try(Socket client = new Socket("localhost",6000)){
            ObjectOutputStream ous = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            String map[];
            Scanner sc = new Scanner(System.in);
            int chances ;
            Object[] c = (Object[]) ois.readObject();                           // reads the initial hangman map from server
            System.out.println("------------------------CLIENT---------------------------");
            map = Arrays.copyOf(c, c.length, String[].class);                   // converts array of objects to a string array.
            printMap(map);
            while(true){
                chances = (int)ois.readObject();                                // reads number of remaining chances from server
                if(chances>9){
                    boolean outcome = (boolean)ois.readObject();                // reads true if player has won during his last chance, and false otherwise
                    if(outcome){
                        System.out.println("Congratulations! You live to fight another day :-)");
                        break;
                    }
                    else{
                    System.out.println("You Are Dead!");
                    System.out.println("The Word was - "+ois.readObject());
                    break;
                    }
                }
                result = (boolean)ois.readObject();                             // reads true if player has won, and false otherwise 
                if(result==true){
                    System.out.println("Congratulations! You live to fight another day :-)");
                    break;
                }
                char input;
                do{
                System.out.println("Enter A letter: ");
                input = sc.next().trim().charAt(0);
                if(!Character.isAlphabetic(input))
                        System.out.println("Invalid input!...Enter an alphabet");
                }
                while(!Character.isAlphabetic(input));
                ous.writeObject(input);                                         // sends the chracter input by user to server
                c = (Object[])ois.readObject();                                 // reads the upated hangman map from the server
                map = Arrays.copyOf(c, c.length, String[].class);               // converts Object[] to String[]
                printMap(map);                                                  // prints HangMan scene
                System.out.println("\n----Word---->"+ois.readObject());
                System.out.println("\nChances Remaning - "+(9-chances));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
