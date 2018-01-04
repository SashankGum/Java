/*
 * Directory.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.Scanner;

/**
 * This program implements a simplified version of find command in Linux.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class Directory {
    
    /**
     * Finds all the folders, sub-folders and files in a given directory.
     * 
     * @param dir   Starting Directory
     * @param f     an ArrayList of File type
     */
    public static void listf(String dir, ArrayList<File> f) {
    try{
    File directory = new File(dir);
    File[] files = directory.listFiles();
    for(int i =0;i<files.length;i++) {
        if (files[i].isFile()) {                       
            f.add(files[i]);                            // adds a file to the ArrayList
        } else if (files[i].isDirectory()) {
            f.add(files[i]);                            // adds the folder to ArrayList
            listf(files[i].getAbsolutePath(), f);       // recursively calls itself with the subfolder and arraylist as parameters
        }
    }
    }
    catch(NullPointerException e){
        System.out.println(e);
    }
    }
    
    /**
     * Calculates the length of a Folder by adding the length of all the files inside it and its sub-folders.
     * 
     * @param file      folder to calculate length of
     * @return          length of the folder
     */ 
    public static long directoryLength(File file,long sizee){
        long size = 0;
        File[] files = file.listFiles();
        for(int i=0;i<files.length;i++){
            if(files[i].isFile()){
                sizee = sizee + files[i].length();                     // adds the length of the file to the total size.
            }
            else if(files[i].isDirectory()){
                    size = directoryLength(files[i],sizee);                // recursively calls itself when a subfolder is encountered
            }
        }
        return sizee;
    }
    
    /**
     * Main Program implements the main logic of the custom find command
     * 
     * @param args 
     */
    public static void main(String[] args) { 
        try{
        Matcher m;
        Pattern p;
        String initialCheck,str,temp,temp1;
        File path = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Input path or press enter to continue in current directory");
        System.out.println("Enter path:");
        String dir = sc.nextLine();
        try{
        if(dir.equals("")){
            path = new File(".");                             // for current directory
        }
        else
        {
            path = new File(dir);                             // for diretory input by user
        }
        }
        catch(Exception ee){
            System.out.println(ee);
        }

        File[] files;
        files = path.listFiles();
        ArrayList<File> f = new ArrayList<File>();
        listf(path.getAbsolutePath(), f);                     // calls listf method to get all files and subfolders into the arraylist
        //System.out.println(f);
        files = f.toArray(files);                             // converts ArrayList to File array type
       // for(int i =0;i<files.length;i++)
            //System.out.println(files[i]);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");       // for date formatting when "-date" command is used
        do{
            System.out.println("Enter Commnd :");
            str = sc.nextLine();
            str = str+" "; 
            // This Regular expression checks if the command input by user follows the same syntax as the find command used in linux.
            initialCheck = ".*(\\s*)(find)(\\s+)(\\.)(\\s+)((-name\\s+(\\w*$?))|(-type\\s+(f\\s+|d\\s+)))(\\s*)(-date\\s|-length\\s)?(\\s*)$";
            p = Pattern.compile(initialCheck);  
            m = p.matcher(str);      // matches the input String to the regular expression 
            //System.out.println(m.find());
          }
        while(m.matches()==false);           // re-enter command if input dosent follow the syntax
        p = Pattern.compile("\\s-name\\s*\\w*");   // regular expression to check if string has "-name" sub-command in it
        m = p.matcher(str);
        boolean result = m.find();
        if(result){
            temp = m.group(0);
            temp = temp.substring(temp.indexOf("e")+1, temp.length());
            temp=temp.trim();
            temp1 = str.substring(str.lastIndexOf("-"),str.length()).trim();
           // files = path.listFiles();
                for(int i = 0;i<files.length;i++){
                    if(files[i].getName().contains(temp+".")||files[i].getName().equals(temp)){
                        if(files[i].isFile()){  // checks if the File object is a file or not 
                            if(temp1.equals("-length")){                // if input command contains "-length" sub-command
                                System.out.printf("%-30s %s",files[i].getName(),files[i].length()+"\n");
                            }
                            else if(temp1.equals("-date")){            // if input command contains "-date" sub-command
                                System.out.printf("%-30s %s",files[i].getName(),sdf.format(files[i].lastModified())+"\n");
                            }
                            else{
                                System.out.println(files[i].getName());  // just prints the files which matches the search name
                            }
                        }
                        else if(files[i].isDirectory()){   // checks if the File object is a directory or not 
                            if(temp1.equals("-length")){                // if input command contains "-length" sub-command
                                System.out.printf("%-30s %s",files[i].getName(),directoryLength(files[i],0)+"\n");
                            }
                            else if(temp1.equals("-date")){             // if input command contains "-date" sub-command
                                System.out.printf("%-30s %s",files[i].getName(),sdf.format(files[i].lastModified())+"\n");
                            }
                            else{
                                System.out.println(files[i].getName());  // just prints the files which matches the search name
                            }
                        }
                    }
                }
        }
        else{              
            // else condition means the input command dosent contain "-name" sub-command but instead contains the "-type" sub-command.
            p = Pattern.compile("\\s-type\\s+(f\\s+|d\\s+)");           //  regular expression to check if string has "-type" sub-command in it
            m = p.matcher(str);
            m.find();
            temp = m.group(0);
            temp = temp.substring(temp.indexOf("e")+1, temp.length());
            temp = temp.trim();
            temp1 = str.trim();
            if(temp.equals("d")){                       // checks for -type d
             //   files = path.listFiles();
                for(int i = 0;i<files.length;i++){
                    if(files[i].isDirectory()){
                        if(temp1.endsWith("-date")){                    // if input command contains "-date" sub-command
                            System.out.printf("%-30s %s",files[i],sdf.format(files[i].lastModified())+"\n");
                        }
                        else if(temp1.endsWith("-length")){             // if input command contains "-length" sub-command
                            System.out.printf("%-30s %s",files[i].getName(),directoryLength(files[i],0)+"\n");
                        }
                        else{
                            System.out.println(files[i].getName());     // just prints the files which matches the search type
                        }
                    }
                }
            }
            else{                                       // else means -type f
                //files = path.listFiles();
                for(int i = 0;i<files.length;i++){
                    if(files[i].isFile()){
                        if(temp1.endsWith("-date")){                     // if input command contains "-date" sub-command
                            System.out.printf("%-30s %s",files[i].getName(),sdf.format(files[i].lastModified())+"\n");
                        }
                        else if(temp1.endsWith("-length")){              // if input command contains "-length" sub-command
                            System.out.printf("%-30s %s",files[i].getName(),files[i].length()+"\n");
                        }
                        else{
                            System.out.println(files[i].getName());        // just prints the files which matches the search type
                        }
                    }   
                }
            }
        }
        
        }
        catch(Exception eee){
            System.out.println(eee+"\t\t\tProgram cannot run due to invalid input!");
        }
    }
}
