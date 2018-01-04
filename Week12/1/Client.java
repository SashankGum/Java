import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.*;  
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.util.Scanner;

public class Client extends Thread{  
    static double even = 0;
    static double odd = 0;
    static final Integer inn = new Integer(1);
    static String fileName = "data.txt";
    static BufferedReader input ;
    static int MAX = 1024;
    static char [] buf = new char[MAX];
    static Registry r;
    static RMIInterfacetesting session;
    public static void init(){
        try{
            r = LocateRegistry.getRegistry();
            session = (RMIInterfacetesting)r.lookup("rmi://localhost:1099/player");
            System.out.println("----");
            input = new BufferedReader(new InputStreamReader (new FileInputStream(fileName)) );
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static int nextBlock() throws IOException  {
        int x  = 0,y=0;
        x = input.read(buf, 0, buf.length); // this is the marked line
        y = session.reading(buf,x);
        return x;
    }
    
    public static void main(String args[]){  
        init();
        try{  
            int soManyCharactersRead;
                session.reset();
                while (( soManyCharactersRead = nextBlock() ) != -1 ) {
                    
                }
                even = session.getEven();
                odd = session.getOdd();
                double oddByEven = odd/even;
                System.out.println("Even - "+ even);
                System.out.println("Odd - "+ odd);
                System.out.println("Odd/Even - "+oddByEven);
            
        }
        catch(Exception e){}  
        }  
}  
