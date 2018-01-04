/*
 * HangManClient.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import static java.lang.System.exit;
import java.rmi.*;  
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * The clientProgram which connects with the server using RMI.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class HangManClient extends Thread{  
    
   /**
    * Prints the HangMan figure given in a Sting[] format.
    * 
    * @param map String[] representation of hangman
    */
   public static void printMap(String[] map){
        for(int i=0;i<map.length;i++){
            System.out.println(map[i]);
        }
    }
    
   /**
    * The main Program where the clients logs into the server
    * The client plays the game by inputing characters which are assessed in the remote methods of the server and the results are returned back.
    * The client logs out of the server once its turn is server. 
    * 
    * @param args 
    */
    public static void main(String args[]){  
        int playerid;
        try{  
            Registry r = LocateRegistry.getRegistry();
            //Session session = (Session)Naming.lookup("rmi://localhost:5070/login");
            //Session session = (Session)Naming.lookup("//129.21.22.196:5070/login");
            Session session = (Session)Naming.lookup("rmi://glados.cs.rit.edu:5070/login");  // connects with login Server
            playerid = session.login();                                          // returns a uniqueID for each player
            System.out.println("Player ID - "+playerid);
            if(playerid ==  -1){
                System.out.println("Server Full! Retry after sometime...");
                exit(0);
            }
            //String url = "rmi://localhost:5070/player"+playerid;
            //String url = "//129.21.22.196:5070/player"+playerid;
            String url = "rmi://glados.cs.rit.edu:5070/player"+playerid;        
            HangMan stub=(HangMan)Naming.lookup(url);                           // looks up for the server in the registry and gets access to game server
            stub.reset();                                                       // resets game variales for each player
            stub.init();
            boolean result = false;
            printMap(stub.stringMan());
            Scanner sc = new Scanner(System.in);
            char input;
            for(int i=0;i<9;i++){
                do{
                System.out.println("Enter A letter: ");
                input = sc.next().trim().charAt(0);
                if(!Character.isAlphabetic(input))                              // input validatin
                        System.out.println("Invalid input!...Enter an alphabet");
                }
                while(!Character.isAlphabetic(input)); 
                System.out.println("Current Word : "+ stub.input(input));
                if(stub.checkResult()){                                         // checks if player has won after every chance
                    result = true;
                    break;
                }
                printMap(stub.stringMan());                                     // prints hangman
                System.out.println("\nChances remaining - "+ (9-stub.chances()+1));
            }
            if((stub.chances()>9)){
                if(stub.checkResult()){
                    result = true;                                              // player has won
                }
                else{
                    result = false;                                             // player has lost
                }
            }
            
            if(result==true)
                System.out.println("Congratulations! You live to fight another day");
            else{
                System.out.println("The Correct word is : "+stub.returnCorrectWord());
                System.out.println("You are dead!");
            }
            
            session.logout(playerid);                                           // client logsout 
        }
        catch(Exception e){}  
        }  
}  