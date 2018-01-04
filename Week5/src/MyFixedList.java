/*
 * MyFixedList.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * The purpose of this program is to understand inheritance and method overriding in classes. 
 * Implements a list whose size fixed
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class MyFixedList extends MyStorage{
    public String MyFList[] = new String[10];           // List of Fixed Size
    public int index = 0;                               // index of list
    
   /**
    * The method add is used to add a string to the Fixed List.
    *
    * @param    s    The string to be added
    * @return        true if string is added successfully, and false otherwise
    */
    @Override
    public boolean add(String s){
        if(index<=MyFList.length-1){
            MyFList[index]=s;
            index++;
            return true;
        }
        else
            return false;
    }
    
   /**
    * This method removes all the elements from the list.
    */
    @Override
    public void clear(){
        for(int i =0;i<MyFList.length;i++){
            MyFList[i] = null;
            index = 0;
        }
    }
    
   /**
    * This method is used to check the list contains a particular string or not
    *
    * @param    s    The string to be checked.
    * @return        true if string is present and false otherwise
    */
    @Override
    public boolean contains(String s){
        boolean result = false;
        for(int i =0;i<MyFList.length && MyFList[i]!= null;i++){
            if(MyFList[i].equals(s)){
                result = true;
                break;
            }
            else 
                result = false;
        }
        return result;
    }
    
   /**
    * This method checks if list is empty or not.
    *
    * @return        true if list is empty and false otherwise.
    */
    @Override
    public boolean isEmpty(){
        boolean result = false;
        int count = 0;
        for(int i =0;i<MyFList.length;i++){
            if(MyFList[i]==null){
                continue;
            }
            else 
                count++;
        }
        if(count==0)
            result = true;
        else 
            result = false;
               
        return result;
    }
    
   /**
    * This method removes a particular string from list, if it is present.
    *
    * @param    s    The string to be removed
    * @return        true if string is removed and false otherwise
    */
    @Override
    public boolean remove(String s){
        String[] tempArray = new String[10];
        int j=0;
        boolean result = false;
        for(int i =0;i < MyFList.length;i++){
         if(MyFList[i]!=null){
            if(MyFList[i].equals(s)){
                MyFList[i]=null;
                index--;
                result = true;
                continue;
            }
            else{
                tempArray[j]=MyFList[i];
                j++;
            }
         }
        }
        MyFList = tempArray;
        return result;
    }
    
   /**
    * This method returns the size of the list.
    *
    * @return        size of list.
    */
    @Override 
    public int size(){
        int i;
        for(i = 0;MyFList[i]!=null;i++);
        return i;
    }
    
   /**
    * This method returns the name of the class
    *
    * @return        class name
    */
    @Override
    public String getClassName(){
        return this.getClass().getName();
    }
    
    /*public void print(){
        System.out.println("----------");
        for(int i =0;i<MyFList.length;i++){
            System.out.println(MyFList[i]+" ");
        }
        System.out.println("----------");
    }*/
    
    public static void main(String args[]){
        
        MyFixedList mfl = new MyFixedList();
        System.out.println(mfl.add("hello"));
        System.out.println(mfl.add("hello1"));
        System.out.println(mfl.add("hello2"));
        System.out.println(mfl.add("hello3"));        
        System.out.println(mfl.contains("hello"));
        System.out.println(mfl.size());
       // mfl.clear();
        System.out.println(mfl.isEmpty());
        //mfl.print();
        System.out.println(mfl.remove("hello"));
        //mfl.print();
        System.out.println(mfl.size());
        System.out.println(mfl.isEmpty());
        System.out.println(mfl.getClassName());
    }
}
