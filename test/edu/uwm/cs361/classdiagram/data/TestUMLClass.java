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



		//not going to work.
		//assertTrue((umlClass.getAttributes().toString().contains(str)));
		assertTrue ( umlClass.getAttributes().contains(a));
	}

	@Test
	public void testRemoveAttribute() {

	}

	@Test
	public void testRenameAttribute(){

	}
}
