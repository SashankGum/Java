/*
 * MyHasSet.java
 *
 * version: 1.0
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * The purpose of this program is to understand inheritance and method overriding in classes. 
 * Implements the methods of HashSet class in java
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 */
public class MyHasSet extends MyStorage{
    int index = 0;
    String[] MySet = new String[index];
   
    
   /**
    * The method is used to add a string to the set. If the element is already present in the set, then it is not added.
    *
    * @param    s    The string to be added
    * @return        true if string is added successfully, and false otherwise
    */
    @Override
    public boolean add(String s){
        int count = 0;
        String[] tempList;
        boolean result = false;
        for(int i=0;i<index ;i++){
            if((MySet[i] == null && s == null)){
                count++;
            }  
            else
                if(MySet[i]!=null){
                    if(MySet[i].equals(s)){
                         count++;
                    }    
                }
        }
        if(index==0)
           tempList = new String[1];
        else
            if(count!=0)
            tempList = new String[index];
        else
            tempList = new String[index+1];
        
        count = 0;
        
        for(int i=0;i<index ;i++){
            if(( MySet[i] == null && s == null && index !=0)){
                count++;
                continue;
            }
            if(MySet[i]!=null){
                if(MySet[i].equals(s)){
                    tempList[i]=MySet[i];  
                    count++;
                }
                else{
                   tempList[i]=MySet[i];  
                }
            }
        }
        
        if(count == 0){
            tempList[index++]=s;
            result = true;
        }
        else
            result = false;
         
        MySet = tempList;
        return result;
    }
    
    
   /**
    * This method removes all the elements from the set.
    */
    @Override
    public void clear(){
        index=0;
        String[] str = new String[index];
        MySet = str;
    }

   /**
    * This method is used to check if the set contains a particular string or not
    *
    * @param    s    The string to be checked
    * @return        true if string is present and false otherwise
    */
    @Override
    public boolean contains(String s){
        boolean result = false;
        for(int i = 0; i < MySet.length; i++){
           if(MySet[i]==null && s==null)
               result = true;
           else{
               if(MySet[i]!=null){
                if(MySet[i].equals(s)){
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
    * This method checks if set is empty or not.
    *
    * @return        true if list is empty and false otherwise.
    */
    @Override
    public boolean isEmpty(){
        boolean result = false;
        if(MySet.length == 0)
            result = true;
        else
            result = false;
        return result;
    }
    
   /**
    * This method removes a particular string from set, if it is present.
    *
    * @param    s    The string to be removed
    * @return        true if string is removed and false otherwise
    */
    @Override
    public boolean remove(String s){
        boolean result = false;
        String[] tempArray = new String[index];
        for(int i =0;i < MySet.length;i++){
         if(MySet[i]!=null){
            if(MySet[i].equals(s)){
            tempArray = new String[index-1];
                break;
            }
         }
         else if(MySet[i]==null){
            tempArray = new String[index-1];
            break;
         }
         else{
            tempArray = new String[index];
            break;
         }
        }
        int j=0;
        for(int i =0;i < MySet.length;i++){
         if(MySet[i]!=null){
            if(MySet[i].equals(s)){
                MySet[i]=null;
                index--;
                result = true;
                continue;
            }
            else{
                tempArray[j]=MySet[i];
                j++;
            }
         }
         else if(MySet[i] == null && s == null){
                MySet[i]=null;
                index--;
                result = true;
                continue;
         }
        }
        MySet = tempArray;
        return result;
    }
    
   /**
    * This method returns the size of the set
    *
    * @return        size of set.
    */
    @Override 
    public int size(){
        int i;
        for(i = 0; i<MySet.length; i++);
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
        for(int i=0;i<MySet.length;i++){
            System.out.println(MySet[i]);
        }
    }*/
    
    public static void main(String[] args) {
        MyHasSet mhs = new MyHasSet();
        mhs.add("abc");
        mhs.add("def");
        mhs.remove("def");
        mhs.add("ghi");
        System.out.println(mhs.contains("def"));
        //mhs.print();
        mhs.clear();
        System.out.println("isEmpty()"+mhs.isEmpty());
        //mhs.print();
        System.out.println("Contains()"+mhs.contains(null));
        mhs.add("def");
        mhs.add(null);
        System.out.println("Contains()"+mhs.contains(null));
        System.out.println("isEmpty()"+mhs.isEmpty());
        mhs.add(null);
        System.out.println(mhs.contains(null));
        mhs.add(null);
        //mhs.remove(null);
        mhs.add("xyz");
        //mhs.clear();    
        //mhs.add("abc");
        //mhs.add("def");
        //mhs.add("ghi");
        //mhs.add("ptr");
        //mhs.print();
        System.out.println(mhs.size());
        System.out.println(mhs.getClassName());
    }
}
