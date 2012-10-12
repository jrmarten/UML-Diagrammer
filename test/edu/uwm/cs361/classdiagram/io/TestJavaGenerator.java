package edu.uwm.cs361.classdiagram.io;

import static org.junit.Assert.*;

import java.io.File;

import edu.uwm.cs361.classdiagram.data.*;

import org.junit.Before;
import org.junit.Test;

public class TestJavaGenerator {

	UMLClass umlClass;

	@Before
	public void setUp() throws Exception {
		umlClass = new UMLClass ( "List");

		umlClass.addAttribute(Attribute.Create("-_size:int"));
		umlClass.addMethod(Method.Create("+size( ):int"));
		umlClass.addMethod(Method.Create("+add(int):boolean" ) );
		umlClass.addMethod(Method.Create ( "+remove( ):int"));
		umlClass.addMethod ( Method.Create ( "abstract + add ( E )" ));
	}

	@Test
	public void test() {
		JavaGenerator.write( umlClass );
		File java = new File ( umlClass.getName() + ".java" );
		assertTrue ( java.exists() );

	}

}
