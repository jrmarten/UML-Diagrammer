package edu.uwm.cs361.uml;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		Scanner cin = new Scanner ( System.in );
		Method m;
		
		String in = "";
		while ( !in.equals ( "exit") )
		{
			System.out.println ( "Input a method: ");
			in = cin.nextLine();
			m = Method.Create(in);
			System.out.println ( ( m != null ) ? m : "Not a proper method." );
		}
		

	}

}