package edu.uwm.cs361.uml;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

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
	}

	@Test
	public void testRemoveAttribute() {

	}

	@Test
	public void testRenameAttribute(){

	}



	public static junit.framework.Test suite() { 
		return new JUnit4TestAdapter(UMLClass.class); 
	}
}
