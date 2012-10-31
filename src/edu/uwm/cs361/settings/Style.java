package edu.uwm.cs361.settings;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
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
	
	/*
	 * kinda weird but should work
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals ( Object o )
	{
		if ( ! ( o instanceof Style ) ) return false;
		
		Style s = (Style)o;
		return s.getName().equals( getName() );
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

	static { init(); }
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
						input = handleComments ( in.nextLine(), in ).trim();
						
						if ( Util.countInstancesOf(input, '}' ) == 1 )
							{
								buffer += input.substring ( 0, input.indexOf('}' ) + 1);
								Style[]  rule = make ( buffer );
								for ( Style next : rule )
									{
										if ( !styleMods.contains( next ) ) styleMods.add( next );
										else
											{
												
												Style existing = styleMods.get( styleMods.indexOf(next));
												for ( String tmpKey : next.props.keySet() )
													{
														existing.set( tmpKey , next.props.get( tmpKey) );
													}
											}
									}
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
	
	private static String handleComments ( String input, Scanner in )
	{
		
		if ( input.contains( "//" ) )
			{
				return input.substring ( 0, input.indexOf( "//" ) );
			}
		if ( input.contains( "/*") )
			{
				String xinput = input.substring( input.indexOf("/*") + 2);
				input = input.substring( 0, input.indexOf ( "/*" ) );
				
				while ( !xinput.contains( "*/" ) )
					{
						if ( !in.hasNext() ) break;
						xinput = in.nextLine();
					}				
				input = xinput.substring( xinput.indexOf( "*/") + 2 );
			}
		
		return input;
	}

	private static Style[] make ( String in )
	{
		if ( Util.countInstancesOf(in, '{') != 1 ) 
			return (Style[]) Util.report ( "Error Style definintion: " + in ) ;
		if ( Util.countInstancesOf(in, '}') != 1 ) 
			return (Style[]) Util.report ( "Error Style definintion: " + in );
		
		String name = in.substring( 0, in.indexOf ( '{' ) );
		String def = in.substring( in.indexOf( '{' ) + 1, in.indexOf('}') );
		HashMap<String, String> map = new HashMap<String, String> ( );
		
		name = name.trim();
//		LinkedList<String> li = new LinkedList<String> ( );
//		for ( String tmp : name.split ( " " ) )
//			{
//				li.add( tmp );
//			}
		
		
		//Set selectors
		String[] ids = name.split( " " );
		Style[] ret = new Style[ids.length];

		for ( int i = 0; i < ids.length; i++ )
			{
				ret[i] = new Style ( ids[i] );
			}
		
		String[] entries = def.split( ";" );
		for ( String entry : entries )
			{
				String[] parts = entry.split( ":" );
				if ( parts.length != 2 ) continue;

				String key = parts[0].trim();
				String val = parts[1].trim();

				map.put( key, val);
			}
		
		for ( Style tmp : ret )
			{
				for ( String key : map.keySet() )
					{
						tmp.set( key, map.get(key));
					}
			}
		
		return ret;
	}
	
	private static final Object[][] COLORS =
		{ 
				{ "red",			Color.red },
				{ "pink",			Color.pink },
				{ "orange",		Color.orange },
				{ "yellow",		Color.yellow },
				{ "green",		Color.green },
				{ "magenta",	Color.magenta },
				{ "cyan",			Color.cyan },
				{ "blue",			Color.blue },
				{ "white",		Color.white },
				{ "black",		Color.black },
				
				//can be spelled both ways in css
				{ "lightgray",	Color.lightGray },
				{ "lightgrey",	Color.lightGray },
				{ "gray",				Color.gray },
				{ "grey",				Color.gray },
				{ "darkgray",		Color.darkGray },
				{ "darkgrey",		Color.darkGray }
		};
	
	public Color getColor ( String key, Color defaultValue ) {
		String val = props.get( key );
		if ( val == null ) return defaultValue; //may be problem later
		
		//for reporting purposes only
		Util.dprint( getName( ) + "." + key + ":" + val );
		
		//cuz I like writing code
		boolean hexNotation = false;
		if ( val.startsWith( "#" ) | (hexNotation = val.startsWith ( "0x" ) ) )
			{
				if ( hexNotation ) val = val.substring( 2 );
				else val = val.substring( 1 );
				
				int hex_val = Integer.parseInt( val, 16);
				
				return new Color ( hex_val );
			}
		
		//simpler checking
		//can add colors by c
		for ( Object[] tmp : COLORS )
			{
				if ( val.equalsIgnoreCase( (String) tmp[0] ) ) return (Color) tmp[1];
			}
		
		return defaultValue;
	}

}
