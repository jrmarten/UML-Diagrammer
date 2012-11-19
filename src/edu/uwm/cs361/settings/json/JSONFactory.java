package edu.uwm.cs361.settings.json;

import java.lang.reflect.Constructor;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.elements.*;

public class JSONFactory
{
	
	private static Object[][] CONTAINER_LIMITS = 
		{
				{ '\"',	'\"', JSONString.class },
				{ '[', 	']',	JSONArray.class },
				{ '{',	'}',	JSONObject.class }
		};
	
	private static final String 		NEG = 				"-?", 
																	NUM = 				"[0-9]+",
																	DECIMAL = 		"\\.?[0-9]*",
																	EXP =					"((e|E)(\\+|-)?)[0-9]+";
	
	
	private static final String JSON_NUMBER_LITERAL = NEG + NUM + DECIMAL + EXP;
	
	
	public Class<? extends JSONElement> getType ( String str )
	{
		str = str.trim();
		if ( str.equalsIgnoreCase("null") ) return JSONNull.class;
		
		if ( str.equalsIgnoreCase("true") ||
				str.equalsIgnoreCase ( "false" ) ) return JSONBoolean.class;
		
		
		if ( str.matches( JSON_NUMBER_LITERAL ) ) return JSONNumber.class;
		
		char start = str.charAt( 0 );
		char end = str.charAt( str.length() - 1 );
		
		for ( Object[] pair : CONTAINER_LIMITS )
			{
				if ( 	pair[0].equals( start ) &&
							pair[1].equals( end ) )
					return (Class<? extends JSONElement>) pair[2];
			}
		
		return JSONNull.class;
	}
	
	/*
	 * @warn key needs to be validated better.
	 */
	public JSONElement create ( String str ) throws JSONParseException
	{
		//if ( !str.contains( ":" ) ) throw new JSONParseException ( "Missing JSON Object attribute separator: :" );
		str = str.trim();
		
		String key = "";
		
		if ( str.contains ( ":" ) )
			{
				key = str.substring(0, str.indexOf ( ":" ) ).trim();
				if ( !key.matches( "\"\\w\"") ) throw new JSONParseException ( "Key malformed: missing \" or not made of [A-Za-z0-9]." );
			}
		
		JSONElement ret = new JSONNull ( );
		
		String val = str.substring( str.indexOf(":") + 1 ).trim();
		
		
		
		Class<? extends JSONElement> type = getType( val );
		
		if ( type.equals ( JSONNull.class ) ) return ret;
		
		//can't parse string.
		if ( type.equals ( JSONObject.class ) ) return ret; 
		
		Class<String> template = String.class;
		
		try
			{
				Constructor<? extends JSONElement> builder = type.getConstructor( template );
				
				return builder.newInstance( val);
				
			} 
			//many exceptions caught
			catch ( Exception e )
			{
				String name = type.toString();
				Util.dprint( "Cannot create class:\t" + 
			name.substring( name.lastIndexOf( '.' ) + 1) );
				Util.dprint( str );
				e.printStackTrace();
			}
		
		return ret;
	}
	
	
}
