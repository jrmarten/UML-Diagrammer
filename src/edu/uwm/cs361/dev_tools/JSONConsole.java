package edu.uwm.cs361.dev_tools;

import java.util.Scanner;

import edu.uwm.cs361.settings.format.CCScopeFormatter;
import edu.uwm.cs361.settings.format.Expansion;
import edu.uwm.cs361.settings.format.FormatterPipe;
import edu.uwm.cs361.settings.format.StringFormatter;
import edu.uwm.cs361.settings.json.JSONQuerier;
import edu.uwm.cs361.settings.json.JSONReader;
import edu.uwm.cs361.settings.json.elements.JSONElement;
import edu.uwm.cs361.settings.json.elements.JSONObject;

public class JSONConsole
{
	
	FormatterPipe filters = new FormatterPipe ( );
	Expansion exp = new Expansion ( );
	JSONQuerier search;
	
	public JSONConsole ( JSONQuerier file )
	{
		filters.add( new CCScopeFormatter ( ) );
		filters.add( exp );
		filters.add( new StringFormatter ( ) );
		search = file;
	}
	
	public String command ( String in )
	{
		if ( in.startsWith( "type " ) )
			return getType ( in.substring( 5 ) );
		
		if ( in.startsWith( "ls " ) )
			return getAttributeNames ( in.substring( 3 ) );
		
		return getVal ( in );
	}
	
	public String getAttributeNames ( String var )
	{
		JSONElement cur = search.query( var );
		
		if ( !cur.isObject() ) return "None";
		
		String buffer = "";
		for ( String attr : ((JSONObject)cur).getAttributes() )
			{
				buffer += attr + '\n';
			}
		
		return buffer;
	}
	
	public String getType ( String var )
	{
		String name = search.query(
				var.substring( var.indexOf( ' ' ) + 1 )
				).getClass().getCanonicalName();
		return name.substring( name.lastIndexOf( '.' ) + 1 );
	}
	
	public String getVal ( String var )
	{
		return filters.format ( search.query( var ).toString() );
	}
	
	public static void main ( String[] args )
	{
		
		Scanner stdin = new Scanner ( System.in );
		String input = "";
		
		System.out.println ( "JSON file: " );
		
		JSONReader reader = JSONReader.Create( stdin.nextLine() );
		
		JSONConsole console = new JSONConsole ( reader.query() );
		
		while ( true )
			{
				input = stdin.nextLine();
				if ( input.toLowerCase().equals( "quit" )||
						input.toLowerCase().equals( "exit" ) )
					break;
				
				System.out.println ( console.command( input) );
			}
	}
}
