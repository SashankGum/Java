/**
 * SessionHelper.java 
 * 
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.rmi.*;  
import java.rmi.server.*;  
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.server.RemoteServer;

/**
 * This class helps to establish a connection between the client and server, by giving a unique ID to each client
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */

public class SessionHelper extends UnicastRemoteObject implements Session{
    static int PlayerCount = 11;
    static boolean players[] = new boolean[PlayerCount];;
    static{
        for(int i=0;i<PlayerCount-1;i++){
            players[i]=false;
        }
    }
    /**
     * calls the super constructor.
     * 
     * @throws RemoteException 
     */
    SessionHelper()throws RemoteException{  
        super();  
    }
    /**
     * Gives a unique ID to each client. 
     * 
     * @return Unique ID if server is not full and -1 if server is full
     */
    public int login(){
        int id = 0; 
        boolean success = false;
        for(int i=0;i<PlayerCount-1;i++){
            if(players[i]==false){
                players[i] = true;
                id = i;
                success = true;
                break;
            }
        }
        if(success == true){
            try{
              System.out.println("Player "+id+" with IP: "+(RemoteServer.getClientHost())+" Has joined!");
              }
              catch(Exception e){
                System.out.println(e);
              }
            return id;
        }
        else{
            System.out.println("Server Full!");
            return -1;
        }
    }
    
    /**
     * Releases the UniqueId so that it can be given to other clients.
     * 
     * @param x  UniqueId of client
     * @return true if logout was successful and false otherwise.
     * @throws NoSuchObjectException 
     */
    public boolean logout(int x) throws NoSuchObjectException{
        boolean result = false;
        if(players[x]==true){
            players[x]=false;
            result = true;
        }
        System.out.println("Player "+x+ " has left the game!");
        return result;
    }
    
}