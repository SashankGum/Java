/*
 * MyClient.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;

/**
 * This program is the client program ,it reads character from data.txt file into the buffer of size 1024
 * sends the buffer  to server
 *
 * @author Rajkumar Lenin Pillai
 * @author Sashank Gummuluri
 */

public class MyClient implements Serializable{
    int MAX = 1024;                         // Maximum size of buffer
    char [] buf = new char[MAX];            // The buffer to be sent to server
    private  static String fileName = "data.txt";
    BufferedReader br;

    /**
     * This is the default constructor of MyClient class where object for BufferedReader is created
     * which reads from data.txt file
     */
    public MyClient() {
        try {
           this.br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        }catch (Exception e){}

    }

    /**
     * This method reads from file and writes into buffer of size 1024
     *
     * @return  br            Buffer of size 1024
     * @throws IOException    Throws this exception whenever input or output operation fails
     */
    private int nextBlock() throws IOException  {
        return br.read(buf, 0, buf.length);
    }

    /**
     * The Main program which begins the execution of client
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("The Client Program Started");
            double even=0,odd=0;
            MyClient ob =new MyClient();      // Creating object of class
            Socket clientsocket =new Socket("127.0.0.1",6500);   // Creating socket object for client with the ipaddress of server and local port no

            PrintWriter out =new PrintWriter(clientsocket.getOutputStream(),true);
            FileReader fr= new FileReader(fileName);
            int soManyCharactersRead;  // To specify the number of characters to be read from buffer
            String result;
            int i;
            while ((soManyCharactersRead=ob.nextBlock())!=-1     ) {
                        out.println(ob.buf);            // Send the buffer to the server
                 }
            clientsocket.shutdownOutput();

            // Receiving the result from server
            ObjectInputStream obj =new ObjectInputStream(clientsocket.getInputStream());
            even=(double)obj.readObject();
            odd=(double)obj.readObject();
            double OddbyEven=(double)obj.readObject();

            // Displaying the count information
            System.out.println("Even: "+even);
            System.out.println("Odd: "+odd);
            System.out.println("Odd/Even: "+OddbyEven);
            clientsocket.close();
        }catch (Exception e){
            System.out.println("Some exception raised"+e);
        }
    }
}
