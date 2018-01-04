/*
 * ProducerConsumer.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program consist of k producers and l consumers sharing a  ring buffer of length n.
 * Producer i produces i items at a time and consumer j consumes j items at a time.
 *
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class ProducerConsumer extends  Thread{
    static int a[];               // The shared buffer
    static int producercount=0;   // To keep count of no of producers
    static int consumercount=0;   // To keep count of no of consumers
    static boolean produce;       // Used to call producer
    static boolean consume;       // Used to call consumer
    static int i=0;               // Pointer used for producer
    static int j=0;               // Pointer used for consumer
    int element=0;                // The element to be inserted
    static int n=0;               // Size of the buffer
    static Vector aVector;
    CountDownLatch cd;


    /**
     * Default constructor for producer
     * @param aVector            Object used for synchronization
     * @param cdl                CountdownLatch
     * @param produce            To call the producer threads
     * @param producercount      No of producers
     */
    public ProducerConsumer(Vector aVector,CountDownLatch cdl,boolean produce,int producercount){
        this.aVector=aVector;
        this.cd=cdl;
        this.produce=produce;
        this.producercount=producercount;
    }


    /**
     * Default constructor for consumer
     * @param aVector               Object used for synchronization
     * @param consume               To call the consumer threads
     * @param consumercount         No of consumers
     */
    public ProducerConsumer(Vector aVector,boolean consume,int consumercount){
        this.aVector=aVector;
        this.consume=consume;
        this.consumercount=consumercount;
    }


    /**
     * Used to check if elements of array are 0 or not
     * @return  True   If array is empty false otherwise
     */
    synchronized public static boolean check(){
        for (int k=0;k<=5;k++){
        if(a[k]!=0){
                return false;
            }
        }
        return true;
    }

    /**
     * Calls all the producer threads and then the consumer threads
     */
    public void run() {

        if(produce) {
            producer();
        }
        if(consume) {
            consumer();
        }
    }

    /**
     * Producers inserts a element in the buffer  and waits if the buffer is full
     */
    synchronized public void  producer(){

            if(i<a.length) {
                if (a[i] == 0) {
                    a[i] = ++element;
                    i++;
                }
            }
            System.out.println("   "+" Producer no "+getName()+"  "+ Arrays.toString(a));
            notifyAll();                       // Notify the waiting producer threads
           try {
                    if(a[i]==1) {
                        wait(5000);    // wait if the buffer is full
                    }
                } catch (Exception e){}
        }

    /**
     * Consumer removes a element from buffer and waits if it is empty
     */
    synchronized public void consumer(){
        //System.out.println("consumer is executing");

            if(j<a.length){
             a[j]=0;
             j++;
            }

            System.out.println("   "+" Consumer no  "+getName()+"  "+Arrays.toString(a));
            notifyAll();                      // Noting all the waiting consumer threads
    }

    /**
     * Begins the execution of producer and consumer threads by taking command line arguments as
     * input for no of producers,consumers and size of buffer
     *
     * @param args    command line arguments
     */
    public static void main(String[] args) {
        try {
            Vector aVector =new Vector();
            CountDownLatch cdl = new CountDownLatch(n);
            int counter=1;
            int producers=Integer.parseInt(args[0]);            // No of producers
            int consumers=Integer.parseInt(args[1]);            // No of consumers
            n=Integer.parseInt(args[2]);                        // Size of buffer
            ProducerConsumer producer[] =new ProducerConsumer[producers];
            ProducerConsumer consumer[] =new ProducerConsumer[consumers];
            a = new int[n];
            do {
                for (int i = 0; i < producers; i++) {

                    producer[i] = new ProducerConsumer(aVector, cdl, true,i+1);
                }
                for (int i = 0; i < producers; i++) {

                    producer[i].start();
                }
                for (int j = 0; j < producers; j++) {
                    producer[j].join();
                }
                produce = false;
                for (int i = 0; i < consumers; i++) {
                    consumer[i] = new ProducerConsumer(aVector, true,consumercount+1);
                }
                for (int i = 0; i < consumers; i++) {
                    consumer[i].start();
                }
                for (int j = 0; j < consumers; j++) {
                    consumer[j].join();
                }
                consume = false;
                i=0;                                                //To maintain a ring buffer after producer has fininshed producing
                j=0;                                                //To maintain a ring buffer after consumer has fininshed consuming
                producercount=0;                                    // Making the producer count to zero after the buffer is full
                consumercount=0;                                    // Making the consumer count to zero after the buffer is empty
                System.out.println(counter+" iteration");
                counter++;
            }while (counter<4);

        }catch (Exception e){ }

    }
}





