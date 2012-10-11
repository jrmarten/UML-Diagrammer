package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMethod
{
	Method attr;

	@Test
	public void basicTest() {
		attr = Method.Create("- _name():String");

		assertTrue ( attr.getName().equals ( "_name" ) );
		assertTrue ( attr.getType().equals("String"));
		assertTrue ( attr.getAccess() == Access.PRIVATE );

		assertFalse ( attr.isStatic ( ) );
		assertFalse ( attr.isAbstract ( ) );
	}

	@Test
	public void argTest() {
		attr = Method.Create( "name():String" );
		assertTrue ( attr.getParameters().isEmpty() );

		attr = Method.Create( "name ( ) : String" );
		assertTrue ( attr.getParameters().isEmpty() );

		attr = Method.Create( " name ( int ): String " );
		assertTrue ( attr.getParameters().contains ( "int" ) );
		assertTrue ( attr.getParameters().size() == 1 );

		attr = Method.Create( "add ( int, int ) : int" );
		assertTrue ( attr.getParameters().contains( "int") );
		assertTrue ( attr.getParameters().size() == 2 );

		for ( String arg : attr.getParameters())
			{
				assertTrue ( arg.equals ( "int" ) );
			}

	}

	@Test
	public void keywordTest ( )
	{
		attr = Method.Create("protected():int");
		assertTrue ( attr == null);
		attr = Method.Create("_x():private");
		assertTrue ( attr == null );
		attr = Method.Create("Abstract():int");
		assertFalse ( attr == null );
	}

	@Test
	public void accessTest ( )
	{
		String sig = "count():int";

		assertTrue ( Attribute.Create( sig ).getAccess() == Access.DEFAULT);

		assertTrue ( Attribute.Create( "~" + sig ).getAccess() == Access.DEFAULT);
		assertTrue ( Attribute.Create( "+" + sig ).getAccess() == Access.PUBLIC);
		assertTrue ( Attribute.Create( "-" + sig ).getAccess() == Access.PRIVATE);
		assertTrue ( Attribute.Create( "#" + sig ).getAccess() == Access.PROTECTED);


		assertTrue ( Attribute.Create( "default " + sig ).getAccess() == Access.DEFAULT);
		assertTrue ( Attribute.Create( "public " + sig ).getAccess() == Access.PUBLIC);
		assertTrue ( Attribute.Create( "private " + sig ).getAccess() == Access.PRIVATE);
		assertTrue ( Attribute.Create( "protected " + sig ).getAccess() == Access.PROTECTED);

	}

	@Test
	public void modTest ( )
	{
		attr = Method.Create("+ abstract static x( ):int");

		//Methods cannot be abstract and static
		assertTrue ( attr == null );

		attr = Method.Create( "- static count:int" );
		assertFalse ( attr == null );
		assertTrue ( attr.isStatic ( ) );
		assertFalse ( attr.isAbstract ( ) );

		attr = Method.Create( "- final regex:Pattern" );
		assertFalse ( attr == null );
		assertTrue ( attr.isAbstract ( ) );
		assertFalse ( attr.isStatic ( ) );
	}

}
