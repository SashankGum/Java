/*
 * MyStorage.java
 *
 * version: 3.0
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * The purpose of this program is to understand inheritance and method overriding in classes. 
 * An interface MyStorage is defined, whose methods have to be implemented by classes which implement it.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public interface MyStorage <S> {
    
   /**
    * The method add is used to add a string to the storage
    *
    * @param    e    The string to be added
    * @return        true if string is added successfully, and false otherwise
    */ 
   public boolean add(S e);
   
   /**
    * This method removes all the elements from the storage
    */
   public void clear();
   
   /**
    * This method is used to check if the storage contains a particular string or not
    *
    * @param    o    The string to be checked
    * @return        true if string is present and false otherwise
    */
   // true if o is in storage
   public boolean contains(S o);
   
   /**
    * This method checks if storage is empty or not.
    *
    * @return        true if list is empty and false otherwise.
    */
   public boolean isEmpty();
   
   /**
    * This method removes a particular string from storage, if it is present.
    *
    * @param    o    The string to be removed
    * @return        true if string is removed and false otherwise
    */
   public boolean remove(S o);
   
   /**
    * This method returns the size of the storage.
    *
    * @return        size of list.
    */
   public int size();
   
   /**
    * This method returns the name of the class
    *
    * @return        class name
    */
   public String getClassName() ;
}

