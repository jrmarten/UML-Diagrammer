package edu.uwm.cs361.classdiagram.data;

import java.io.*;

class JavaGenerator
{
    private String tab = "\t";
    private int tab_count = 0;
    private PrintWriter fout;

    private static String[][] defaults =
    { 
	{"byte", "0"
	{"int", "0" },
	{"double", "0.0"},
	{"float", "0.0f"},
	{"short", "0"},
	{"long", "0L"},
	{"char", "0"},
	{"boolean", "false"}
    }

    public JavaGenerator ( File file ) throws FileNotFoundException
    {
	fout = new PrintWriter ( file );
    }
    
    private String get_indent ( )
    {
	StringBuilder sb = new StringBuilder();
	for ( int i = 0; i < tab_count; i++ )
	    {
		sb.append ( tab );
	    }
	return sb.toString();
    }

    public void close ( )
    {
	if ( fout == null ) return;
	fout.close ( );
	fout = null;
    }

    private void write ( String buffer )
    {
	if ( fout == null ) return;
	String[] lines = buffer.split ( "\n" );
	
	for ( String line : lines )
	    {
		fout.println( get_indent() + line.trim() );
		tab_count += (count(line, '{' ) - count(line,'}' ));
	    }
    }

    public static void write ( UMLClass umlclass )
    {
	JavaGenerator genny = new JavaGenerator ( umlclass.getName() + ".java" );
	String start = "\n{\n", end = "\n{\n";

	genny.write ( "class " + umlclass.getName() + start );
	
	for ( Attribute attr : umlclass.getAttributes ( ) )
	    {
		String sig = "";
		sig += attr.getAccess().toString() + " ";
		sig += (attr.isStatic())? "static " : "";
		sig += (attr.isFinal())? "final " : "";
		sig += attr.getType() + " ";
		sig += attr.getName() + ";\n";
		genny.write ( sig );
	    }

	for ( Method meth : umlclass.getMethods ( ) )
	    {
		String sig = "";
		sig += meth.getAccess().toString() + " ";
		sig += (meth.isStatic())? "static " : "";
		sig += (meth.isAbstract())? "abstract " : "";
		sig += meth.getType ( ) + " ";
		sig += meth.getName() + " ( ";
		
		Iterator<String> it = meth.getParameters ( ).iterator() ;
		int index = 0;
		for ( ; it.hasNext() ; )
		    {
			sig += it.next() + " arg" + index;
			if ( it.hasNext() ) break;
			sig += ", ";
		    }
		sig += " )" + start;
		sig += " return " + genny.getDefault ( meth.getType ( ) ) + " ;" + end;
		genny.write ( sig );
	    }

	genny.write ( end );

	genny.close ( );
    }
    
    public String getDefault ( String type )
    {
	type = type.trim();

	for ( String[] pair : defaults )
	    {
		if ( type.equals ( pair[0] ) ) return pair[1];
	    }
	return "null";
    }

    private static int count ( String line, char ch )
    {
	int sum = 0;
	
	for ( int i = 0; i < line.length(); i++ )
		if ( line.charAt(i) == ch ) sum++;
	
	return sum;
    } 
}