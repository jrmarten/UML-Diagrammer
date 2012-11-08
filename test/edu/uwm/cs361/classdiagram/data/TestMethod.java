package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import edu.uwm.cs361.Util;

public class TestMethod
{

	Method	attr;

	@After
	public void delim ( )
	{
		System.out.println ( "\n\n\n\n" );
	}

	public void passed ( )
	{
		System.out.println ( "passed" );
	}

	private void startTest ( String name )
	{
		System.out.println ( "Starting " + name + ". . . " );
	}

	@Test
	public void basicTest ( )
	{
		startTest ( "Basic Test" );
		attr = Method.Create ( "- _name():String" );

		assertNotNull ( attr );

		assertTrue ( attr.getName ( ).equals ( "_name" ) );
		assertTrue ( attr.getType ( ).equals ( "String" ) );
		assertTrue ( attr.getAccess ( ) == Access.PRIVATE );

		assertFalse ( attr.isStatic ( ) );
		assertFalse ( attr.isAbstract ( ) );

		assertTrue ( Method.Create ( "_name : String" ) == null );
		assertTrue ( Method.Create ( "_name String" ) == null );
		
		//decided not to test
		//Method meth = Method.Create ( "_name( ) String " );

		passed ( );
	}

	@Test
	public void argTest ( )
	{
		startTest ( "arg test" );

		attr = Method.Create ( "name():String" );
		assertNotNull ( attr );
		assertTrue ( attr.getParameters ( ).isEmpty ( ) );

		attr = Method.Create ( "name ( ) : String" );
		assertNotNull ( attr );
		assertTrue ( attr.getParameters ( ).isEmpty ( ) );

		attr = Method.Create ( " name ( index : int ): String " );
		assertNotNull ( attr );
		
		//only type of an argument matters
		assertTrue ( attr.getParameters ( ).contains ( Argument.Create("x : int" ) ) );
		assertTrue ( attr.getParameters ( ).size ( ) == 1 );

		attr = Method.Create ( "add ( int x, int y ) : int" );
		assertNotNull ( attr );
		assertTrue ( attr.getParameters ( ).contains ( Argument.Create( "x : int" ) ) );
		assertTrue ( attr.getParameters ( ).size ( ) == 2 );

	

		passed ( );
	}

	@Test
	public void keywordTest ( )
	{
		startTest ( "Keyword Test" );

		attr = Method.Create ( "protected():int" );
		assertTrue ( attr == null );
		attr = Method.Create ( "_x():private" );
		assertTrue ( attr == null );
		attr = Method.Create ( "Abstract():int" );
		assertFalse ( attr == null );
		passed ( );
	}

	@Test
	public void accessTest ( )
	{
		startTest ( "Access Test" );

		String sig = " count ( ) : int";

		assertTrue ( Method.Create ( sig ) != null );

		assertTrue ( Method.Create ( sig ).getAccess ( ) == Access.DEFAULT );

		assertTrue ( Method.Create ( "~" + sig ).getAccess ( ) == Access.DEFAULT );
		assertTrue ( Method.Create ( "+" + sig ).getAccess ( ) == Access.PUBLIC );
		assertTrue ( Method.Create ( "-" + sig ).getAccess ( ) == Access.PRIVATE );
		assertTrue ( Method.Create ( "#" + sig ).getAccess ( ) == Access.PROTECTED );

		assertTrue ( Method.Create ( "default " + sig ).getAccess ( ) == Access.DEFAULT );
		assertTrue ( Method.Create ( "public " + sig ).getAccess ( ) == Access.PUBLIC );
		assertTrue ( Method.Create ( "private " + sig ).getAccess ( ) == Access.PRIVATE );
		assertTrue ( Method.Create ( "protected " + sig ).getAccess ( ) == Access.PROTECTED );

		passed ( );
	}

	@Test
	public void modTest ( )
	{
		startTest ( "Modifier test" );
		attr = Method.Create ( "+ abstract static x( ):int" );

		// Methods cannot be abstract and static
		assertTrue ( attr == null );

		attr = Method.Create ( "- static modTest():int" );
		assertFalse ( attr == null );
		assertTrue ( attr.isStatic ( ) );
		assertFalse ( attr.isAbstract ( ) );

		attr = Method.Create ( "- abstract regex():Pattern" );
		assertFalse ( attr == null );
		assertTrue ( attr.isAbstract ( ) );
		assertFalse ( attr.isStatic ( ) );

		passed ( );
	}

}
