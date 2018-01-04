/*
 * MyStorage.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * The purpose of this program is to understand inheritance and method overriding in classes. 
 * A base class MyStorage is defined, whose methods will be overridden by its child classes.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class  MyStorage {
    
   /**
    * The method add is used to add a string to the storage
    *
    * @param    s    The string to be added
    * @return        true if string is added successfully, and false otherwise
    */ 
   public boolean add(String e) {
	return true;
   }
   
   /**
    * This method removes all the elements from the storage
    */
   public void clear()	{
   }
   
   /**
    * This method is used to check if the storage contains a particular string or not
    *
    * @param    s    The string to be checked
    * @return        true if string is present and false otherwise
    */
   // true if o is in storage
   public boolean contains(String o)	{
	return true;
   }
   
   /**
    * This method checks if storage is empty or not.
    *
    * @return        true if list is empty and false otherwise.
    */
   public boolean isEmpty()	{
	return true;
   }
   
   /**
    * This method removes a particular string from storage, if it is present.
    *
    * @param    s    The string to be removed
    * @return        true if string is removed and false otherwise
    */
   public boolean remove(String o)	{
	return true;
   }
   
   /**
    * This method returns the size of the storage.
    *
    * @return        size of list.
    */
   public int size()	{
	return 0;
   }
   
   /**
    * This method returns the name of the class
    *
    * @return        class name
    */
   public String getClassName() {
        return "MyStorage";
   }
}

