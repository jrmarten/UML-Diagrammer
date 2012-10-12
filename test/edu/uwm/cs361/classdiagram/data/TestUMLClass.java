package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

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
		System.out.println(meth1);
		
		
	}
	
	@Test
	public void removeMethod( ) {
		
	}
	
	@Test
	public void renameMethod( ) {
		
	}
	
	@Test
	public void testGetNumberOfMethods( ) {
		
	
	}
	
	
	
	
}
