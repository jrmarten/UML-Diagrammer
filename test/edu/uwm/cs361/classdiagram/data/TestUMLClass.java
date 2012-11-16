package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.dprint;
import static edu.uwm.cs361.Util.printIterable;
import static org.junit.Assert.assertEquals;
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
	private LinkedList<UMLClass>	classList						= new LinkedList<UMLClass>();
/*	private UMLClass							connectingFrom			= new UMLClass();
	private UMLClass							connectingTo				= new UMLClass();*/

	private UMLClass							foo;
	private UMLClass							bar;
	private UMLClass							widget;
	private UMLClass							abs;
	private UMLClass							abstractList;

	private UMLClass							colInter;
	private UMLClass							runInter;
	private UMLClass							iterInter;
	private UMLClass							iterableInter;

	@Before
	public void setUp() throws Exception {
		umlClass = new UMLClass();
		setUpFooBar();
		widget = new UMLClass("Widget");
		abs = new UMLAbstractClass("AbstractClass");
		setUpInterfaces();
	}

	public void setUpFooBar() {
		foo = new UMLClass("Foo");
		bar = new UMLClass("Bar");

		foo.addMethod(Method.Create("+ pity (  ) : void"));
		foo.addMethod(Method.Create("+ fighter ( ) : Band"));
		bar.addMethod(Method.Create("+ drink ( ): void "));
		bar.addMethod(Method.Create("+ isLastCall ( ) : boolean "));
	}

	public void setUpInterfaces() {
		colInter = new UMLInterface("Collection<E>");
		runInter = new UMLInterface("Runnable");
		iterInter = new UMLInterface("Interator<E>");
		iterableInter = new UMLInterface("Iterable<E>");
		abstractList = new UMLAbstractClass("List<E>");

		iterableInter.addMethod(Method.Create("+ iterator ( ) : Iterator<E>"));

		colInter.addSuperclass(iterableInter);
		colInter.addMethod(Method.Create("+ add ( E ): boolean"));
		colInter.addMethod(Method.Create("+ iterator ( ) : Iterator<E>"));

		runInter.addMethod(Method.Create("+ run (  ) : void"));

		iterInter.addMethod(Method.Create("+ hasNext ( ) : boolean"));
		iterInter.addMethod(Method.Create("+ next ( ) : E"));
		iterInter.addMethod(Method.Create("+ remove ( ) : void "));

		abstractList.addSuperclass(colInter);
	}

	@Test
	public void testDeclaration() {
		assertFalse(foo.isAbstractClass());
		assertFalse(bar.isAbstractClass());
		assertFalse(widget.isAbstractClass());
		assertFalse(foo.isAbstractClass());

		assertTrue(abs.isAbstractClass());

		dprint(foo.getDeclaration());
		assertTrue(foo.getDeclaration().equals("class Foo"));
	}

	@Test
	public void testAddAttribute() {
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue(a != null);
		assertTrue(umlClass.addAttribute(a));
		assertTrue(umlClass.getAttributes().contains(a));

		a = Attribute.Create("- size : boolean");
		assertTrue(a != null);
		assertFalse(umlClass.addAttribute(a));
		assertFalse(umlClass.getAttributes().contains(a));
		assertTrue(umlClass.getAttributes().size() == 1);

		a = Attribute.Create("+ size : int");
		assertTrue(a != null);
		assertFalse(umlClass.addAttribute(a));
		assertTrue(umlClass.getAttributes().size() == 1);

		Attribute only = umlClass.getAttributes().iterator().next();

		dprint(only.getSignature());

		assertTrue(only.getName().equals("size"));
		assertTrue(only.getType().equals("int"));
		assertTrue(only.getAccess().equals(Access.PRIVATE));
		assertFalse(only.isStatic());
		assertFalse(only.isFinal());

		assertFalse(umlClass.addAttribute(null));
	}

	public void addAttribute(UMLClass c, String attrstr, boolean contained,
			int size) {
		Attribute a = Attribute.Create(attrstr);
		assertNotNull(a);
		assertTrue(c.addAttribute(a) != contained);
		assertTrue(c.getAttributes().contains(a));
	}

	@Test
	public void testRemoveAttribute() {
		/*
		 * Add attribute to the class, check that it is added.
		 */
		String str = "- size : int";
		Attribute a = Attribute.Create(str);
		assertTrue(a != null);
		assertTrue(umlClass.addAttribute(a));
		assertTrue(umlClass.getAttributes().contains(a));

		umlClass.removeAttribute(a);
		assertFalse(umlClass.getAttributes().contains(a));

		assertTrue(umlClass.addAttribute(a));

	}

	@Test
	public void testAddMethod() {
		AddMethod(umlClass, "+ getName():String", false, true, 1);
		AddMethod(umlClass, "- _type():String", false, true, 2);
		AddMethod(umlClass, "+ getName():boolean", true, false, 2);
		AddMethod(umlClass, "+ getName(str : String):String", false, true, 3);
		AddMethod(umlClass, "- getName(int index):String", false, true, 4);

		// Checking method properties should not be in a test for a class.

		assertFalse(umlClass.addMethod(null));
	}

	public void AddMethod(UMLClass c, String methStr, boolean isIn, boolean inserted, int size) {
		Method m = Method.Create(methStr);
		assertNotNull(m);
		assertTrue(c.addMethod(m) != isIn); // TODO: verify
		dprint(m.toString(), false);

		dprint("\nMethod List", false);
		printIterable(c.getMethods(), false);
		assertTrue(c.getMethods().contains(m) == inserted);
		assertTrue(c.getMethods().size() == size);
	}

	@Test
	public void testAbstractMethod() {
		Method absMeth = Method.Create("+ abstract add ( E ): boolean");

		// test to see if adding an abstract method
		// makes a class abstract
		// and removing it makes it non-abstract
		testAbstractProperty(foo, false, false);
		foo.addMethod(absMeth);
		testAbstractProperty(foo, true, false);
		foo.removeMethod(absMeth);
		testAbstractProperty(foo, false, false);

		// test to see if abstract declared class
		// will remain abstract

		testAbstractProperty(abs, true, true);
		abs.addMethod(absMeth);
		testAbstractProperty(abs, true, true);
		abs.removeMethod(absMeth);
		testAbstractProperty(abs, true, true);
	}

	public void testAbstractProperty(UMLClass c, boolean ab, boolean ab_d) {
		assertTrue(c.isAbstract() == ab);
		assertTrue(c.isAbstractClass() == ab_d);
	}

	@Test
	public void testRemoveMethod() {
		// Don't have to test add, test remove.
		Method meth1 = Method.Create("+ name(): String");
		Method meth2 = Method.Create("+ type():String");
		assertNotNull(meth1);
		assertNotNull(meth2);
		umlClass.addMethod(meth1);
		umlClass.addMethod(meth2);

		assertTrue(umlClass.removeMethod(meth1));
		assertFalse(umlClass.getMethods().contains(meth1));
		assertTrue(umlClass.getMethods().contains(meth2));

		// Maybe extra feature, maybe not.
		// assertTrue ( umlClass.removeMethod ( "type" ) );

		assertTrue(umlClass.removeMethod(meth2));
		assertFalse(umlClass.getMethods().contains(meth1));
		assertFalse(umlClass.getMethods().contains(meth2));

	}

	@Test
	public void testConnection() {
		UMLClass origin = new UMLClass();
		UMLClass classes[] = new UMLClass[4];
		Connection cons[] = new Connection[4];
		
		for(int i = 0; i < 4; i++) {
			classes[i] = new UMLClass();
			cons[i] = new Connection(origin, classes[i]);
		}
		
		/*
		 * Check basic add/remove functionality
		 */
		assertTrue(origin.addConnection(cons[0]));
		cons[0].register();
		assertTrue(origin.addConnection(cons[1]));
		cons[1].register();
		
		LinkedList<Connection> connections = origin.getConnections();
		
		assertTrue(connections.contains(cons[0]));
		assertTrue(connections.contains(cons[1]));
		assertFalse(connections.contains(cons[2]));
		assertFalse(connections.contains(cons[3]));

		assertTrue(origin.removeConnection(cons[0]));
		assertTrue(origin.removeConnection(cons[1]));
		assertFalse(origin.removeConnection(cons[2]));
		assertFalse(origin.removeConnection(cons[3]));
		
		assertFalse(connections.contains(cons[0]));
		assertFalse(connections.contains(cons[1]));
		
		/*
		 * Check contains by type
		 */
		cons[0].setConnectionType(origin, ConnectionType.AGGREGATION);
		cons[1].setConnectionType(origin, ConnectionType.ASSOCIATION);
		cons[2].setConnectionType(origin, ConnectionType.COMPOSITION);
		cons[3].setConnectionType(origin, ConnectionType.DEPENDENCY);
		for (int i = 0; i < 4; i++) {
			assertTrue(classes[i].addConnection(cons[i]));
			cons[i].register();
		}
		
		LinkedList<Connection> aggConnections = origin.getConnections(ConnectionType.AGGREGATION);
		LinkedList<Connection> assConnections = origin.getConnections(ConnectionType.ASSOCIATION);
		LinkedList<Connection> compConnections = origin.getConnections(ConnectionType.COMPOSITION);
		LinkedList<Connection> depConnections = origin.getConnections(ConnectionType.DEPENDENCY);
		
		assertTrue(aggConnections.contains(cons[0]));
		assertEquals(1, aggConnections.size());
		assertTrue(assConnections.contains(cons[1]));
		assertEquals(1, assConnections.size());
		assertTrue(compConnections.contains(cons[2]));
		assertEquals(1, compConnections.size());
		assertTrue(depConnections.contains(cons[3]));
		assertEquals(1, depConnections.size());
		
	}

	public <E> void checkItEqual(Iterable<E> a, Iterable<E> b) {
		Iterator<E> ait = a.iterator();
		Iterator<E> bit = b.iterator();

		while (ait.hasNext() && bit.hasNext())
			{
				assertTrue(ait.next().equals(bit.next()));
			}

		assertFalse(ait.hasNext());
		assertFalse(bit.hasNext());
	}
}
