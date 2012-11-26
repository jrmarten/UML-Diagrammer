package edu.uwm.cs361.settings.json.elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.JSONFactory;
import edu.uwm.cs361.settings.json.JSONParseException;

public class JSONObject extends AbstractJSONElement
{
	private HashMap<String, JSONElement> mapping = new HashMap<String, JSONElement> ( );
	
	public final static boolean DEBUG = false;
	
	public JSONObject ( String val )
	{
		val = val.trim();
		val = getKey ( val ); 	//taking away {} functions the same as removing
													  //the "" from keys.
		val = val.trim();
		
		String[] pair;				
				
		JSONFactory jFac = new JSONFactory ( );
		
		for ( String part : jFac.extractLiterals( val ) )
			{
				if ( !part.contains( ":" ) )
					{
						Util.dprint( "Parse error no colon: " + part );
						continue;
					}
				part = part.trim();
				pair = part.split ( ":", 2 );
				
				pair[0] = pair[0].trim();
				pair[1] = pair[1].trim();
				
				
				if ( !isKey ( pair[0] ) )
					{
						Util.dprint( "Malformed Attribute Identifier: " + pair[0] );
						continue;
					}
				
				pair[0] = getKey ( pair[0] );
				
				Util.dprint( "Data\t" + pair[0] + " : " + pair[1], DEBUG );
				
				try
					{
						if ( pair[0].equals( "this" ) )
							Util.dprint ( "May not set \"this\" in an object" );
						else mapping.put ( pair[0], jFac.create( pair[1] ) );
					} catch (JSONParseException e)
					{
						e.printStackTrace();
					}
			}
	}
	
	@Override
	public String toString ( )
	{
		String buf = "\n{\n";
		
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
		if ( key.equals( "this" ) ) return this;
		JSONElement ret = mapping.get( key );
		return (ret==null)? NULL : ret ;
	}
	
	public Set<String> getAttributes ( )
	{
		return mapping.keySet();
	}
	
	public void set ( String key, JSONElement e )
	{
		if ( key.equals( "this" ) ) return;
		mapping.put ( key, e );
	}
	
	@Override
	public boolean isObject ( )
	{
		return true;
	}
}
