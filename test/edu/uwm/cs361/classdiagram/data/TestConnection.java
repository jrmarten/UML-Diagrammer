package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.connection.Multiplicity;

public class TestConnection
{
	UMLClass class_a = new UMLClass();
	UMLClass class_b = new UMLClass();
	UMLClass newClass = new UMLClass();
	Connection oneCon = new Connection(null,null);
	Connection twoCon = new Connection(null,null);
	ConnectionType oneType, twoType;
	
	
	@Test
	public void testConnections(){
		//Adding attributes before making connections
		
		Attribute attr;
		attr = Attribute.Create ( "- _name:String" );
		class_a.addAttribute(attr);
		System.out.println(class_a.getAttributes());
		assertTrue(class_a.myAttributes != null);
		
		//Adding methods before making connections
		String meth = "+getName() : String";
		class_a.myMethods.add(Method.Create(meth));
		System.out.println(class_a.getMethods());
		assertTrue(class_a.myMethods != null);
		
		//Adding names before making connections
		
		class_a.setName("Ayyyyyyy");
		class_b.setName("Beeeeeee");
		assertTrue(class_a.myName.equalsIgnoreCase("Ayyyyyyy"));
		
		
		//No connections initially
		assertTrue(newClass.getConnections().isEmpty());
		oneCon = new Connection(class_b, class_b);
		newClass.addConnection(oneCon);
		
		//Connections have been added and are tested for
		System.out.println(class_a.getConnections());
		assertFalse(newClass.getConnections().isEmpty());
		assertTrue(newClass.getConnections() != null);
		
		//Test remove connections
		newClass.removeConnection(oneCon);
		assertTrue(newClass.getConnections().isEmpty());
		
		
	}
	
	@Test
	public void testConnectionType(){
		//still need to implement, keep getting null pointer exception.
		oneCon = new Connection(newClass, class_b);
		assertFalse(newClass.getConnections().contains(oneCon));
		
		newClass.addConnection(oneCon);
		assertTrue(newClass.getConnections().contains(oneCon));
		oneType = ConnectionType.AGGREGATION;
		oneCon.setConnectionType(newClass, oneType);
		assertTrue(newClass._cons.contains(oneCon));
		assertFalse(newClass._cons.contains(twoCon));
		
		//oneType == AGGREGATION
		assertTrue(oneCon.getConnectionType(newClass).equals(oneType)); 
		assertTrue(oneCon.getConnectionType(newClass).equals(ConnectionType.AGGREGATION));
		
		twoCon = new Connection(newClass, class_a);
		assertFalse(newClass.getConnections().contains(twoCon));
		assertTrue(newClass.getConnections().contains(oneCon));
		newClass.addConnection(twoCon);
		twoType = ConnectionType.COMPOSITION;
		twoCon.setConnectionType(newClass,twoType);
		
		//twoType == COMPOSITION
		assertTrue(newClass.getConnections().contains(twoCon));
		assertTrue(newClass.getConnections().getLast().getConnectionType(newClass).equals(ConnectionType.COMPOSITION));
		
		//remove the first connection, leaving only twoType == COMPOSITION
		newClass.removeConnection(oneCon);
		assertFalse(newClass.getConnections().contains(oneCon));
		assertTrue(newClass.getConnections().getFirst().getConnectionType(newClass).equals(ConnectionType.COMPOSITION));
	}
	
	@Test
	public void testMultiplicity ( )
	{
		Multiplicity mult = Multiplicity.Create( "0-2,5-7,9-15" );
		
		Util.dprint( "<" + mult + ">" );
		
		for ( int i = 0; i < 3; i++ )
			{
				assertTrue ( mult.inRange ( i ) );
			}
		
		assertFalse ( mult.inRange( -1 ) );
		assertFalse ( mult.inRange( 3 ) );
		
		mult = Multiplicity.Create( "*" );
		assertTrue ( mult.inRange( 500 ) );
		
		mult = Multiplicity.Create( "5-*" );
		for ( int i = 0; i < 5; i++ )
			{
				assertFalse ( mult.inRange ( i ) );
			}
		for ( int i = 5; i < 100;i++ )
		{
			assertTrue ( mult.inRange( i ) ); 
		}
	}
}
