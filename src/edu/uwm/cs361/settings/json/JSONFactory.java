package edu.uwm.cs361.settings.json;

import java.lang.reflect.Constructor;
import java.util.LinkedList;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.elements.*;

public class JSONFactory
{
	
	private static final boolean DEBUG = false;
	
	private static Object[][] CONTAINER_LIMITS = 
		{
				{ '\"',	'\"', JSONString.class, "String" },
				{ '[', 	']',	JSONArray.class, "Array" },
				{ '{',	'}',	JSONObject.class, "Object" }
		};
	
	private static final String 		NEG = 				"-?", 
																	NUM = 				"[0-9]+",
																	DECIMAL = 		"(\\.[0-9]*)?",
																	EXP =					"((e|E)(\\+|-)?[0-9]+)?";
	
	
	private static final String JSON_NUMBER_LITERAL = NEG + NUM + DECIMAL + EXP;
	
	
	public Class<? extends JSONElement> getType ( String str )
	{
		str = str.trim();
		if ( str.equalsIgnoreCase("null") )
			{
				Util.dprint ( "Null found:\t" + str, DEBUG );
				return JSONNull.class;
			}
		
		if ( str.equalsIgnoreCase("true") ||
				str.equalsIgnoreCase ( "false" ) )
			{
				Util.dprint( "Boolean Literal Found:\t" + str, DEBUG );
				return JSONBoolean.class;
			}
		
		if ( str.matches( JSON_NUMBER_LITERAL ) )
			{
				Util.dprint( "Number Literal Found:\t" + str, DEBUG );
				return JSONNumber.class;
			}
		
		char start = str.charAt( 0 );
		char end = str.charAt( str.length() - 1 );
		
		for ( Object[] pair : CONTAINER_LIMITS )
			{
				if ( 	pair[0].equals( start ) &&
							pair[1].equals( end ) )
					{
						Util.dprint( (String)pair[3] + " Literal Found:\t" + str, DEBUG );
						return (Class<? extends JSONElement>) pair[2];
					}
			}
		
		Util.dprint ( "Could not find a type:\t" + str, DEBUG );
		
		return JSONNull.class;
	}
	
	/*
	 * @warn key needs to be validated better.
	 */
	public JSONElement create ( String str ) throws JSONParseException
	{
		str = str.trim();

		JSONElement ret = JSONObject.NULL;
		
		Class<? extends JSONElement> type = getType( str );
		
		if ( type.equals ( JSONNull.class ) ) return JSONNull.NULL;
		
		
		Class<String> template = String.class;
		
		try
			{
				Constructor<? extends JSONElement> builder = type.getConstructor( template );
				return builder.newInstance( str );
				
			} 
			//many exceptions caught
			catch ( Exception e )
			{
				String name = type.toString();
				Util.dprint( "Cannot create class:\t" + 
				name.substring( name.lastIndexOf( '.' ) + 1) +
				":\t" + str );
				e.printStackTrace();
			}
		
		Util.dprint ( "Unable to parse:\t" + str );
		
		return ret;
	}
	


	public Iterable<String> extractLiterals( String str )
	{
		int list_counter = 0;
		int obj_counter = 0;
		boolean in_str = false;
		String found;
		
		int a = 0;
		
		LinkedList<String> attr = new LinkedList<String>();
		
		for ( int i = 0; i < str.length(); i++ )
			{
				char ch = str.charAt( i );
				
				switch ( ch )
				{
					case '[':
						list_counter++;
						break;
					case ']':
						list_counter--;
						break;
					case '{':
						obj_counter++;
						break;
					case '}':
						obj_counter--;
						break;
					case '\"':
						if ( i != 0 && str.charAt( i - 1 ) != '\\' )
							in_str = !in_str;
				}
				
				if ( ch == ',' && 
						list_counter == 0 &&
						obj_counter == 0 )// && !in_str )
					{
						found = str.substring( a, i ).trim();
						Util.dprint ( "Attribute Found:\t" +found, DEBUG );
						
						attr.add( found );
						a = i + 1;
					}
			}
		
		found = str.substring( a ).trim();
		Util.dprint( "Last:\t" + found, DEBUG );
		if ( !found.trim().isEmpty() ) attr.add( found );
		
		return attr;
	}
}
