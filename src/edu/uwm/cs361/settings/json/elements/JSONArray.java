package edu.uwm.cs361.settings.json.elements;

import java.util.Iterator;
import java.util.LinkedList;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.JSONFactory;
import edu.uwm.cs361.settings.json.JSONParseException;

public class JSONArray extends AbstractJSONElement implements Iterable<JSONElement>
{
	private LinkedList<JSONElement> _val = new LinkedList<JSONElement> ( );
	
	public JSONArray ( String val )
	{
		val = val.trim();
		int len = val.length();
		val= val.substring(1).substring( 0, len - 2);
		
		JSONFactory jFact = new JSONFactory ( );
		
		for ( String element : jFact.extractLiterals( val ) )
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
	
	public JSONArray ( JSONElement... elements )
	{
		for ( JSONElement e : elements )
			{
				_val.add( e );
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
	
	public JSONElement get ( int i )
	{
		if ( i < 0 || i >= _val.size() ) return JSONNull.NULL;
		
		return _val.get( i );
	}
	
	@Override
	public Object getElement ( )
	{
		return _val.iterator();
	}
	
	@Override
	public Iterator<JSONElement> iterator()
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
