/*
 * MyServer.java
 *
 * version: 1.0
 *
 * Revisions:
 *     $Log$
 */

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * This program is the server program ,it accepts the buffer of size 1024 sent by client and reads the character in the buffer
 * to determine the no of even and odd no
 * @author Rajkumar Lenin Pillai
 * @author Sashank Gummuluri
 */
public class MyServer implements Serializable {

    /**
     * The main program which establishes connection between client and server and computes the no of even and odd no in
     * data.txt file and sends the even and odd count to client
     * @param args   command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("The Server Program Started");
            ServerSocket serversocket =new ServerSocket(6500);  // Creating a Server object with local port no of which same as client
            Socket  clientsocket = serversocket.accept();           // Establishing connection between client and server
            BufferedReader serverbr = new BufferedReader(new InputStreamReader(clientsocket.getInputStream())); // To recieve the input stream from client
            int k;
            double even=0,odd=0;
            double OddByEven;
            
            // To read the characters in the buffer sent by client  and count the no of even and odd no
            while((k=serverbr.read())!=-1) {
                int no = Character.getNumericValue((char)k); // To get the actual value of data from the ascii value
                if ((no % 2) == 0) {
                    even++;
                }
                else {
                    if ((no % 2) != 0 && (char)k!= '.' && (char)k != ' ' && k!=10 && k!=13 ){
                        odd++;
                     }
                }
            }
             clientsocket.shutdownInput();
	     OddByEven=odd/even;
             // Sending the computed data back to client
             ObjectOutputStream obj =new ObjectOutputStream(clientsocket.getOutputStream());
             obj.writeObject(even);
             obj.writeObject(odd);
             obj.writeObject(OddByEven);
             clientsocket.close();
            serversocket.close();
        }catch (Exception e){
            System.out.println("Some exception raised"+e);
        }
    }
}
