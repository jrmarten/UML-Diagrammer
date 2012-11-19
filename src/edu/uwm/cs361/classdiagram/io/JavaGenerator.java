package edu.uwm.cs361.classdiagram.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Connection;
import edu.uwm.cs361.classdiagram.data.ConnectionType;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.classdiagram.data.UMLClass;

public class JavaGenerator
{
	private String						tab									= "\t";
	private int								tab_count						= 0;
	private PrintWriter				fout;
	
	private static String 		collection 					= null;
	
	
	private static String[][]	defaults	= { 
				{ "byte", "0" }, 
				{ "int", "0" },
				{ "double", "0.0" }, 
				{ "float", "0.0f" }, 
				{ "short", "0" },
				{ "long", "0L" }, 
				{ "char", "0" }, 
				{ "boolean", "false" }, 
				{ "void", "" } 
			};

	public JavaGenerator(File file) throws FileNotFoundException
	{
		fout = new PrintWriter(file);
	}

	private String get_indent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tab_count; i++)
			{
				sb.append(tab);
			}
		return sb.toString();
	}

	public void close() {
		if (fout == null)
			return;
		fout.close();
		fout = null;
	}

	private void write(String buffer) {
		if (fout == null)
			return;
		String[] lines = buffer.split("\n");

		for (String line : lines)
			{
				tab_count += -count(line, '}');
				fout.println(get_indent() + line.trim());
				tab_count += (count(line, '{'));
			}
	}

	public static String getFileName(String name) {
		if (name.contains("<"))
			{
				name = name.substring(0, name.indexOf('<'));
			}
		return name + ".java";
	}

	public static String getCollection ( )
	{
		if ( collection == null )
			collection = UMLApplicationModel.getProjectSettings().getString("Collection", "Collection" );
		return collection;
	}
	
	public static void write(String directiory, UMLClass umlclass) {

		File java_src;
		JavaGenerator genny;
		
		getCollection();

		try
			{

				java_src = new File(directiory + System.getProperty("file.separator")
						+ getFileName(umlclass.getName()));
				genny = new JavaGenerator(java_src);
			} catch (Exception e)
			{
				UMLApplicationModel.error("file.JavaGeneration.error", "IO Error");
				return;
			}

		Util.dprint(java_src.getAbsolutePath());

		String start = "\n{\n", end = "\n}\n\n";

		genny.write(umlclass.getDeclaration() + start);

		for (Attribute attr : umlclass.getAttributes())
			{
				String sig = attr.getSignature();
				sig += ";\n\n";
				genny.write(sig);
			}
		
		int i = 0;
		for ( Connection tmp : umlclass.getConnections( ConnectionType.COMPOSITION ) )
			{
				String sig = "private " + tmp.getOther( umlclass ).getName() + " ";
				
				if ( tmp.getRole( umlclass ).equals( "" ) )
						sig += "comp" + i++;
				else sig += tmp.getRole( umlclass );
				
				genny.write( sig + ";\n\n" );
			}
		
		i = 0;
		for ( Connection tmp : umlclass.getConnections( ConnectionType.AGGREGATION ) )
			{
				String sig = "private " + collection + "<" + tmp.getOther( umlclass ).getName() + "> ";
				
				if ( tmp.getRole( umlclass ).equals( "" ) )
					sig += "agg" + i;
				else sig += tmp.getRole( umlclass );
				
				genny.write( sig + ";\n\n" );
			}

		for (Method meth : umlclass.getMethods())
			{
				String sig = meth.getSignature();
				sig += start;
				sig += " return " + genny.getDefault(meth.getType()) + ";" + end;
				genny.write(sig);
			}

		genny.write(end);

		genny.close();
	}

	public String getDefault(String type) {
		type = type.trim();

		for (String[] pair : defaults)
			{
				if (type.equals(pair[0]))
					return pair[1];
			}
		return "null";
	}

	private static int count(String line, char ch) {
		int sum = 0;

		for (int i = 0; i < line.length(); i++)
			if (line.charAt(i) == ch)
				sum++;

		return sum;
	}
}