package edu.uwm.cs361.settings;

import java.awt.Color;
import java.io.File;

import edu.uwm.cs361.Util;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CSSRule extends Settings
{
	private String	selector;

	public CSSRule(String name) {
		selector = name;
	}

	public String getName() {
		return selector;
	}

	@Override
	public String toString() {
		return getName();
	}

	/*
	 * kinda weird but should work
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CSSRule))
			return false;

		CSSRule s = (CSSRule) o;
		return s.getName().equals(getName());
	}
	
	public Color getColor ( String key, Color defaultValue ) 
	{
		String val = props.get( key );
		if ( val == null ) return defaultValue; 
		
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
		
		for ( Object[] tmp : COLORS )
			{
				if ( val.equalsIgnoreCase( (String) tmp[0] ) ) return (Color) tmp[1];
			}
		
		return defaultValue;
	}

	private static final Object[][]	COLORS	= 
		{
			{ "red", Color.red },
			{ "pink", Color.pink }, 
			{ "orange", Color.orange },
			{ "yellow", Color.yellow }, 
			{ "green", Color.green },
			{ "magenta", Color.magenta }, 
			{ "cyan", Color.cyan },
			{ "blue", Color.blue },
			{ "white", Color.white },
			{ "black", Color.black },

			// can be spelled both ways in css
			{ "lightgray", Color.lightGray }, 
			{ "lightgrey", Color.lightGray },
			{ "gray", Color.gray }, 
			{ "grey", Color.gray },
			{ "darkgray", Color.darkGray }, 
			{ "darkgrey", Color.darkGray } 
		};
	
	public static Settings getSettings(File f) {
		throw new NotImplementedException();
	}
}
