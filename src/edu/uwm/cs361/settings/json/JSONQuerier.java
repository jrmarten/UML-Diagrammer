package edu.uwm.cs361.settings.json;

import java.util.LinkedList;
import java.util.Queue;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.elements.JSONArray;
import edu.uwm.cs361.settings.json.elements.JSONElement;
import edu.uwm.cs361.settings.json.elements.JSONObject;

public class JSONQuerier
{
	
	JSONElement root;
	JSONElement cur;
	Queue<Dereference> queries;
	
	public JSONQuerier ( JSONObject obj )
	{
		root = obj;
		if ( root == null ) root = JSONObject.NULL;
		queries = new LinkedList<Dereference>();
	}
	
	public JSONElement query ( String request )
	{
		cur = root;
		loadQueries ( request );
		
		while ( !queries.isEmpty() )
			{
				cur = handle_lookup ( queries.poll() );
				if ( cur == JSONObject.NULL ) break;
			}
			
		return cur;
	}
	
	private JSONElement handle_lookup ( @Nullable Dereference deref )
	{
		if ( deref == null ) return JSONObject.NULL;
		
		if ( deref.type == DereferenceType.OBJECT )
			{
				if ( !cur.isObject() ) return JSONObject.NULL;
				JSONObject base = (JSONObject) cur;
				return base.get( deref.val );
			}
		
		if ( deref.type == DereferenceType.ARRAY )
			{
				if ( !cur.isArray() ) return JSONObject.NULL;
				JSONArray base = (JSONArray) cur;
				int index = Integer.parseInt( deref.val );
				return base.get( index );
			}
		
		return JSONObject.NULL;
	}
	
	
	private void loadQueries ( String request )
	{
		int deref_index = -1;
		int index_index = -1;
		
		Dereference next;
		
		int use_index;
		boolean deref_used = false;
		
		while ( true )
			{
				deref_index = request.indexOf ( '.' );
				index_index = request.indexOf( '[', 1 );
				
				if ( deref_index == -1 && index_index < 1 ) break;
				
				deref_used = (deref_index < index_index && deref_index != -1) || index_index < 1;
				if ( deref_used )
					use_index = deref_index;
				else
					use_index = index_index;
				
				next = getDeref ( request.substring(0, use_index).trim() );
				
				if ( next == null ) Util.dprint( "Malformed Query" );
				queries.offer( next );
				
				request = request.substring( use_index + ((deref_used)?1:0) );
			}
		
		queries.add( getDeref ( request.trim() ) );
	}
	
	private Dereference getDeref ( String query )
	{
		Dereference ret = new Dereference ( );
		ret.type = DereferenceType.OBJECT;
		ret.val = query;
		
		if ( query.startsWith( "[" ) ) 
			{
				ret.type = DereferenceType.ARRAY;
				String inside = query.substring( 
							query.indexOf( '[' ) + 1,
							query.indexOf( ']' )
						);
				inside = inside.trim();
				
				if ( !inside.matches( "[0-9]" ) ) return null;
				ret.val = inside;
			}
		
		return ret;
	}
	
	private static enum DereferenceType { 
		OBJECT, ARRAY
	}
	
	private static class Dereference 
	{
		public String val;
		public DereferenceType type;
	}
}
