package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestAttribute
{

	Attribute	attr;

	@Test
	public void basicTest ( )
	{
		attr = Attribute.Create ( "- _name:String" );

		assertTrue ( attr.getName ( ).equals ( "_name" ) );
		assertTrue ( attr.getType ( ).equals ( "String" ) );
		assertTrue ( attr.getAccess ( ) == Access.PRIVATE );

		assertFalse ( attr.isStatic ( ) );
		assertFalse ( attr.isFinal ( ) );

		assertTrue ( Attribute.Create ( "- 1invalid:String" ) == null );
		assertTrue ( Attribute.Create ( "- _name String" ) == null );

	}

	@Test
	public void keywordTest ( )
	{
		attr = Attribute.Create ( "protected:int" );
		assertTrue ( attr == null );
		attr = Attribute.Create ( "_x:private" );
		assertTrue ( attr == null );
		attr = Attribute.Create ( "+ _name : int " );
		assertNotNull ( attr );
	}

	@Test
	public void accessTest ( )
	{
		String sig = "count:int";

		assertNotNull ( Attribute.Create ( sig ) );
		assertTrue ( Attribute.Create ( sig ).getAccess ( ) == Access.DEFAULT );

		assertTrue ( Attribute.Create ( "~" + sig ).getAccess ( ) == Access.DEFAULT );
		assertTrue ( Attribute.Create ( "+" + sig ).getAccess ( ) == Access.PUBLIC );
		assertTrue ( Attribute.Create ( "-" + sig ).getAccess ( ) == Access.PRIVATE );
		assertTrue ( Attribute.Create ( "#" + sig ).getAccess ( ) == Access.PROTECTED );

		assertTrue ( Attribute.Create ( "default " + sig ).getAccess ( ) == Access.DEFAULT );
		assertTrue ( Attribute.Create ( "public " + sig ).getAccess ( ) == Access.PUBLIC );
		assertTrue ( Attribute.Create ( "private " + sig ).getAccess ( ) == Access.PRIVATE );
		assertTrue ( Attribute.Create ( "protected " + sig ).getAccess ( ) == Access.PROTECTED );

	}

	@Test
	public void modTest ( )
	{
		attr = Attribute.Create ( "+ final static x:int" );

		assertFalse ( attr == null );
		assertTrue ( attr.isFinal ( ) );
		assertTrue ( attr.isStatic ( ) );

		attr = Attribute.Create ( "- static count:int" );
		assertFalse ( attr == null );
		assertTrue ( attr.isStatic ( ) );
		assertFalse ( attr.isFinal ( ) );

		attr = Attribute.Create ( "- final regex:Pattern" );
		assertFalse ( attr == null );
		assertTrue ( attr.isFinal ( ) );
		assertFalse ( attr.isStatic ( ) );

		attr = Attribute.Create ( "- something:Pattern" );
		assertNotNull ( attr );
		assertFalse ( attr.isFinal ( ) );
		assertFalse ( attr.isStatic ( ) );
	}
}
