package edu.uwm.cs361.classdiagram.data;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
	private static Scanner stdin = new Scanner ( System.in );
	private static PrintStream stdout = System.out;

	private static String a = "test",b = "prog";

	private static String switchreg =
			"^ *" +
					"("+a+"|"+b+")" +
					"" +
					" *$";

	public static void main(String[] args) {
		testRep();
	}

	public static void testReg ( String regexs )
	{
		Pattern regex = Pattern.compile( regexs );
		String in = "";

		while ( true )
			{
				in = stdin.nextLine();
				if ( in.equals ( "exit " ) ) break;
				stdout.println ( (regex.matcher(in).find()) ? "good": "bad" );
			}

	}

	public static void testRep ( )
	{
		Method m;
		Attribute a;

		String in = "";
		while ( true )
			{
				System.out.println ( "Input a method: ");
				in = stdin.nextLine();
				if ( in.equals("exit") ) break;

				//System.out.println( ((p.matcher(in).find()) ? "good":"bad") );


				m = Method.Create(in);
				a = Attribute.Create(in);
				String msg = null;
				msg = (m != null ) ? (m + ((m.isAbstract()) ? " is Abstract" : "") + ((m.isStatic())? " is Static." : "")) : null;
				msg = (a != null ) ? (a + (a.isStatic()? " is Static":"") + (a.isFinal()?" is Final": "") ): msg;
				System.out.println ( ( msg != null ) ? msg : "Not a proper method or attribute." );
			}

	}

}
