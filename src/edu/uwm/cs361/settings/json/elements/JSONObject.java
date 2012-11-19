package edu.uwm.cs361.settings.json.elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.JSONFactory;
import edu.uwm.cs361.settings.json.JSONParseException;

public class JSONObject extends AbstractJSONElement
{
	private HashMap<String, JSONElement> mapping = new HashMap<String, JSONElement> ( );
	
	
	public JSONObject ( String val )
	{
		val = getKey (val ); 	//taking away {} functions the same as removing
													//the "" from keys.
		
		String[] parts = val.split( "," );
		String[] pair;
		
		JSONFactory jFac = new JSONFactory ( );
		
		for ( String part : parts )
			{
				part = part.trim();
				pair = part.split ( ":" );
				
				if ( pair.length != 2 )
					{
						Util.dprint( "Malformed Attribute: " + part );
						continue;
					}
				pair[0] = pair[0].trim();
				pair[1] = pair[1].trim();
				
				
				if ( !isKey ( pair[0] ) )
					{
						Util.dprint( "Malformed Attribute Identifier: " + pair[0] );
						continue;
					}
				
				pair[0] = getKey ( pair[0] );
				
				try
					{
						mapping.put ( pair[0], jFac.create( pair[1] ) );
					} catch (JSONParseException e)
					{
						e.printStackTrace();
					}
			}
	}
	
	@Override
	public String toString ( )
	{
		String buf = "{\n";
		
		
		Iterator<Map.Entry<String, JSONElement>> it;
		it = mapping.entrySet().iterator();
		
		while ( it.hasNext() )
			{
				Map.Entry<String, JSONElement> ent = it.next();
				buf += "\t\""+ent.getKey() + "\" : " + ent.getValue().toString();
				if ( !it.hasNext() ) break;
				buf += ",\n";
			}
		
		buf += "\n}";
		
		return buf;
	}
	
	private static boolean isKey ( String str )
	{
		return str.charAt( 0 ) == '\"' 
				&& str.charAt( str.length() - 1) == '\"';
	}
	
	private static String getKey ( String str )
	{
		return str.substring( 1 ).substring( 0, str.length() - 2 );
	}
	
	@Override
	public Object getElement ( )
	{
		return mapping;
	}
	
	public JSONElement get ( String key )
	{
		return mapping.get ( key );
	}
	
	public void set ( String key, JSONElement e )
	{
		mapping.put ( key, e );
	}
	
	@Override
	public boolean isObject ( )
	{
		return true;
	}
}
