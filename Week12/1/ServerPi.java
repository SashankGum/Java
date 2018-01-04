/*
 * ServerPi.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */
import java.rmi.registry.*;  
/**
 * Binds the server URL and port to the registry with the stub object
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class ServerPi extends Thread{
    static int port = 1099;
    static int count = 0;
    /**
     * Binds the URL to registry and remote stub object
     */
    public void run(){
        try{        
                    LocateRegistry.createRegistry(port);
                    Registry r = LocateRegistry.getRegistry();
                    RMIInterfacetesting stub;
                        stub = new countHelper();
                        r.rebind("rmi://localhost:"+port+"/player",stub);
                        System.out.println("----");
            }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * Main Method
     * 
     * @param args 
     */
    public static void main(String args[]){  
        try{
            new ServerPi().start();  
            System.out.println("Server Started Successfully!");
        }
        catch(Exception e){
            System.out.println(e);
        }  
    }  
}  
