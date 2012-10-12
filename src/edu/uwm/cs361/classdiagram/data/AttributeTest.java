package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttributeTest {

	Attribute attr;

	@Test
	public void basicTest() {
		attr = Attribute.Create ( "-_num:int");

		assertFalse ( attr == null );
		assertTrue ( attr.getAccess() == Access.PRIVATE);
		assertTrue ( attr.getName().equals ( "_num" ) );
		assertTrue ( attr.getType().equals ( "int" ) );

		assertFalse ( attr.isFinal() );
		assertFalse ( attr.isStatic() );
	}

	@Test
	public void modTest() {
		attr = Attribute.Create( "+ static final max:int");

		assertFalse ( attr == null );
		assertTrue ( attr.isStatic() );
		assertTrue ( attr.isFinal() );

		attr = Attribute.Create("- final keywords:String[]");

		assertFalse ( attr == null );
		assertTrue ( attr.isFinal() );
		assertFalse ( attr.isStatic() );

		attr = Attribute.Create("- static instances:int");

		assertFalse ( attr == null );
		assertTrue ( attr.isStatic() );
		assertFalse ( attr.isFinal() );
	}

	@Test
	public void nameTest ( ) {
		assertFalse ( Attribute.Create("_name:int") == null );
		assertTrue ( Attribute.Create("1num:int") == null );
		assertFalse ( Attribute.Create("$var:int") == null );
		assertFalse ( Attribute.Create("d12DD:int") == null );
	}

	@Test
	public void testAccess ( ) {
		String sig = "_max:int";

		assertTrue ( Attribute.Create(sig).getAccess() == Access.DEFAULT);
		assertTrue ( Attribute.Create("#" + sig).getAccess() == Access.PROTECTED);
		assertTrue ( Attribute.Create("+" + sig).getAccess() == Access.PUBLIC );
		assertTrue ( Attribute.Create("-" + sig).getAccess() == Access.PRIVATE);
		assertTrue ( Attribute.Create("~" + sig).getAccess() == Access.DEFAULT);

		assertTrue ( Attribute.Create("protected " + sig).getAccess() == Access.PROTECTED);
		assertTrue ( Attribute.Create("public " + sig).getAccess() == Access.PUBLIC );
		assertTrue ( Attribute.Create("private " + sig).getAccess() == Access.PRIVATE);
		assertTrue ( Attribute.Create("default " + sig).getAccess() == Access.DEFAULT);
	}

	@Test
	public void keywordsTest ( ) {

		attr = Attribute.Create("protected:int");
		assertTrue ( attr == null );
		assertTrue ( Attribute.Create("Hello:public") == null);
		attr = Attribute.Create("abstract:int");
		assertTrue ( attr == null );
		attr = Attribute.Create("Abstract:int");
		assertFalse ( attr == null );
	}
}
