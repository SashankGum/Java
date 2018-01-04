/*
 * Pi.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */
// original from: http://rosettacode.org/wiki/Pi_set#Java
// modified for color

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.awt.Color;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.util.Scanner;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * This Program counts the number of even and odd digits of pi from a given text file using multiple threads and generates
 * an output file output.png which contains a red color for even no and odd blue for odd nos which is written by each thread
 * under execution.
 *
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */

public class Pi extends JFrame implements Runnable {

    private final int LENGTH_OF_SQUARE = 3;
    private final int LENGTH 	       = 330;
    private final int LENGTH_OF_WINDOW = LENGTH * LENGTH_OF_SQUARE;
    private BufferedImage theImage;
    private String fileName = null;
    Reader input;;
    CountDownLatch cd;

    /**
     * This method executes when each thread is started
     */
    synchronized public void run(){
        createImage();
        cd.countDown();                                         //Reduce count of CountdownLatch by one
    }

    public Pi() {
        super("Pi");

        setBounds(100, 100, LENGTH_OF_WINDOW, LENGTH_OF_WINDOW);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            input = new InputStreamReader(System.in);
        } catch ( Exception e )	{
            e.printStackTrace();
            System.exit(0);
        }
    }
    public Pi(String fileName,CountDownLatch cd) {
        this();
        this.fileName = fileName;
        this.cd=cd;
    }

    public char nextDigit()	{
        char buf[] = new char[1];
        try {
            input.read(buf);
            if ( buf[0] == '.' )
                input.read(buf);
        } catch ( Exception e)	{
            e.printStackTrace();
            System.exit(0);
        }
        return buf[0];
    }

    public void saveImage(BufferedImage theImage)	{
        try {
            String suffix = fileName.substring(1 + fileName.lastIndexOf("."));
            File outputfile = new File(suffix + "_" + fileName);
            ImageIO.write(theImage, suffix, outputfile);
        } catch (Exception e )	{
            e.printStackTrace();
        }
    }
    public void fillSquare(int xOrig, int yOrig, int color)	{
        for (int x = 0; x < LENGTH_OF_SQUARE; x ++ )
            for (int y = 0; y < LENGTH_OF_SQUARE; y ++ )
                theImage.setRGB(xOrig + x, yOrig + y , color);
    }
    public void createImage()	{
        theImage = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        int red = Color.RED.getRGB();
        int blue = Color.BLUE.getRGB();
        int colorUsed;

        for (int y = 0; y < getHeight(); y += LENGTH_OF_SQUARE) {
            for (int x = 0; x < getWidth(); x += LENGTH_OF_SQUARE) {
                char digit = nextDigit();
                fillSquare(x, y,  digit % 2 == 0 ? red : blue );
            }

        }
        repaint();
        saveImage(theImage);
	return ;
   }

    @Override
    public void paint(Graphics g) {

        g.drawImage(theImage, 0, 0, this);
    }
    /**
     * Reads the files and counts the number of even and odd digits of pi and generates an output file output.png
     *
     * @param  args command line arguments
     */
     public static void main(String[] args) {
        String fileName = null;
        if ( args.length == 1 )
            fileName = args[0];
        else
            fileName = "output.png";

        Pi aPi ;

        int n = 1;
        long Mintime = 10000;
        int minCore = 1;
        String min = "";
        for(n=1;n<4;n++){
            long threadTime;
            long ms = System.currentTimeMillis();
            try{
             CountDownLatch cdl = new CountDownLatch(n);                    // Create a countdownlatch for n threads

                ExecutorService exec = Executors.newFixedThreadPool(n);
                Pi thrt[]= new Pi[n+1];

                // An array of threads
                for(int i=0;i<n;i++){
                    thrt[i] =  new Pi(fileName,cdl);
                 }

                // Start all the threads
                for(int j=0;j<n;j++){
                    exec.execute(thrt[j]);
                }

                exec.shutdown();
                cdl.await();                                                  //Main thread is waiting for countdownlatch to finish

            System.out.print("With "+n+" Threads - ");
            System.out.println("\t\t\t\t\t"+(System.currentTimeMillis()-ms)); // Time taken by each thread
            threadTime = (System.currentTimeMillis()-ms);
            if(threadTime <= Mintime){
                minCore = n;
                Mintime = threadTime;
                min = "With "+n+" Threads ";
            }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        System.out.println("We encounter most efficient solution - ");
        System.out.println(""+min+" With Execution time of "+Mintime+"ms\n\n" );
        int cores = Runtime.getRuntime().availableProcessors();                 // To get the no of available processors
        if(minCore == cores){
            System.out.println("System Core count: "+cores);
            System.out.println("Best performance achieved! \nThread count = core count");
        }
        else{
            System.out.println("System Core count: "+cores);
            System.out.println("Since the program involves file i/o using multiple threads, we cannot see much speed\nimprovements as, creating threads has its own overhead");
        }
     }
}
