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
}
