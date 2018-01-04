/*
 * X.java
 *
 * version: 2.1
 * 
 * Revisions: 
 *     $Log$ 
 */

/**
 * This program is used to evaluate the output without actually executing it.
 * 
 * @author Sashank
 * @author Rajkumar Pillai 
 */
class X {

    public static void main( String args[] ) {
    
	int n = 0;
	
	here:

	while ( true )  {                // lets call this outerloop
    
		while ( ( ( n != 3 ) || ( n != 5 ) ) && ( n < 4 ) )  {  // lets call this innerloop
			if ( ++n == 0 )
				System.out.println("a/	n is " + n );
			else if ( n++ == 1 )    {
				System.out.println("b/	n is " + n );
			} else if ( n++ == 2 )
				System.out.println("c/	n is " + n );
			else 
				System.out.println("d/	n is " + n );

			System.out.println("	executing break here");

		}
		
		System.out.println( n % 2 == 0 ?
					( n == 4 ? "=" : "!" )
				      : ( n % 3 != 0 ? "3" : "!3" ));
		break here;
	}
    }
}

/* EXPLANATION
 * initially n is initialized with 0
 * - Outerloop iteration - 1 
 *   - Innerloop iteration - 1
 *     - the execution goes inside the loop body as the expression of innerloop evaluates to TRUE for n = 0
 *     - the value of n is incremented (n = 1, due to ++preincrement) and then the 'if' is evaluated, since 1!=0 ie. FALSE; the execution jumps to else if
 *     - the 'else if' evaualtes to TRUE, as the value of n = 1 and it will be incremented after the execution of 'else if' statement to n = 2,
 *       due to the postincrement ++ operator on n. Since the 'else if' evaluates to TRUE, the compiler wont execute the other nested 'if else'
         and will directly execute the last print statement in the innerloop; printing "	executing break here"
 * - Outerloop iteration - 2
 *   - Innerloop iteration - 2
 *     - n which is previouly 2, now gets incremented to n = 3, due to preincrement (++n) operator. Now the 'if' condition is evaluated to FALSE as n!=0
 *       and now the next 'else if' condition is evaluated.
 *     - in the next 'else if' condition, the value of n is compared with 1 first which evaluates to FALSE, and is then incremented 
 *       to n = 4 (this is because of postincrement operator on n). Then execution then jumps to the next 'else if' condition 
 *     - the 'else if' evaualtes to TRUE, as the value of n = 4 and it will be incremented after the execution of 'else if' statement to n = 5,
 *       due to the postincrement ++ operator on n. Since the 'else if' evaluates to FALSE, the execution jumpts to the 'else' condition.
 *     - the 'else' condiion prints the string "d/	n is 5"
 *     - now the last print statement in the innerloop will be executed; printing "	executing break here"
 *   - Now since n = 5 and n % 2 != 0, expression on the right side of ':' will be executed.
 *     - n % 3 != 0, So the statement on the left side of ':' is executed and "3" will be printed. 
 */

/**
 * OUTPUT
 * b/	n is 2
 * 	executing break here
 * d/	n is 5
 *	executing break here
 * 3
 */