package edu.uwm.cs361.settings.json.elements;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
		
		//String[] parts = parseForAttributes( val );
		String[] pair;
		Iterable<String> it = parseForAttributes ( val );
		JSONFactory jFac = new JSONFactory ( );
		
		for ( String part : it )
			{
				//Util.dprint( "parsed Attribute:\t" + part );
				
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
		JSONElement ret = mapping.get( key );
		return (ret==null)? NULL : ret ;
	}
	
	public JSONElement query ( String ref )
	{		
		String next_query = "";
		String cur_ref = ref;
		
		if ( ref.contains( "." ) )
			{
				cur_ref = ref.substring( 0, ref.indexOf( '.' ) ) ;
				next_query = ref.substring( ref.indexOf ( '.' ) + 1 );
			}
		
		int index = getIndex ( cur_ref );
		if ( index == -2 ) return JSONObject.NULL;
		if ( index != -1 ) cur_ref = cur_ref.substring( 0, cur_ref.indexOf( '[' ) );
		
		JSONElement ret = get ( cur_ref );
		
		if ( ret.isArray() && index != -1 )
			{
				ret = ((JSONArray) ret).get( index );
			}
		
		if ( next_query.isEmpty() )
			{
				return ret;
			}
		
		if ( ret.isObject() )
			{
				return ((JSONObject)ret).query ( next_query );
			}
		
		return JSONObject.NULL;
	}
	
	private static int getIndex ( String str )
	{
		int start = str.indexOf( '[' );
		int end = str.indexOf( ']' );
		
		if ( start == -1 && end == -1 )
			{
				return -1;
			}
		if ( start == -1 || end == -1 )
			{
				return -2;
			}
		if ( end < start ) return -2;
		
		String index = str.substring( start + 1, end );
		
		try
		{
			return Integer.parseInt( index );
		}
		catch ( NumberFormatException e )
		{
			return -2;
		}
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

	public static Iterable<String> parseForAttributes ( String str )
	{
		int list_counter = 0;
		int obj_counter = 0;
		boolean in_str = false;
		String found;
		
		int a = 0;
		
		LinkedList<String> attr = new LinkedList<String>();
		
		for ( int i = 0; i < str.length(); i++ )
			{
				char ch = str.charAt( i );
				
				switch ( ch )
				{
					case '[':
						list_counter++;
						break;
					case ']':
						list_counter--;
						break;
					case '{':
						obj_counter++;
						break;
					case '}':
						obj_counter--;
						break;
					case '\"':
						if ( i != 0 && str.charAt( i - 1 ) != '\\' )
							in_str = !in_str;
				}
				
				if ( ch == ',' && 
						list_counter == 0 &&
						obj_counter == 0 )// && !in_str )
					{
						found = str.substring( a, i ).trim();
						Util.dprint ( "Attribute Found:\t" +found, DEBUG );
						
						attr.add( found );
						a = i + 1;
					}
			}
		
		found = str.substring( a ).trim();
		Util.dprint( "Last:\t" + found, DEBUG );
		attr.add( found );
		
		return attr;
	}
}
