package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;


public class TestUMLClass {

	private UMLClass umlClass;
	private LinkedList<UMLClass> classList = new LinkedList<UMLClass>();
	private UMLClass newAssociatedClass = new UMLClass();
	private UMLClass newSuperClass = new UMLClass();
	private UMLClass newDependClass = new UMLClass();

	@Before
	public void setUp() throws Exception {
		umlClass = new UMLClass();
	}


	@Test
	public void testAddAttribute(){
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue ( a != null );
		assertTrue ( umlClass.addAttribute(a) );
		assertTrue ( umlClass.getAttributes().contains(a));
		
		a = Attribute.Create ( "- size : boolean");
		assertTrue ( a != null );
		assertFalse ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.getAttributes ( ).contains ( a ) );
		assertTrue ( umlClass.getAttributes ( ).size ( ) == 1 );
		
		a = Attribute.Create ( "+ size : int" );
		assertTrue ( a != null );
		assertFalse ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.getAttributes ( ).size() == 1);
		
		Attribute only = umlClass.getAttributes ( ).iterator ( ).next ( );
		
		assertTrue ( only.getName ( ).equals ( "size" ) );
		assertTrue ( only.getType ( ).equals ( "int" ) );
		assertTrue ( only.getAccess ( ).equals ( Access.PRIVATE ) );
		assertFalse ( only.isStatic ( ) );
		assertFalse ( only.isFinal ( ) );
	}

	@Test
	public void testRemoveAttribute() {
		/*
		 * Add attribute to the class, check that it is added.
		 */
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue(a != null);
		assertTrue ( umlClass.addAttribute(a) );
		assertTrue ( umlClass.getAttributes().contains(a));
		
		umlClass.removeAttribute(a);
		assertFalse ( umlClass.getAttributes().contains(a));

		assertTrue ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.removeAttribute ( "size" ));
		
	}



	@Test
	public void testAddMethod( ){
		/*
		 * Add method to the class, check that it is added.
		 */
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
		assertTrue(umlClass.getMethods().size() == 1);
		/*
		 * Add 2nd method to the class, check that it is added.
		 */
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
		assertTrue(umlClass.getMethods().size() == 2);


	}

	@Test
	public void removeMethod( ) {
		/*
		 * Add methods and check that they are added.
		 */
		String str = "+ name():String";
		Method meth1 = Method.Create( str );
		String str2 = "- _type():String";
		Method meth2 = Method.Create( str2 );
		umlClass.addMethod(meth1);
		assertTrue(meth1 != null);
		assertTrue ( umlClass.getMethods().contains(meth1));

		umlClass.addMethod(meth2);
		assertTrue(meth2 != null);
		assertTrue ( umlClass.getMethods().contains(meth2));


		/*
		 * Remove method and check that it is removed.
		 */
		umlClass.removeMethod(meth2);
		assertFalse( umlClass.getMethods().contains(meth2));


	}



	/*
	  public void testGetNumberOfMethods( ) {
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



		umlClass.removeMethod(meth1);
		System.out.println("umlClass.getNumberOfMethods(): " + umlClass.getNumberOfMethods());
		//assertTrue(umlClass.getNumberOfMethods() == 1);

	}
	 */




	@Test
	public void testAddAssociation( ) {
		umlClass.addAssociation(newAssociatedClass);
		classList.add( newAssociatedClass );

		Iterator<UMLClass> it = umlClass.getAssociations().iterator();
		Iterator<UMLClass> itl = classList.iterator();

		while ( it.hasNext() && itl.hasNext() )
			{
				assertTrue ( it.next().equals(itl.next()));
			}

		assertFalse ( it.hasNext() );
		assertFalse ( itl.hasNext() );


	}

	@Test
	public void testRemoveAssociation( ) {
		/*
		 * 
		 * Not yet implemented
		 * 	
		 */

	}



	@Test
	public void testAddSuperclass( ) {

		umlClass.addSuperclass(newSuperClass);
		classList.add(newSuperClass);

		Iterator<UMLClass> it = umlClass.getSuperclasses().iterator();
		Iterator<UMLClass> itl = classList.iterator();

		while ( it.hasNext() && itl.hasNext() )
			{
				assertTrue ( it.next().equals(itl.next()));
			}

		assertFalse ( it.hasNext() );
		assertFalse ( itl.hasNext() );
	}


	@Test
	public void testRemoveSuperclass( ) {
		/*
		 * 
		 * Not yet implemented
		 * 	
		 */
	}

	@Test
	public void addDependency( ) {
		umlClass.addDependency(newDependClass);
		classList.add(newDependClass);

		Iterator<UMLClass> it = umlClass.getDependencies().iterator();
		Iterator<UMLClass> itl = classList.iterator();

		while ( it.hasNext() && itl.hasNext() )
			{
				assertTrue ( it.next().equals(itl.next()));
			}

		assertFalse ( it.hasNext() );
		assertFalse ( itl.hasNext() );
	}

	@Test
	public void removeDependency( ) {
		/*
		 * 
		 * Not yet implemented
		 * 	
		 */
	}


}
