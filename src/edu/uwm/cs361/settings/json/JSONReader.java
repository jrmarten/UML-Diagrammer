package edu.uwm.cs361.settings.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.uwm.cs361.settings.json.elements.JSONObject;

public class JSONReader
{
	
	Scanner fin;
	JSONObject base;
	
	
	private JSONReader ( ) { }
	private JSONReader ( Scanner stream )
	{
		fin = stream;
		read();
	}
	
	public JSONObject getData ( )
	{
		return base;
	}

	public JSONQuerier query ( )
	{
		return new JSONQuerier ( base );
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
	
	public static JSONReader Create( String filename )
	{
		JSONReader ret;
		try
		{
			Scanner input = new Scanner ( new File ( filename ) );
		  ret = new JSONReader ( input );
		}
		catch ( FileNotFoundException e )
		{
			ret = new JSONReader ( );
			ret.base = new JSONObject ( "{\n}" );
		}
		return ret;
	}
}
