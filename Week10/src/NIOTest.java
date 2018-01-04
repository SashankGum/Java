import java.io.*;
import java.util.Scanner;
/**
 * This Program has uses two types of threads, namely reader and writer.
 * The purpose of writer is to read a block of data from file and fill it in the buffer. As soon as the writer fills the buffer,
 * n number of readers starts writing the data on the buffer into a separate file(different for each reader).
 * This process continues till the file is read completely. and we have produced n copies of the original file.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class NIOTest extends Thread {
    
    private  static String fileName = "data.txt";
    static NIOTest[] reader;
    BufferedReader input ;
    static int MAX = 1024;
    static char [] buf;
    int info;
    String name;
    PrintWriter[] pw;
    static int file_counter = 0;
    
    static int writeReturn = 0;
    static int bufferStatus = 0;
    static  int threadCounter = 0;
    int id;
    int readBufferFlag=0;

    /**
     * Constructor which initializes the static members and non-static members.
     * 
     * @param name  name of each thread
     * @param id    id of each thread
     * @throws FileNotFoundException 
     */
    public NIOTest(String name,int id) throws FileNotFoundException{
        try{
        this.input = new BufferedReader(new InputStreamReader (new FileInputStream(fileName)));
        this.name = new String(name);
        this.id = id;
        pw = new PrintWriter[threadCounter];
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    /**
     * initializes the buffer size, depending upon the number of threads.
     */
    public static void init(){
        int x = (int)threadCounter/2;
        buf = new char[x*MAX];
    }

    /**
     * Writer reads a block of data from a file and fills the buffer. After it fills the buffer, it waits till all the 
     * readers consume the data in the buffer.
     * It continues to do so till the end of file is reached.
     * 
     * @throws IOException 
     */
    public void writer() throws IOException  {
        //pw = new PrintWriter("File.txt");
        try     {
                while (  ( writeReturn = input.read(buf, 0, MAX) ) != -1 ) {
                    //System.out.println("Writer - notify wait()"+buf[0]);
                    bufferStatus=1;
                    while(bufferStatus!=0){
                        //System.out.println("Writer Waiting");
                        sleep(7);
                    }
          

                }
                bufferStatus = -1;
                writeReturn = -1;
        }
        catch(Exception ee){
            System.out.println(ee);
        }
        System.out.println("All readers have copied the file!");
    }
    
    /**
     * This method checks if all the reader threads have read the buffer or not. If they have then it notifies the writer thread.
     * 
     * @return true if all the readers have read the buffer, and false otherwise.
     */
    public synchronized static boolean checkBufferFlag(){
        int count = 0;
        for(int i=0;i<threadCounter;i++){
            if(reader[i].readBufferFlag==1){
                count++;
                //System.out.println("count - "+count);
            }
        }
        if(count==threadCounter){
            //System.out.println("Reset Buffer Status");
            bufferStatus = 0;
            return true;
        }
        else
            return false;
    }
    
    
    /**
     * This method is run parallelly by n reader threads, each thread copies the data in the buffer into a file(unique for each thread)
     * and they continue to do so, un till the writer stops filling the buffer when it encounters the end of file.
     */
    public void reader(){
        //synchronized(inn2){
           // synchronized(name){
            try {
                //System.out.println("Thread "+id);
                pw[id-1] = new PrintWriter("file"+(id)+".txt");
                while(writeReturn!=-1){                        //file dosent end
                    while(bufferStatus!=1){                    // when buffer is empty, wait till buffer is full
                        if(bufferStatus==-1)                   
                            break;
                        //System.out.println("sleep - "+id+"ReadFlag - "+readBufferFlag+" - BUFFERsTATUS - "+bufferStatus);
                        sleep(1);             
                    }
                    if(bufferStatus==1){                       // when buffer is full, write in file
                            readBufferFlag = 1;
                            pw[id-1].write(buf);
                            pw[id-1].flush();
                            System.out.print("");
                            System.out.print("");
                            //System.out.println("Thread "+id+" - Write -->"+buf[0]);
                            while(bufferStatus!=0){            // check if all threads have read from buffer
                                boolean x = checkBufferFlag();
                                if(bufferStatus==-1||writeReturn==-1)
                                    break;
                              //  System.out.println("Thread - "+id+" in wait till Read Reset");
                                //sleep(1);
                            }
                            readBufferFlag=0;                              // reset flag
                            //System.out.println("Thread " + id +" out of wait - "+readBufferFlag);
                      
                            while(bufferStatus!=0){                       //exit condition
                                //System.out.println("-------"+bufferStatus);
                                if(bufferStatus==1||bufferStatus==-1||writeReturn==-1){
                                break;
                                }
                                sleep(1);
                            }
                        
                    }
                }
                if(writeReturn==-1){
                    pw[id-1].flush();
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
    }
    
    /**
     * run method directs the threads to perform their designated functionalities.
     */
    @Override
    public void run(){
        try {
            if(name.equals("writer"))
                writer();
            else{
                reader();
            }
                
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
   /**
    * the reader and writer threads start  from the main method.
    *
    * @param  args command line arguments
    */
    public static void main(String[] args) {
            long ms = System.currentTimeMillis();
            Scanner s = new Scanner(System.in);
            try{
                threadCounter = 3;
                init();
                NIOTest writer = new NIOTest("writer",0);
                writer.start();
                reader = new NIOTest[threadCounter];
                for(int i = 0;i<threadCounter;i++){
                    reader[i] = new NIOTest("reader"+i,i+1);
                }
                for(int i = 0;i<threadCounter;i++){
                    reader[i].start();
                }
                for(int i = 0;i<threadCounter;i++){
                    reader[i].join();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        System.out.println("Time Taken"+(System.currentTimeMillis()-ms));
    }
    
}
