/*
 * MyList.java
 *
 * version: 2.0
 * 
 * Revisions: 
 *     $Log$ 
 */
import java.util.*;

/**
 * The purpose of this program is to understand interface and usage of generic programming. 
 * Implements a Dynamic list.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class MyList<S> implements MyStorage<S>{ 
    int index = 0;
    Vector<S> MyDList = new Vector<S>();
    
   /**
    * The method add is used to add a generic element to the Dynamic List.
    *
    * @param    s    The generic element to be added
    * @return        true if element is added successfully, and false otherwise
    */
    @Override
    public boolean add(S s){
        MyDList.add(s);
        return true;
    }
    
   /**
    * This method removes all the elements from the list.
    */
    @Override
    public void clear(){
        MyDList.removeAllElements();
    }
    
   /**
    * This method is used to check the list contains a particular element or not
    *
    * @param    s    The element to be checked
    * @return        true if element is present and false otherwise
    */
    @Override
    public boolean contains(S s){
        return MyDList.contains(s);
    }
    
   /**
    * This method checks if list is empty or not.
    *
    * @return        true if list is empty and false otherwise.
    */
    @Override
    public boolean isEmpty(){
       return MyDList.isEmpty();
    }
    
   /**
    * This method removes a particular element from list, if it is present.
    *
    * @param    s    The element to be removed
    * @return        true if element is removed and false otherwise
    */
    @Override
    public boolean remove(S s){
        return MyDList.remove(s);
    }
    
   /**
    * This method returns the size of the list.
    *
    * @return        size of list.
    */
    @Override
    public int size(){
        return MyDList.size();
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
    
    /**
     * Main method is just used for testing in the class. Do pay heed to it.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        MyList<String> ml = new MyList<String >();
        ml.add("abc");
        ml.add("def");
        ml.add("ghi");
        System.out.println(("Size()"+ml.size()));
        System.out.println(("contains()"+ml.contains("def")));
        System.out.println(("remove()"+ml.remove("def")));
        System.out.println(("isEmpty()"+ml.isEmpty()));
        System.out.println(("contains()"+ml.contains("def")));
        ml.clear();
        System.out.println(("isEmpty()"+ml.isEmpty()));
        System.out.println(("Size()"+ml.size()));
        System.out.println(ml.getClassName());
    }
}
