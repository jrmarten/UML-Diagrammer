package edu.uwm.cs361.settings;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import edu.uwm.cs361.Util;

public class Style extends Settings
{
	private String selector;

	public Style ( String name )
	{
		selector = name;
	}

	public String getName( )
	{
		return selector;
	}

	@Override
	public String toString ( )
	{
		return getName ( );
	}

	private static LinkedList<Style> styleMods = new LinkedList<Style> ( );
	public static Style get ( String selector )
	{
		for ( Style s : styleMods )
			{
				if ( s.getName().equals(selector) )
					return s;
			}
		return null;
	}

	static
	{
		init();
	}

	private static void init ( )
	{
		Settings pref = Settings.getGlobal();
		String stylesheet = pref.getString( "StyleSheet", "File not found");
		File cssfile = new File ( stylesheet );

		if ( stylesheet.equals( "File not found") || !cssfile.exists() ) return;

		try
			{
				Scanner in = new Scanner ( cssfile );
				String buffer = "";
				String input = "";
				
				while ( in.hasNext() )
					{
						input = in.nextLine();
						
						if ( input.contains( "//" ) )
							{
								input = input.substring ( 0, input.indexOf( "//" ) );
							}
						if ( input.contains( "/*") )
							{
								input = input.substring( 0, input.indexOf ( "/*" ) );
								String xinput = input.substring( input.indexOf("/*") + 1);
								
								while ( !xinput.contains( "*/" ) )
									{
										xinput = in.nextLine();
									}
								
								input = xinput.substring( input.indexOf( "*/" ) + 3);
							}
						
						
						if ( Util.countInstancesOf(input, '}' ) == 1 )
							{
								if ( Util.countInstancesOf( buffer, '{' ) != 1 )
									{
										Util.dprint( "Stylesheet malformed" );
										input = "";
									}
								buffer += input.substring ( 0, input.indexOf('}' ) + 1);
								Style next = make ( buffer );
								if ( next != null ) styleMods.add( next );
								buffer = input.substring( input.indexOf('}') + 1);
							}
						else
							buffer += input;
					}
				
				
			} catch (FileNotFoundException e)
			{
				Util.dprint( "File not found: Stylesheet" );
			}
		Util.dprint ( styleMods );

	}

	private static Style make ( String in )
	{
		if ( Util.countInstancesOf(in, '{') != 1 ) 
			return (Style) Util.report ( "Error Style definintion: " + in ) ;
		if ( Util.countInstancesOf(in, '}') != 1 ) 
			return (Style) Util.report ( "Error Style definintion: " + in );

		Util.dprint( "Sytle works: " + in );

		String name = in.substring( 0, in.indexOf ( '{' ) );
		String def = in.substring( in.indexOf( '{' ) + 1, in.indexOf('}') );

		name.trim();
		LinkedList<String> li = new LinkedList<String> ( );
		for ( String tmp : name.split ( " " ) )
			{
				li.add( tmp );
			}
		name = Util.join( li, " ");

		Style ret = new Style ( name );

		String[] entries = def.split( ";" );
		for ( String entry : entries )
			{
				String[] parts = entry.split( ":" );
				if ( parts.length != 2 ) continue;

				String key = parts[0].trim();
				String val = parts[1].trim();

				ret.set( key, val);
			}
		return ret;
	}
	public Color getColor ( String key, Color defaultValue ) {
		/**
		 * Does this need to check for "background-color" and "foreground-color"
		 * as how you have it in the style sheet?  I believe the built-in decode function achieves this.
		 
		if(key.contains("0x")){
			
		}
		*/
		//checking for all colors in the Color. library
		if (key.equalsIgnoreCase("red")) return Color.red;
		if (key.equalsIgnoreCase("pink")) return Color.pink;
		if (key.equalsIgnoreCase("orange")) return Color.orange;
		if (key.equalsIgnoreCase("yellow")) return Color.yellow;
		if (key.equalsIgnoreCase("green")) return Color.green;
		if (key.equalsIgnoreCase("magenta")) return Color.magenta;
		if (key.equalsIgnoreCase("cyan")) return Color.cyan;
		if (key.equalsIgnoreCase("blue")) return Color.blue;
		if (key.equalsIgnoreCase("white")) return Color.white;
		if (key.equalsIgnoreCase("lightGray")) return Color.lightGray;
		if (key.equalsIgnoreCase("gray")) return Color.gray;
		if (key.equalsIgnoreCase("darkGray")) return Color.darkGray;
		if (key.equalsIgnoreCase("black")) return Color.black;		
		try { 
			return Color.decode(key); //built-in function of Color class
		}
		catch (Exception nfe) {
			System.out.println("Invalid color code or color: " + key);
			return defaultValue;	
		}
	}

}
