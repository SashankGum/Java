/**
 * HangManServer.java 
 * 
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.rmi.*;  

/**
 * This Program acts as a server, which contains the logic to bind the stubs to the registry
 * Server binds the a stub object to each client slot
 * 
 * @author Sashank Gummuuri
 * @author Rajkumar Pillai
 */
public class HangManServer extends Thread{
    static int port = 5070;
    static int count = 0;
    public void run(){
        try{        
                    
                    //System.setProperty("java.security.policy", "policy.all");
                    //        System.setProperty("java.security.policy", "policy.all");
                    //        if (System.getSecurityManager() == null)
                    //    {
                    //              System.setSecurityManager(new RMISecurityManager());
                    //    }
                    //System.setSecurityManager(new SecurityManager());
                    //LocateRegistry.createRegistry(port);
                    //Registry r = LocateRegistry.getRegistry();
                    HangMan stub; 
                    Session helper = new SessionHelper();
                    //Naming.rebind("//129.21.22.196:5070/login", helper);  
                    Naming.rebind("rmi://glados.cs.rit.edu:5070/login", helper);  
                    for(int i=0;i<10;i++){                                             // there are 10 maximum users possible.
                        stub = new HangManRemote();
                        //Naming.rebind("rmi://localhost:"+port+"/player"+i,stub);
                        //Naming.rebind("//129.21.22.196:5070/player"+i,stub);
                        Naming.rebind("rmi://glados.cs.rit.edu:5070/player"+i, stub);  
                    }
                    //r.rebind("rmi://localhost:"+port+"/player0",stub);
                    System.out.println("---------");
                
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    /**
     * Main method
     * 
     * @param args 
     */
    public static void main(String args[]){  
        try{
            new HangManServer().start();  
            System.out.println("Server Started Successfully!");
        }
        catch(Exception e){
            System.out.println(e);
        }  
    }  
}  