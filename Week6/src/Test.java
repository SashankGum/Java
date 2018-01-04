import java.util.*;

public class Test <T extends MyStorage>{
   final int MAX_ELEMENTS = 3;
   final int MAX_COLLISION = 1;
   String allObjects[] = new String[ MAX_COLLISION * MAX_ELEMENTS];
   MyStorage aStorage;
   T t;
   long milliSeconds = 0;
   
   public Test() {
   }
   
   
   public void testInt(T aStor){
        this.t = aStor;
        t.add(1);
        t.add(7);
        t.add(9);
        t.add(7);
        System.out.println("contains():"+t.contains(9));
        System.out.println("remove()"+t.remove(9));
        System.out.println("isEmpty()"+t.isEmpty());
        t.clear();
        System.out.println("isEmpty()"+t.isEmpty());
   }
   
   public void testString(T arr){
       this.t = arr;
       t.add("abc");
       t.add("def");
       t.add("ghi");
       t.add("def");
       System.out.println("contains():"+t.contains("def"));
       System.out.println("remove()"+t.remove("def"));
       System.out.println("isEmpty()"+t.isEmpty());
       t.clear();
       System.out.println("isEmpty()"+t.isEmpty());
   }

   public static void main(String args[] )	{
         //------------For Integer-----------------------------
	 //(new Test()).testInt(new MyHashSet<Integer>());
         //(new Test()).testInt(new MySortedList<Integer>());
         //(new Test()).testInt(new MyList<Integer>());
         //------------For String--------------------------------
        //(new Test()).testString(new MyList<String>());
         (new Test<MySortedList>()).testInt(new MySortedList());
         //(new Test()).testInt(new MyHashSet<String>());
	System.exit(0);
   }
}
