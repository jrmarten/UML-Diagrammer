package edu.uwm.cs361.settings.json.elements;

import java.util.Iterator;
import java.util.LinkedList;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.JSONFactory;
import edu.uwm.cs361.settings.json.JSONParseException;

public class JSONArray extends AbstractJSONElement
{
	private LinkedList<JSONElement> _val = new LinkedList<JSONElement> ( );
	
	public JSONArray ( String val )
	{
		int len = val.length();
		String trimmed = val.substring(1).substring( 0, len - 1);
		
		JSONFactory jFact = new JSONFactory ( );
		
		for ( String element : trimmed.split( "," ) )
			{
				element = element.trim();
				
				try
					{
						_val.add( jFact.create( element ) );
						
					} catch (JSONParseException e)
					{
						Util.dprint( "Error Creating Array: " + element );
						e.printStackTrace();
					}
			}
	}
	
	@Override 
	public String toString ( )
	{
		String buf = "[ ";
		
		Iterator<JSONElement> it = _val.iterator();
		
		while ( it.hasNext() )
			{
				buf += it.next();
				if ( !it.hasNext() ) break;
				
				buf += ", ";
			}
		
		buf += " ]";
		
		return buf;
		
	}
	
	@Override
	public Object getElement ( )
	{
		return _val.iterator();
	}
	
	@Override
	public boolean isArray ( )
	{
		return true;
	}
	
	public static boolean isLiteral ( String lit )
	{
		return lit.charAt( 0 ) == '[' && lit.charAt( lit.length() -1 ) == ']';
	}
}
