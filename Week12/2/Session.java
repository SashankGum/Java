/**
 * Session interface, which contains the methods login and logout, which have to be implemented by each client to establish a connection
 * with the server.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
 import java.rmi.*;  
 
public interface Session extends Remote{  
    
    /**
     * login returns a unique id to each client, with which the client can establish a connection with the server 
     */
    public int login() throws RemoteException;
    
    /**
     * the clients connection with the server is terminated
     */
    public boolean logout(int x) throws RemoteException;
    
}  