
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This is a CustomInputStream which implements a buffer using ArrayList, The buffer is filled to its capacity.with the help of a thread.
 * once the buffer is full, when ever a read function is called it reads from the buffer, thus avoiding blocking.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */

public class CustomInputStr extends BufferedInputStream implements Runnable{
    private static volatile ArrayList al = new ArrayList();                 // ArrayList buffer
    static Integer intt = new Integer(1);
    
    /**
     * Constructor which initializes the values
     * 
     * @param io InputStream object
     * @param n   
     * @param size size of BufferedInputStream
     * @throws IOException 
     */
    public CustomInputStr (InputStream io,int n,int size) throws IOException{
        super(io,size);                                  //calls FillterInputStream constructor
        Thread t = new Thread(this);
        t.start();
    } 
    
    /**
     * Run method of threads, fills the buffer till its full.
     * It starts executing when the buffer is empty.
     */
    @Override
    public void run(){
        try {
            int byt;
            synchronized(intt){
            while((byt = in.read())!=-1){
                if(al.size()<=500){                                 // 500 is the buffer size
                    //System.out.println("Wrire"+byt);
                    al.add(byt);
                }
                else{                                               
                    if(al.size()>=500){
                        intt.notifyAll();                           // notifies the read function to consume from buffer
                        intt.wait();                                // goes into wait state once the buffer is full
                    }
                    al.add(byt);
                }
            }
            if(byt==-1){
                al.add(byt);
                intt.notifyAll();
            }
           }
        
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    /*----------------------------Last Modified------------------------------------
    public void run(){
        try {
            int byt;
           synchronized(al){
            while((byt = in.read())!=-1){
                   // System.out.println("writing"+(int)byt);
                    al.add(byt);
            }
           }
        
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    */ //----------------------------------------------------------------------
    
    /**
     * Reads an integer from buffer and returns it.
     * If the buffer is empty, then this goes into wait state till the buffer is full again.
     * 
     * @return    Integer value read from buffer. 
     */
    @Override
    public synchronized int read(){
        int byt = -1;
        try{
            synchronized(intt){
                if(al.size()==0){
                    //System.out.println("wait in Read");
                    al.clear();
                    intt.notifyAll();
                    intt.wait();
                    //System.out.println("Read Awake");
                }
                
                 byt = (int)al.remove(0);
                 //System.out.println("remove "+byt);
                 return byt;
                
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return byt;
    }
    /*  ------------------Last Modified----------------------------------
    public synchronized int read(){
        int byt = -1;
        try{
           synchronized(al){
                if(al.size()>0){                                     //wait(10);
                 byt = (int)al.remove(0);
                 //System.out.println("remove "+byt);
                 return byt;

                }
                else{
                    return -1;
                }
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return byt;
    }
    */
    
}
