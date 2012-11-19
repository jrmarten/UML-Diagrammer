package edu.uwm.cs361.settings.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import edu.uwm.cs361.settings.json.elements.JSONObject;

public class JSONReader
{
	
	Scanner fin;
	Stack<String> nests;
	JSONObject base;
	
	private JSONReader ( Scanner stream )
	{
		fin = stream;
		nests = new Stack<String> ( );
		
		read();
	}
	
	public JSONObject getData ( )
	{
		return base;
	}

	private void read ( )
	{
		String content = "";
		
		while ( fin.hasNext() )
			{
				content += fin.nextLine();
			}
		
		base = new JSONObject ( content );
	}
	
	
	/*
	private void parse ( String cur )
	{
		int search_index = 0;
		
		while ( search_index != -1 )
			{
				int next_start = cur.indexOf( '{', search_index );
				int next_end = cur.indexOf( '}', search_index );
				
				
			}
		
	}
	
	//*/
	
	/*

	public JSONElement getElement ( String str ) throws JSONParseException
	{
		if ( !str.contains( ":" ) ) throw new JSONParseException ( "Missing JSON Object attribute seporator: :" );
		str = str.trim();
		
		String key = str.substring(0, str.indexOf ( ":" ) ).trim();
		if ( !key.matches( "\"\\w\"") ) throw new JSONParseException ( "Key malformed: missing \" or not made of [A-Za-z0-9]." );
		
		JSONElement ret = new JSONNull ( );
		
		String val = str.substring( str.indexOf(":") + 1 ).trim();
		
		
		
		Class<? extends JSONElement> type = AbstractJSONElement.getType( val );
		
		if ( type.equals ( JSONNull.class ) ) return ret;
		
		//can't parse string.
		if ( type.equals ( JSONObject.class ) ) return ret; 
		
		Class<String> template = String.class;
		
		try
			{
				Constructor<? extends JSONElement> builder = type.getConstructor( template );
				
				return builder.newInstance( val);
				
			} 
			catch ( Exception e )
			{
				e.printStackTrace();
			}
		
		/*
		 exceptions associated with the try block above.
		  
		 catch (SecurityException e)
			{
				e.printStackTrace();
			} catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			} catch (InstantiationException e)
			{\
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			} 
		
		
		
		
		return ret;
	}

	//*/
	
	
	
	public static JSONReader Create( String filename )
	{
		try
		{
			Scanner input = new Scanner ( new File ( filename ) );
			return new JSONReader ( input );
		}
		catch ( FileNotFoundException e )
		{
			return null;
		}
	}
	
	
	/*
	public static class JSONParseException extends Exception
	{
		public JSONParseException ( String msg )
		{
			super ( msg );
		}
	}
	*/
}
