/*
 * maze.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.io.File;
import java.util.Scanner;

/**
 * The purpose of this program is to find the way out of a 3D Maze (if any)
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai 
 */
public class maze {
    
    public static final String FILE_NAME = "maze.txt";
    public static final int START_LEVEL = 2 ;
    public static final int START_ROW = 0;
    public static final int START_INDEX = 1;
    
   /**
    * The method displayMaze is used to read the structure of the maze from the file provided and convert 
    * the read structure into a 3D character array.
    *
    * @param    sc    Is the scanner object pointing to the file containing maze structure.
    */
    public static char[][][] displayMaze(Scanner sc){
        String temp1;
        int i=0,j=0,k=0;
        //below loop is used to used to display the maze from file.
        while(sc.hasNextLine()){
            temp1 = sc.nextLine().trim();
            System.out.println(temp1);
            if(temp1.startsWith("next")){ 
                i++;                     //counts the number of levels in maze
                j=0;                     //resets the count of number of rows after every level
            }
            else{
                j++;                     //counts the numbers of rows in a particular level
            }
        }
        sc.close();                        
        j--;
        String[][] maaaze = new String[i+1][j+1]; // initialize 2D string array with calculated maze length
        i=0;
        j=0;
        try{
            sc = new Scanner(new File(FILE_NAME));
            //copies the values of maze from file into the 2D string array
            while(sc.hasNextLine()){    
                temp1 = sc.nextLine().trim();
                if(temp1.startsWith("next", 0)){

                    i++;
                    j=0;
                }
                else{
                    maaaze[i][j] = temp1;
                    j++;
                }
            }
        }
        catch(Exception e){
            System.out.println("Error!"+e);
        }
        //declaring a new 3D character array using the lengths of the 2D string array 
        //m[level][row][column]
        char[][][] m = new char[maaaze.length][maaaze[0].length][maaaze[0][1].length()];
        //this loop copies the values of 2D string array, into a 3D character array
        for(i=0;i<maaaze.length;i++){
            for(j=0;j<maaaze[0].length;j++){
                for(k=0;k<maaaze[0][1].length();k++){
                m[i][j][k] = maaaze[i][j].toCharArray()[k];
                }    
            }
        }
        
       return m;
    }
    
    
   /**
    * The method checkDirection returns the next possible direction that the person can take,
    * if there is no possible step, then it returns "none"
    * 
    *
    * @param    maze    Is the scanner object pointing to the file containing maze structure.
    * @param    i       index of character array representing the level 
    * @param    j       index of character array representing the row
    * @param    k       index of character array representing the columns 
    * 
    * @return           the direction of next move and returns "none", if there is no possible move.
    */
    public static String checkDirection(char[][][] maze,int i,int j,int k){
        String dir = "none";
        if(maze[i][j].length-1!=k){
            if(maze[i][j][k+1]=='.'){
                dir="east";
            }
        }
        if(maze[i].length-1!=j){
                if(maze[i][j+1][k]=='.'){
                    dir="south";
                }
        }
        if(j!=0){
                if(maze[i][j-1][k]=='.')
                    dir="north";
            }
        if(k!=0){
                if(maze[i][j][k-1]=='.'){
                    dir= "west";
                }
        }
        if(i!=0){
                if(maze[i-1][j][k]=='.'){
                    dir= "up";
                }
        }
        if(maze.length-1!=i){
                if(maze[i+1][j][k]=='.'){
                    dir = "down";
                }
        }

            return dir;
     }
    
   /**
    * The method countDirection returns count of how many possible directions
    * a person can move from one point.
    * 
    *
    * @param    maze    Is the scanner object pointing to the file containing maze structure.
    * @param    i       index of character array representing the level 
    * @param    j       index of character array representing the row
    * @param    k       index of character array representing the 
    * 
    * @return           no of possible directions from single point.
    */
    public static int countDirection(char[][][] maze,int i,int j,int k){
        int count = 0;
        if(maze[i][j].length-1!=k){
            if(maze[i][j][k+1]=='.'){                  // east
                count++;
            }
        }
        if(maze[i].length-1!=j){
                if(maze[i][j+1][k]=='.'){                // south
                    count++;
                }
        }
        if(j!=0){
                if(maze[i][j-1][k]=='.')                // north
                    count++;
            }
        if(k!=0){
                if(maze[i][j][k-1]=='.'){               // west
                    count++;
                }
        }
        if(i!=0){
                if(maze[i-1][j][k]=='.'){               // up
                    count++;
                }
        }
        if(maze.length-1!=i){
                if(maze[i+1][j][k]=='.'){               // down
                    count++;
                }
        }

            return count;
     }

   /**
    * The method checkFinish is used to check if a given point in maze is the 
    * exit(FINAL) point or not
    * 
    *
    * @param    maze    Is the scanner object pointing to the file containing maze structure.
    * @param    i       index of character array representing the level 
    * @param    j       index of character array representing the row
    * @param    k       index of character array representing the 
    * 
    * @return           returns true, if point is exit point; returns false otherwise
    */   
    public static boolean checkFinish(char[][][] maze,int i, int j, int k){
        boolean end = false; 
        if(i==START_LEVEL && j==START_ROW && k==START_INDEX){     // start point cannot be final point
            end = false;
        }
        else{
            if(k == maze[i][j].length-1 && maze[i][j][maze[0][1].length-1] == '.')  // exit point on last column of any level
                end = true;
            if(j == maze[1].length-1 && maze[i][maze[1].length-1][k] == '.')        // exit point on last row of any level
                end = true;
            if(j == 0 && maze[i][0][k] == '.')                                      // exit point on firt row of any level
                end = true;
            if(k == 0 && maze[i][j][0] == '.')                                      // exit point on first column of any level
                end = true;
            if(i == 0 && maze[0][j][k] == '.')                                      // exit point on first level of the maze
                end = true;
            if(i == maze.length && maze[maze.length][j][k] == '.')                  // exit point on last level of the maze
                end = true;
            }
        
        return end;
    }
    
    
   /**
    * The method rightWall checks if wall is present on the right side of the person or not.
    * 
    *
    * @param    maze    Is the scanner object pointing to the file containing maze structure.
    * @param    i       index of character array representing the level 
    * @param    j       index of character array representing the row
    * @param    k       index of character array representing the 
    * 
    * @return           returns true, if wall is present on right side; and returns false otherwise
    */  
    public static boolean rightWall(char[][][] maze,int i, int j, int k){
        boolean right = false; 
        switch(checkDirection(maze, i, j, k)){
            case "east":
                if(maze[i][j+1][k]=='#')
                    right = true;
                break;
            case "south":
                if(maze[i][j][k-1]=='#')
                    right = true;
                break;
            case "west":
                if(maze[i][j-1][k]=='#')
                    right = true;
                break;
            case "north":
                if(maze[i][j][k+1]=='#')
                    right = true;
                break;
            case "none":
                    right = false;
            default :
                    right = false;
        
        }
        return right;
    }
    
    /**
    * The startGame contains the main logic of the program. It contains the implementation of 
    * wall follower technique, to move forward in the maze and to find the exit point.
    *
    * @param    maze    Is the scanner object pointing to the file containing maze structure.
    */  
    public static void startGame(char[][][] maze){
        int i,j,k;
        int tempi,tempj,tempk;                           // break point indexes
        String dir;
        i = START_LEVEL; 
        j = START_ROW;
        k = START_INDEX;
        tempi = i;
        tempj = j;
        tempk = k;
        int retcount = 0;
        outerloop:
        while(!checkFinish(maze, i, j, k)){              //loop runs till end point is found 
           dir = checkDirection(maze, i, j, k);   
            if(dir.equals("none")){                      // no direction
                maze[i][j][k]='~';
                retcount++;
                if(retcount>30)                          // exit condition for loop, when there is no exit point
                   break outerloop;
                i = tempi;                               // 
                j = tempj;                               //  reset array indexes to break point indexes
                k = tempk;                               //
            }
            else{
            if(!dir.equals("none")){                     // direction exists
                if(rightWall(maze, i, j, k)){            // right wall exists
                    switch(dir){
                        case "up": maze[i][j][k] = '~';  // move up to next level
                                    i--;
                                    break;
                        case "down": maze[i][j][k] = '~';// move down to below level
                                    i++; 
                                    break;
                        case "east": maze[i][j][k] = '~';// move to next column
                                    k++;
                                    break;
                        case "west": maze[i][j][k] = '~';// move to previous column
                                     k--;
                                    break;
                        case "north": maze[i][j][k] = '~';// move to next row
                                      j--;
                                    break;
                        case "south": maze[i][j][k] = '~';// move to previous row
                                     j++;
                                    break;
                    }
                    if(countDirection(maze, i, j, k)>1){ // if morethan one direction from single point 
                        tempi = i;
                        tempj = j;                        // set break point values
                        tempk = k;
                    }
                }
                else{                                     // direction exists but no right
                    switch(dir){
                        case "up": maze[i][j][k] = '~';   // move up to next level
                                    i--;
                                    break;
                        case "down":maze[i][j][k] = '~';  // move down to below level
                                    i++; 
                                    break;
                        case "east":maze[i][j][k] = '~';  // move to next column 
                                    k++;
                                    break;
                        case "west":maze[i][j][k] = '~';  // move to previous column
                                    k--;
                                    break;
                        case "north":maze[i][j][k] = '~'; // move to next row
                                     j--;
                                    break;
                        case "south": maze[i][j][k] = '~'; // move to previous row
                                     j++;
                                     break;
                    }
                }
        
            }
            }
        }
        
        if(checkFinish(maze, i, j, k)){                  // Display Maze found
            maze[i][j][k] = '~';
            System.out.println("\nMaze end found!");
        }
        else
            System.out.println("Oops...Maze End Not found!"); 
        
        
        // print traversed maze
        for(i=0;i<maze.length;i++){
            for(j=0;j<maze[0].length;j++){
                for(k=0;k<maze[0][1].length;k++){
                    System.out.print(maze[i][j][k]);
                }
                    System.out.println();
            }
            System.out.println("");
        }
    }
 
    
   /**
    * In the main method, the displayMaze is called to display the read maze from file and startGame 
    * is called to begin finding the path towards the exit.
    * @param       args    command line arguments (ignored)
    * 
    * @exception    e      FileNotFoundException
    */   
    public static void main(String args[]){
        char[][][] mazee;
        try {
        Scanner sc = new Scanner(new File(FILE_NAME));
        mazee = displayMaze(sc);
        startGame(mazee);
        }
        catch(Exception e){
            System.out.println("Error!"+e);
        }
        
    }
}
