
import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sashank
 */

public class NIOInputStream extends FilterReader implements Runnable{
    private static volatile ArrayList al = new ArrayList();
    static Integer intt = new Integer(1);
    static int index;
    static int buf_cap;
    static char[] buf; 
    int return_value;
    Thread t;
    

    public NIOInputStream (Reader r) throws IOException{
        super(r);                                  //calls FillterInputStream constructor
        index = 0;
         t = new Thread(this);
         t.start();
    } 

    
    @Override
    public void run(){
        int byt;
        try {
            synchronized(this){
                while(( byt= super.read(buf, 0, buf.length))!=-1){
                    if(buf==null){
                        return_value = byt;
                        System.out.println(return_value);
                    }
                    else{
                        this.notifyAll();
                        this.wait();
                    }
                    
            }
            } 
        } catch (Exception e) {
            System.out.println(e);
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

    @Override
    public int read(char byt[],int off,int length) throws IOException{
        try{
            if(buf == null){
                buf = new char[length];
                t.start();
                return super.read(byt, 0, length);
            }
            else{
                synchronized(this){
                    if(buf==null){
                        buf =  byt;
                        this.notify();
                        this.wait();
                    }
                }
                return return_value;
            }
        }
        catch(Exception ee){
            System.out.println("");
        }

        return 0;
    }
}
