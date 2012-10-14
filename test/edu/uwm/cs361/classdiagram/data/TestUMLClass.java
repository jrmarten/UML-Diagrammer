package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TestUMLClass
{

	private UMLClass							umlClass;
	private LinkedList<UMLClass>	classList						= new LinkedList<UMLClass> ( );
	private UMLClass							newAssociatedClass	= new UMLClass ( );
	private UMLClass							newSuperClass				= new UMLClass ( );
	private UMLClass							newDependClass			= new UMLClass ( );

	private UMLClass							foo;
	private UMLClass							bar;
	private UMLClass							widget;
	private UMLClass							abs;

	private UMLClass							colInter;
	private UMLClass							runInter;
	private UMLClass							iterInter;

	@Before
	public void setUp ( ) throws Exception
	{
		umlClass = new UMLClass ( );
		setUpFooBar ( );
		widget = new UMLClass ( "Widget" );
		abs = new AbstractUMLClass ( "AbstractClass" );
		setUpInterfaces ( );
	}

	public void setUpFooBar ( )
	{
		foo = new UMLClass ( "Foo" );
		bar = new UMLClass ( "Bar" );

		foo.addMethod ( Method.Create ( "+ pity (  ) : void" ) );
		foo.addMethod ( Method.Create ( "+ fighter ( ) : Band" ) );
		bar.addMethod ( Method.Create ( "+ drink ( ): void " ) );
		bar.addMethod ( Method.Create ( "+ isLastCall ( ) : boolean " ) );
	}

	public void setUpInterfaces ( )
	{
		colInter = new UMLInterface ( "Collection<E>" );
		runInter = new UMLInterface ( "Runnable" );
		iterInter = new UMLInterface ( "Interator<E>" );

		colInter.addMethod ( Method.Create ( "+ add ( E ): boolean" ) );
		colInter.addMethod ( Method.Create ( "+ iterator ( ) : Iterator<E>" ) );

		runInter.addMethod ( Method.Create ( "+ run (  ) : void" ) );

		iterInter.addMethod ( Method.Create ( "+ hasNext ( ) : boolean" ) );
		iterInter.addMethod ( Method.Create ( "+ next ( ) : E" ) );
		iterInter.addMethod ( Method.Create ( "+ remove ( ) : void " ) );
	}

	@Test
	public void testDeclairation ( )
	{
		assertFalse ( foo.isAbstractClass ( ) );
		assertFalse ( bar.isAbstractClass ( ) );
		assertFalse ( widget.isAbstractClass ( ) );
		assertFalse ( foo.isAbstractClass ( ) );

		assertTrue ( abs.isAbstractClass ( ) );
	}

	@Test
	public void testAddAttribute ( )
	{
		String str = "- size : int";
		Attribute a = Attribute.Create ( str );
		assertTrue ( a != null );
		assertTrue ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.getAttributes ( ).contains ( a ) );

		a = Attribute.Create ( "- size : boolean" );
		assertTrue ( a != null );
		assertFalse ( umlClass.addAttribute ( a ) );
		assertFalse ( umlClass.getAttributes ( ).contains ( a ) );
		assertTrue ( umlClass.getAttributes ( ).size ( ) == 1 );

		a = Attribute.Create ( "+ size : int" );
		assertTrue ( a != null );
		assertFalse ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.getAttributes ( ).size ( ) == 1 );

		Attribute only = umlClass.getAttributes ( ).iterator ( ).next ( );

		System.out.println ( only.getSignature ( ) );

		assertTrue ( only.getName ( ).equals ( "size" ) );
		assertTrue ( only.getType ( ).equals ( "int" ) );
		assertTrue ( only.getAccess ( ).equals ( Access.PRIVATE ) );
		assertFalse ( only.isStatic ( ) );
		assertFalse ( only.isFinal ( ) );
	}

	public void addAttribute ( UMLClass c, String attrstr, boolean contained,
			int size )
	{
		Attribute a = Attribute.Create ( attrstr );
		assertNotNull ( a );
		assertTrue ( c.addAttribute ( a ) != contained );
		assertTrue ( c.getAttributes ( ).contains ( a ) );
	}

	@Test
	public void testRemoveAttribute ( )
	{
		/*
		 * Add attribute to the class, check that it is added.
		 */
		String str = "- size : int";
		Attribute a = Attribute.Create ( str );
		assertTrue ( a != null );
		assertTrue ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.getAttributes ( ).contains ( a ) );

		umlClass.removeAttribute ( a );
		assertFalse ( umlClass.getAttributes ( ).contains ( a ) );

		assertTrue ( umlClass.addAttribute ( a ) );
		assertTrue ( umlClass.removeAttribute ( "size" ) );

	}

	@Test
	public void testAddMethod ( )
	{
		AddMethod ( umlClass, "+ getName():String", false, 1 );
		AddMethod ( umlClass, "- _type():String", false, 2 );
		AddMethod ( umlClass, "+ getName():boolean", true, 2 );
		AddMethod ( umlClass, "+ getName(String):String", true, 3 );
		AddMethod ( umlClass, "- getName(int):String", true, 4 );

		// Checking method properties should not be in a test for a class.

		assertFalse ( umlClass.addMethod ( null ) );
	}

	public void AddMethod ( UMLClass c, String methStr, boolean mult, int size )
	{
		Method m = Method.Create ( methStr );
		assertNotNull ( m );
		assertTrue ( c.addMethod ( m ) != mult ); // TODO: verify
		assertTrue ( c.getMethods ( ).contains ( m ) );// TODO: verify
		assertTrue ( c.getMethods ( ).size ( ) == size );
	}

	@Test
	public void testAbstractMethod ( )
	{
		Method absMeth = Method.Create ( "+ abstract add ( E ): boolean" );

		// test to see if adding an abstract method
		// makes a class abstract
		// and removing it makes it non-abstract
		testAbstractProperty ( foo, false, false );
		foo.addMethod ( absMeth );
		testAbstractProperty ( foo, true, false );
		foo.removeMethod ( absMeth );
		testAbstractProperty ( foo, false, false );

		// test to see if abstract declared class
		// will remain abstract

		testAbstractProperty ( abs, true, true );
		abs.addMethod ( absMeth );
		testAbstractProperty ( abs, true, true );
		abs.removeMethod ( absMeth );
		testAbstractProperty ( abs, true, true );
	}

	public void testAbstractProperty ( UMLClass c, boolean ab, boolean ab_d )
	{
		assertTrue ( c.isAbstract ( ) == ab );
		assertTrue ( c.isAbstractClass ( ) == ab_d );
	}

	@Test
	public void removeMethod ( )
	{
		// Don't have to test add, test remove.
		Method meth1 = Method.Create ( "+ name(): String" );
		Method meth2 = Method.Create ( "+ type():String" );
		assertNotNull ( meth1 );
		assertNotNull ( meth2 );
		umlClass.addMethod ( meth1 );
		umlClass.addMethod ( meth2 );

		assertTrue ( umlClass.removeMethod ( meth1 ) );
		assertFalse ( umlClass.getMethods ( ).contains ( meth1 ) );
		assertTrue ( umlClass.getMethods ( ).contains ( meth2 ) );

		// Maybe extra feature, maybe not.
		// assertTrue ( umlClass.removeMethod ( "type" ) );

		assertTrue ( umlClass.removeMethod ( meth2 ) );
		assertFalse ( umlClass.getMethods ( ).contains ( meth1 ) );
		assertFalse ( umlClass.getMethods ( ).contains ( meth2 ) );

	}

	@Test
	public void testAddAssociation ( )
	{
		umlClass.addAssociation ( newAssociatedClass );
		classList.add ( newAssociatedClass );

		Iterator<UMLClass> it = umlClass.getAssociations ( ).iterator ( );
		Iterator<UMLClass> itl = classList.iterator ( );

		while ( it.hasNext ( ) && itl.hasNext ( ) )
			{
				assertTrue ( it.next ( ).equals ( itl.next ( ) ) );
			}

		assertFalse ( it.hasNext ( ) );
		assertFalse ( itl.hasNext ( ) );

	}

	@Test
	public void testRemoveAssociation ( )
	{
		/*
		 * 
		 * Not yet implemented
		 */

	}

	@Test
	public void testAddSuperclass ( )
	{
		assertTrue ( umlClass.addSuperclass ( foo ) );
		assertFalse ( umlClass.addSuperclass ( bar ) );
		assertTrue ( umlClass.addSuperclass ( runInter ) );
		assertTrue ( umlClass.addSuperclass ( colInter ) );
		assertTrue ( umlClass.addSuperclass ( iterInter ) );

		int interfaces = 0;
		int nonInterfaces = 0;
		for ( UMLClass tmp : umlClass.getSuperclasses ( ) )
			{
				if ( tmp instanceof UMLInterface ) interfaces++;
				else nonInterfaces++;
			}

		assertTrue ( nonInterfaces == 1 );
		assertTrue ( interfaces == 3 );
	}

	@Test
	public void testRemoveSuperclass ( )
	{
		/*
		 * 
		 * Not yet implemented
		 */
	}

	@Test
	public void addDependency ( )
	{
		umlClass.addDependency ( newDependClass );
		classList.add ( newDependClass );

		Iterator<UMLClass> it = umlClass.getDependencies ( ).iterator ( );
		Iterator<UMLClass> itl = classList.iterator ( );

		while ( it.hasNext ( ) && itl.hasNext ( ) )
			{
				assertTrue ( it.next ( ).equals ( itl.next ( ) ) );
			}

		assertFalse ( it.hasNext ( ) );
		assertFalse ( itl.hasNext ( ) );
	}

	@Test
	public void removeDependency ( )
	{
		/*
		 * 
		 * Not yet implemented
		 */
	}

}
