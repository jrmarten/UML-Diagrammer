package edu.uwm.cs361.classdiagram.data;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.Connection.*;

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
	}
}
