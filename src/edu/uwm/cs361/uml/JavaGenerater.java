package edu.uwm.cs361.uml;

import java.io.*;

class JavaGenerator
{
    private String tab = "\t";
    private int tab_count = 0;

    private PrintWriter fout;

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

    public void write ( String buffer )
    {
	String[] lines = buffer.split ( "\n" );
	
	for ( String line : lines )
	    {
		fout.println( get_indent() + line.trim() );
		tab_count += (count(line, '{' ) - count(line,'}' ));
	    }
    }

    

    public static int count ( String line, char ch )
    {
	int sum = 0;
	
	for ( int i = 0; i < line.length(); i++ )
		if ( line.charAt(i) == ch ) sum++;
	
	return sum;
    } 


}