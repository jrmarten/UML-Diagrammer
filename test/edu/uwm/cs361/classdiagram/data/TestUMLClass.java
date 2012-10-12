package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;


public class TestUMLClass {

	private UMLClass umlClass;

	@Before
	public void setUp() throws Exception {
		umlClass = new UMLClass();
	}


	@Test
	public void testAddAttribute(){
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue(a != null);
		System.out.println ( a );
		umlClass.addAttribute(a);
		assertTrue ( umlClass.getAttributes().contains(a));
	}

	@Test
	public void testRemoveAttribute() {
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue(a != null);
		System.out.println ( a );
		umlClass.addAttribute(a);
		assertTrue ( umlClass.getAttributes().contains(a));


		umlClass.removeAttribute(a);
		assertFalse ( umlClass.getAttributes().contains(a));

	}

	@Test
	public void testRenameAttribute(){
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue(a != null);
		System.out.println ( a );
		umlClass.addAttribute(a);
		assertTrue ( umlClass.getAttributes().contains(a));

		String str2 = "- size : double";
		Attribute b = Attribute.Create(str2);
		assertTrue(b != null);
		System.out.println( b );
		umlClass.renameAttribute(a, b);	

		assertFalse( umlClass.getAttributes().contains(a));
		assertTrue( umlClass.getAttributes().contains(b));


	}

	@Test
	public void testAddMethod( ){
		String str = "+ name():String";
		Method meth1 = Method.Create( str );
		umlClass.addMethod(meth1);
		assertTrue(meth1 != null);
		System.out.println(meth1);

		/*
		 * Test all of the method 1 attributes
		 */
		assertTrue(meth1.getName().equals("name"));
		assertTrue(meth1.getType().equals("String"));
		assertTrue(meth1.getAccess() == Access.PUBLIC);
		assertTrue(umlClass.getNumberOfMethods() == 1);

		String str2 = "- _type():String";
		Method meth2 = Method.Create( str2 );
		umlClass.addMethod(meth2);
		assertTrue(meth2 != null);
		System.out.println(meth2);
		/*
		 * Test all of the method 2 attributes
		 */
		assertTrue(meth2.getName().equals("_type"));
		assertTrue(meth2.getType().equals("String"));
		assertTrue(meth2.getAccess() == Access.PRIVATE);
		assertTrue(umlClass.getNumberOfMethods() == 2);


	}

	@Test
	public void removeMethod( ) {

		String str = "+ name():String";
		Method meth1 = Method.Create( str );
		String str2 = "- _type():String";
		Method meth2 = Method.Create( str2 );
		umlClass.addMethod(meth1);
		umlClass.addMethod(meth2);
		assertTrue(meth1 != null);
		assertTrue(meth2 != null);
		System.out.println("meth 1 = " + meth1);
		System.out.println("meth 2 = " + meth2);

		System.out.println(umlClass.getMethods());
		System.out.println(umlClass.getNumberOfMethods());
		//assertTrue(umlClass.getNumberOfMethods() == 2);

		/*
		 * remove a method
		 */

		umlClass.removeMethod(meth1);
		System.out.println(umlClass.getNumberOfMethods());
		//assertTrue(umlClass.getNumberOfMethods() == 1);

		/**
		 * 
		 * testing for the actual method in the UMLClass umlClass
		 * cannot get recognition of the method when checking...
		 * 
		 */


	}

	@Test
	public void renameMethod( ) {
		/*
		 * needs to be fixed
		 * 
		 */

		String str = "+ name():String";
		Method meth1 = Method.Create( str );
		umlClass.addMethod(meth1);
		assertTrue(meth1 != null);
		//System.out.println(meth1);

		/*
		 * Test all of the method 1 attributes
		 */
		assertTrue(meth1.getName().equals("name"));
		String str2 = "- _name():String";
		Method meth2 = Method.Create(str2);

		umlClass.renameMethod(meth1, meth2);


	}

	@Test
	public void testGetNumberOfMethods( ) {
		/*
		 * needs to be fixed
		 * 
		 */
		String str = "+ name():String";
		Method meth1 = Method.Create( str );
		String str2 = "- _type():String";
		Method meth2 = Method.Create( str2 );
		umlClass.addMethod(meth1);
		umlClass.addMethod(meth2);
		assertTrue(meth1 != null);
		assertTrue(meth2 != null);
		System.out.println("meth 1 = " + meth1);
		System.out.println("meth 2 = " + meth2);

		System.out.println("umlClass.getMethods(): " + umlClass.getMethods());
		System.out.println("umlClass.getNumberOfMethods(): " + umlClass.getNumberOfMethods());
		//assertTrue(umlClass.getNumberOfMethods() == 2);

		/*
		 * remove a method, check for correct number of methods updated.
		 */

		umlClass.removeMethod(meth1);
		System.out.println("umlClass.getNumberOfMethods(): " + umlClass.getNumberOfMethods());
		//assertTrue(umlClass.getNumberOfMethods() == 1);

	}

	@Test
	public void testAssociations ( ) {

	}

	
	@Test
	 public void testSuperclass ( ) {
		
	}
	
	
	@Test
	public void testInheritance ( ) {
		
	}
	
	
}
