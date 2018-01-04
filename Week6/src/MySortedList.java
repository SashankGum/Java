/*
 * MySortedList.java
 *
 * version: 2.0
 * 
 * Revisions: 
 *     $Log$ 
 */

import java.util.*;
/**
 * The purpose of this program is to understand interfaces and usage of generic programming.. 
 * Implements a Dynamic list which is sorted all the times.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */

public class MySortedList<S extends Comparable<S>> implements MyStorage<S>{
    Vector<S> MySList = new Vector<S>();
    int nullcounter = 0;
    
   /**
    * The method add is used to add a generic element to the Dynamic List and calls the sort method every time an element is added.
    *
    * @param    s    The element to be added
    * @return        true if element is added successfully, and false otherwise
    */
    @Override
    public boolean add(S s){
        if(s==null){
            MySList.add(0,null);
            nullcounter++;
        }
        else
        MySList.add(s);
        //Comparator com = Collections.reverseOrder().reversed();
        //MySList.sort(com);
        sort();
        //Collections.sort(MySList<S>,Comparator<? extends S>);
        return true;
    }
    
   /**
    * This method removes all the elements from the list.
    */
    @Override
    public void clear(){
        MySList.removeAllElements();
    }
    
   /**
    * This method is used to check the list contains a particular element or not
    *
    * @param    s    The element to be checked
    * @return        true if element is present and false otherwise
    */
    @Override
    public boolean contains(S s){
        return MySList.contains(s);
    }
    
   /**
    * This method checks if list is empty or not.
    *
    * @return        true if list is empty and false otherwise.
    */
    @Override
    public boolean isEmpty(){
       return MySList.isEmpty();
    }
    
   /**
    * This method removes a particular generic element from list, if it is present.
    *
    * @param    s    The element to be removed
    * @return        true if element is removed and false otherwise
    */
    @Override
    public boolean remove(S s){
        return MySList.remove(s);
    }
    
   /**
    * This method returns the size of the list.
    *
    * @return        size of list.
    */
    @Override
    public int size(){
        return MySList.size();
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
    
   // public int compareTo()

    /**
     * This method sorts the strings in the list.
     */
    public void sort(){
        //System.out.println("Print - "+MySList.get(0).compareTo(MySList.get(MySList.size()-1)));
        for (int j = MySList.size() - 1; j >= nullcounter; j--) {
            for (int i = nullcounter+1; i <= j; i++) {
                if (MySList.get(i-1).compareTo(MySList.get(i)) > 0) {
                    S tmp = MySList.get(i-1);
                    MySList.set(i-1, MySList.get(i));
                    //arr[i-1] = arr[i];
                    MySList.set(i, tmp);
                    //arr[i] = tmp;
                }
      
            }
        }
    }
    
    /**
     * print method, prints all elements in the list.
     * just used for the purpose of testing.
     */
    //@Override
    public void print(){
        System.out.println(Arrays.toString(MySList.toArray()));
    }
    
    /**
     * main method just used for testing. Pay no heed to it.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        MySortedList<String> str = new MySortedList<>();
        str.add(null);
        str.add("abc");
        str.print();
        str.add("B");
        str.add(null);
        str.add("b");
        str.print();
        str.add("abc");
        str.add("def");
        str.add("ghi");
        str.print();
        System.out.println(("Size()"+str.size()));
        System.out.println(("contains()"+str.contains("def")));
        System.out.println(("remove()"+str.remove("def")));
        System.out.println(("isEmpty()"+str.isEmpty()));
        System.out.println(("contains()"+str.contains("def")));
        str.print();
        str.clear();
        System.out.println(("isEmpty()"+str.isEmpty()));
        System.out.println(("Size()"+str.size()));
        System.out.println(str.getClassName());
        
    }
}
