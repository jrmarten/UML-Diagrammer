package edu.uwm.cs361.settings.format;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import edu.uwm.cs361.Util;

/*
 * Used for bash like parameter expansion
 */

public class Expansion extends AbstractFormatter
{

	private HashMap<String, String> globals;
	private Stack<HashMap<String, String>> scopes = 
			 new Stack<HashMap<String, String>> ( );
	
	
	public Expansion ( )
	{
		this ( new HashMap<String, String> ( ) );
	}
	
	public Expansion ( HashMap<String, String> map )
	{
		globals = map;
	}
	
	public void startScope ( ) { scopes.push( new HashMap<String, String> ( ) ); }
	public void endScope ( ) { scopes.pop(); }
	
	public String get ( String variable )
	{
		for ( int i = scopes.size() - 1; i >= 0; i-- )
			{
				String val = scopes.elementAt( i ).get( variable );
				if ( val != null ) return val;
			}
		return globals.get( variable );
	}
	
	public void set ( String ref, String val )
	{
		if ( scopes.isEmpty() ) globals.put( ref , val );
		else scopes.peek().put( ref, val );
	}
	
	
	//TODO: update with stacks
	public String dir ( ) { return dir ( false ); }
	public String dir ( boolean verbose )
	{
		StringBuilder sb = new StringBuilder ( );
		
		for ( Map.Entry<String, String> var : globals.entrySet() )
			{
				sb.append( var.getKey() );
				if ( verbose )
					sb.append( " : " + var.getValue() );
		
				sb.append( '\n' );
			}
		
		for ( Map<String, String> scope : scopes )
			{
				for ( Map.Entry<String, String> var : scope.entrySet() )
					{
						sb.append( var.getKey() );
						if ( verbose )
							sb.append( " : " + var.getValue() );
				
						sb.append( '\n' );
					}
			}
		return sb.toString();
	}
	
	/*TODO: fix bug.
	 * 
	 * @Bug escaped reference will be replaced if
	 * another of the same reference is referenced.
	 * (reference. . . Reference! . . REFERENCE!!!)
	 * 
	 */
	
	@Override
	public String format ( String str )
	{
		int var_index = str.indexOf( '$' );
		boolean skipped = false;
		
		while ( var_index != -1 )
			{
				if ( skipped = !escaped ( str, var_index ) )
					{
						
						String key = getReference ( str, var_index);
						String val = get( key );
						
						if ( val == null )
							{
								Util.dprint ( key + " is unset" );
								val = "";
							}
						
						if ( str.charAt( var_index + 1) == '{' ) key = "{" + key + "}"; 
						key = '$' + key;
						
						str = str.replace ( key, val );
						
						var_index = str.indexOf( '$', var_index );
					}
				var_index = str.indexOf( '$', var_index + ((skipped)?0:1));
				
			}
		
		return str;
	}
	
	
	//XXX: may have errors
	private static String getReference ( String str, int index )
	{
		index++;
		boolean encap = str.charAt( index ) == '{';
		char term_char = (encap)?'}':' ';
		
		int i;
		for ( i = index; i < str.length(); i++ )
			{
				if ( str.charAt( i ) == term_char ) break;
			}
		return str.substring( index + ((encap)?1:0), i );
	}
}
