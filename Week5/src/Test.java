import java.util.*;

public class Test {
   final int MAX_ELEMENTS = 3;
   final int MAX_COLLISION = 1;
   String allObjects[] = new String[ MAX_COLLISION * MAX_ELEMENTS];
   MyStorage aStorage;
   long milliSeconds = 0;
   
   public Test() {
   }
   public void start()	{
	milliSeconds = System.currentTimeMillis();
   }
   public void end()	{
	System.err.println("Time for all:	" + ( System.currentTimeMillis() - milliSeconds) );
   }
   public void init()	{
	int rennerle = 0;
	for ( int index = 0; index < MAX_COLLISION * MAX_ELEMENTS; index ++)		{
			allObjects[rennerle ++] = new String("" + 2 * index);
	}
   }
   public void print()	{
	for ( int index = 0; index < MAX_COLLISION * MAX_ELEMENTS; index ++)	
		System.out.println(index + ": " + allObjects[index]);
   }
   public void addTest()	{
	init();
	for ( int index = 0; index < MAX_COLLISION * MAX_ELEMENTS; index ++)		{
		aStorage.add( allObjects[index] );
	}
   }
   public void clearTest()	{
   }
   public void containsTest()	{
	System.out.println(aStorage.contains(allObjects[0]));
   }
   public void isEmptyTest()	{
	System.out.println(aStorage.isEmpty());
   }
   
   public void removeTestObjectsWhichAreStored()	{
	init();
	String o = null;
	boolean passed = true;
	
	for ( int index = 0; index < MAX_COLLISION * MAX_ELEMENTS ; index ++ )	{
		o = allObjects[index];
		if  (! aStorage.remove( o ) )	{
			System.out.println("remove of existing object at " + index + " failed ");
			System.out.println("\t" + o  );
			passed = false;
		}

	}
	if (  aStorage.remove( o ) )	{
		System.out.println("remove of former existing object which has already been removed failed");
		System.out.println("\t" + o  );
		passed = false;
	}
	if ( ! passed )
		System.out.println("remove: for " +  aStorage.getClassName() + " failed.");
   }
   public void removeTestObjectsWhichAreNotStored()	{
	init();
	boolean passed = true;
	if  ( aStorage.remove( "this one does not exit in storage" ) )	{
		System.out.println("remove: of  none existing object did not fail");
		passed = false;
	}
	if ( ! passed )
		System.out.println("remove: for " +  aStorage.getClassName() + " failed.");
   }
   public void removeTest()	{
   	removeTestObjectsWhichAreStored();
	removeTestObjectsWhichAreNotStored();
   }
   public void sizeTest()	{
	System.out.println(aStorage.size() );
   }
   public void getClassNameTest()	{
	System.out.println(aStorage.getClassName() );
   }
   public void testIt(MyStorage aStorage)	{
	this.aStorage = (MyStorage) aStorage;
	start();

	addTest();	
 	removeTest();
	clearTest();
	isEmptyTest();
	sizeTest();
	getClassNameTest();

	end();
   }

   public static void main(String args[] )	{
	 //(new Test()).testIt(new MyHasSet());
	//(new Test()).testIt(new MyList());
	 (new Test()).testIt(new MyFixedList());
	System.exit(0);
   }
}
