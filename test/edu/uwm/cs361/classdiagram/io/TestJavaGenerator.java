package edu.uwm.cs361.classdiagram.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.classdiagram.data.UMLClass;

public class TestJavaGenerator
{

	UMLClass	umlClass;
	Scanner		in;

	@Before
	public void setUp() throws Exception {
		umlClass = new UMLClass("List<E>");

		umlClass.addAttribute(Attribute.Create("-_size:int"));
		umlClass.addMethod(Method.Create("+size( ):int"));
		umlClass.addMethod(Method.Create("+add(int index ):boolean"));
		umlClass.addMethod(Method.Create("+remove( ):int"));
		umlClass.addMethod(Method.Create("abstract add ( E element ):boolean"));
		umlClass.addMethod(Method.Create("public static main(String[] args ):void"));
		umlClass.addMethod(Method.Create("public static add ( int a, int b ) : boolean"));
	}

	@Test
	public void test() throws FileNotFoundException {
		JavaGenerator.write(".", umlClass);
		File java = new File(umlClass.getName() + ".java");
		assertFalse(java.exists());
		
		Util.dprint(java + " does " + ((java.exists()) ? "" : "not ") + "exist");
		if (umlClass.getName().contains("<"))
			{
				String name = umlClass.getName().substring(0, umlClass.getName().indexOf('<'));
				java = new File(name + ".java");
			}
		Util.dprint(java);
		assertTrue(java.exists());
		in = new Scanner(java);
		LinkedList<String> cont = new LinkedList<String>();
		while (in.hasNext())
			{
				cont.add((in.nextLine() + "\n"));
			}

		System.out.println(cont);

		check(cont, "abstract class List<E>");
		check(cont, "private int _size;");
		check(cont, "public int size()");
		check(cont, "public boolean add(int index)");
		check(cont, "public int remove()");
		check(cont, "default abstract boolean add(E element)");
		check(cont, "public static void main(String[] args)");
		check(cont, "public static boolean add(int a,int b)");

	}

	public void check(Iterable<String> cont, String sig) {
		boolean works = false;
		for (String line : cont)
			{
				line = line.trim();
				if (line.equals(sig))
					{
						works = true;
						break;
					}
			}
		assertTrue(works);
	}

	public void check(String contents, String sig) {
		Pattern pat = Pattern.compile(sig);

		assertTrue(pat.matcher(contents).find());
	}

}
