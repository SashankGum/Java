/*
 * MyHashSet.java
 *
 * version: 3.0
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * The purpose of this program is to understand interfaces and usage of generic programming. 
 * Implements the methods of HashSet class in java
 * 
 * @author Sashank Gummuluri
 * @author Rajkumar Pillai
 * @
 */
public class MyHashSet<S> implements MyStorage<S>{
    MyList[] ml;
    
   /**
    * A constructors which implements a list at every index position.
    */
    public MyHashSet() {
            ml = new MyList[51];
            for(int i=0;i<ml.length;i++)
                ml[i] = new MyList();
    }
    
   /**
    * The method is used to add generic type to the set at a particular index based on its hash value. 
    * If the element is already present in the set, then it is not added.
    *
    * @param    s    The generic type to be added
    * @return        true if element is added successfully, and false otherwise
    */
    @Override
    public boolean add(S s){
        int hcode;
        boolean result = false;
        hcode = (s.hashCode()%50);
        if(!ml[hcode].contains(s)){
            result = ml[hcode].add(s);
            //ml[hcode].print();
        }
        
        return result;
    }
    
   /**
    * This method removes all the elements from the set.
    */
    @Override
    public void clear(){
        for(int i=0;i<ml.length;i++)
            ml[i] = new MyList();
    }
    
   /**
    * This method is used to check if the set contains a particular element or not
    *
    * @param    s    The generic type to be checked
    * @return        true if element is present and false otherwise
    */
    @Override
    public boolean contains(S s){
        int hcode = (s.hashCode()%50);
        if(ml[hcode].contains(s))
            return true;
        else
            return false;
    }
    
   /**
    * This method checks if set is empty or not.
    *
    * @return        true if set is empty and false otherwise.
    */
    @Override
    public boolean isEmpty(){
        int flag = 0;
        boolean result = true;
        for(int i=0;i<ml.length;i++){
            if(!ml[i].isEmpty())
                flag++;
        }
        if(flag>0)
            result = false;
        return result;
    }
    
   /**
    * This method removes a particular element from set, if it is present.
    *
    * @param    s    The generic Element to be removed
    * @return        true if element is removed and false otherwise
    */
    @Override
    public boolean remove(S s){
        int hcode = (s.hashCode()%50);
        boolean result = false;
        result = ml[hcode].remove(s);
        return result;
    }
    
   /**
    * This method returns the size of the set
    *
    * @return        size of set.
    */
    @Override
    public int size(){
        int sum = 0;
        for(int i = 0; i<ml.length; i++){
            sum = sum + ml[i].size();
        }
        return sum;
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
    /*
    @Override
    public boolean add(String s){
        String[] temp = new String[index];
        int hcode;
        int index = 0;
        boolean result = false;
        repeatflag = 0;
        hcode = s.hashCode();
        System.out.println("HashCode - "+hcode);
        if(hcode==0 && nullflag == 0){
            temp = new String[1];
            temp[0]=null;
            MyHSet = temp;
            result = true;
            nullflag = 1;
        }
        else
        if(hcode > MyHSet.length-1 || hcode < 0){
            while(hcode <0 || hcode > 2147483647)
            if(hcode<0){
                hcode = -hcode;
                hcode = hcode%MyHSet.length;
            }
            temp = new String[hcode+1];
            index = hcode;
        }
        if(hcode<=MyHSet.length-1){
            index = hcode;
            while(MyHSet[index]!=null){
                if(MyHSet[index]==s)
                    repeatflag++;
                index++;
            }
            if(index<=MyHSet.length-1)
                temp = new String[MyHSet.length];
            else
                temp = new String[index+1];
        }
        for(int i = 0; i< MyHSet.length;i++){
            temp[i] = MyHSet[i];
        }
        MyHSet = temp;
        if(repeatflag>0)
            result = false;
        else{
            MyHSet[index]=s;
            result = true;
        }
        
        return result;
    }
    */

    /**
     * Main method is just used for testing in the class. Do pay heed to it.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        MyHashSet<Integer> mhs = new MyHashSet();
        //mhs.add("abc");
        //mhs.add("def");
       // mhs.add(null);
        //mhs.add("ijk");
        //mhs.add("Aa");
        //        mhs.add("Aa");
        //        mhs.add("BB");
        //mhs.add("abcxccccccccccc");
       // mhs.add("xyz");
       // mhs.add("ijk");
        //mhs.print();
        mhs.add(5);
        mhs.add(3);
        mhs.add(7);
        System.out.println("Contans()"+mhs.remove(7));
        /*mhs.add("abc");
        mhs.add("bcd");
        System.out.println("size()"+(mhs.size()));
        System.out.println("remove()"+mhs.remove("abc"));
        System.out.println("isEmpty()"+mhs.isEmpty());
        System.out.println("contains()"+mhs.contains("abc"));
        mhs.clear();
        System.out.println("size()"+(mhs.size()));
        System.out.println(mhs.getClassName());
        */
    }
}
