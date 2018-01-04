/*
 * MyList.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */


/**
 * The purpose of this program is to understand inheritance and method overriding in classes. 
 * Implements a Dynamic list.
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class MyList extends MyStorage{ 
    int index = 0;
    String[] MyDList = new String[index];
    
    
   /**
    * The method add is used to add a string to the Dynamic List.
    *
    * @param    s    The string to be added
    * @return        true if string is added successfully, and false otherwise
    */
    @Override
    public boolean add(String s){
        String[] tempList = new String[index+1];
        for(int i=0;i<index && MyDList[i]!= null ;i++){
            tempList[i]=MyDList[i];
        }
        tempList[index++]=s;
        MyDList = tempList;
        return true;
    }
    
   /**
    * This method removes all the elements from the list.
    */
    @Override
    public void clear(){
        index=0;
        String[] str = new String[index];
        MyDList = str;
    }
    
   /**
    * This method is used to check the list contains a particular string or not
    *
    * @param    s    The string to be checked
    * @return        true if string is present and false otherwise
    */
    @Override
    public boolean contains(String s){
        boolean result = false;
        for(int i = 0; i < MyDList.length; i++){
           if(MyDList[i]==null && s==null)
               result = true;
           else{
               if(MyDList[i]!=null){
                if(MyDList[i].equals(s)){
                    result = true;
                    break;
                }
                else 
                    result = false;
               }
           }
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
        if(MyDList.length == 0)
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
        boolean result = false;
        String[] tempArray = new String[index];
        for(int i =0;i < MyDList.length;i++){
         if(MyDList[i]!=null){
            if(MyDList[i].equals(s)){
            tempArray = new String[index-1];
                break;
            }
         }
         else if(MyDList[i]==null){
            tempArray = new String[index-1];
            break;
         }
         else{
            tempArray = new String[index];
            break;
         }
        }
        int j=0;
        for(int i =0;i < MyDList.length;i++){
         if(MyDList[i]!=null){
            if(MyDList[i].equals(s)){
                MyDList[i]=null;
                index--;
                result = true;
                continue;
            }
            else{
                tempArray[j]=MyDList[i];
                j++;
            }
         }
         else if(MyDList[i] == null && s == null){
                MyDList[i]=null;
                index--;
                result = true;
                continue;
         }
        }
        MyDList = tempArray;
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
        for(i = 0; i<MyDList.length&&MyDList[i]!=null; i++);
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
        for(int i=0;i<MyDList.length;i++){
            System.out.println(MyDList[i]);
        }
    }*/
    
    public static void main(String[] args) {
        MyList ml = new MyList();
        ml.add("abc");
        ml.add("def");
        ml.add("ghi");
                ml.remove("def");
                System.out.println(ml.size());
               // ml.print();
        ml.clear();
        System.out.println("isEmpty() "+ml.isEmpty());
        ml.add("xcvb");
        ml.add("xcvb");
        System.out.println(ml.size());
                ml.add(null);
        //ml.print();
        ml.remove("def");
        ml.remove(null);
        System.out.println("contains()"+ml.contains("def"));
        //ml.clear();
        //ml.add("ghi");
        //ml.print();
        System.out.println(ml.size());
       // ml.print();
        System.out.println("isEmpty() "+ml.isEmpty());
        System.out.println(ml.getClassName());
    }
}
