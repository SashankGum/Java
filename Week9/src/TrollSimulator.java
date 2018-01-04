
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class TrollSimulator extends Thread{
    static int cookieCount;
    static int threadCount = 0;
    int threadStatus;   // 0 - Eliminated, 1 - playing, 2 - winner 
    static String round;
    static Integer roundint = new Integer(0);
    static Integer roundCount = new Integer(0);
    String name;
    CountDownLatch cd;
    
    public TrollSimulator(int i, CountDownLatch cd) {
        threadStatus = 1;
        name = "Troll - "+i;
        this.cd = cd;
    }
    
    public void playGame() throws InterruptedException{
        try{
            while(threadCount>1&&threadStatus == 1){
                Random ran = new Random();
                int rann = 1 + ran.nextInt(200);
                sleep((long)rann);
                synchronized(roundint){
                    if(cookieCount>0){
                        cookieCount--;
                        System.out.print(" - "+name+" - ");
                        roundint.wait();
                    }
                    else if(cookieCount==0){
                        threadCount--;
                        cookieCount = threadCount-1;
                        //System.out.println(threadCount);
                        System.out.println("\n"+name + " Couldnt get cookie! ----> Eliminated");
                        threadStatus = 0;
                        roundint.notifyAll();
                        roundint++;
                        break;
                    }
                }
            }
            if(threadCount==1 & cookieCount==0 && threadStatus!=0 ){
                System.out.println("Winner is - "+name );
                threadStatus = 2;
            }
    }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public void run(){
        try {
            playGame();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
    
    public static void setValues(int n){
        cookieCount = n-1;
        threadCount = n;
    }
    
    public static void main(String[] args) {
            int n = 5;
             CountDownLatch cdl = new CountDownLatch(n);

                ExecutorService exec = Executors.newFixedThreadPool(n);
                TrollSimulator thrt[]= new TrollSimulator[n+1];
                setValues(n);
                for(int i=0;i<n;i++){
                    thrt[i] = new TrollSimulator(i,cdl);
            }
                for(int j=0;j<n;j++){
                    exec.execute(thrt[j]);
                }
                exec.shutdown();
    }
    
}
