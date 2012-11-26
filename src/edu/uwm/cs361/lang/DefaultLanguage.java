package edu.uwm.cs361.lang;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.settings.format.Expansion;
import edu.uwm.cs361.settings.json.JSONQuerier;
import edu.uwm.cs361.settings.json.elements.JSONArray;
import edu.uwm.cs361.settings.json.elements.JSONElement;

public class DefaultLanguage extends AbstractLanguage
{

	private String attr_format, meth_format;
	private Expansion vars = new Expansion ( );
	
	
	private Header attr_header, meth_header;
	
	private Pattern attr_regex, meth_regex;
	
	public DefaultLanguage(JSONQuerier lang_file)
	{
		super(lang_file);
		
		JSONElement format = lang_file.query( "attribute.header" );
		attr_format = (format.isString())?(String)format.getElement():"Error: no attribute format";
		
		format = lang_file.query( "method.header" );
		meth_format = (format.isString())?(String)format.getElement():"Error: no method format";
		
		
		
		String access_pat = "("+Util.join(access.getList(), "|" )+")";
		String name_pat = "("+(
				((format = lang_file.query("name_pattern")).isString())?
						format.toString() :
							"([_$A-Za-z][0-9_$A-Za-z]*)") + ")";
		
		
		String type_pat = '('+(((format = lang_file.query( "type_pattern" )).isString())? format.toString() : "[A-Za-z]+") +')';
		String params_pat = "(" + lang_file.query( "method.param").toString() + ")";
		
		vars.startScope();
		vars.set( "access", access_pat );
		vars.set("name", name_pat);
		vars.set( "type", type_pat );
		vars.set( "params", params_pat);
		
		attr_regex = Pattern.compile( vars.format ( attr_format ) );
		meth_regex = Pattern.compile( vars.format ( meth_format ) );
		
		vars.endScope();
	}
	
	public String[] getList ( JSONElement jElement )
	{
		String[] ret = null;
		if ( jElement.isString() )
			{
				ret = new String[1];
				ret[0] = (String) jElement.getElement();
			}
		else if ( jElement.isArray() )
			{
				LinkedList<String> col = new LinkedList<String> ( );
				for ( JSONElement tmp : (JSONArray)jElement) if ( tmp.isString() )
					col.add( (String) tmp.getElement() );
				
				ret = (String[]) col.toArray();
			}
		
		return ret;
	}

	@Override
	public String getHeader(Attribute attr) {
		vars.startScope();
		
		vars.set ( "name" , attr.getName() );
		vars.set ( "access", attr.getAccess().toString() );
		vars.set ( "mods", "" );
		vars.set ( "type", attr.getType() );
		
		String header = vars.format( attr_format );
		vars.endScope();
		return header;
	}

	@Override
	public String getHeader(Method meth) {
		vars.startScope();
		
		vars.set ( "name" , meth.getName() );
		vars.set ( "access", meth.getAccess().toString() );
		vars.set ( "mods", "" );
		vars.set ( "type", meth.getType() );
		
		String header = vars.format( meth_format );
		vars.endScope();
		return header;
	}

	@Override
	public Attribute parseAttribute(String str) {
		
		Matcher mat = attr_regex.matcher( str );
		
		
		return null;
	}

	@Override
	public Method parseMethod(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAttribute(String str) {
		
		
		
		Pattern pat = Pattern.compile( "" );
		Matcher mat = pat.matcher( str );
		
		for ( int i = 1; i < mat.groupCount(); i++ )
			{
				String part = mat.group();
			}
		
		/*str = str.trim ( );
		if ( str.charAt( str.length() - 1 ) == ';' ) str = str.substring(0,str.length()-1);
		
		String name, type, access;
		LinkedList<String> mods = new LinkedList<String> ( );
		
		String[] word = str.split( "\\s" );  //splits on whitespace
		if ( !accessp ( word[0] ) ) return false;
		
		
		
		
		return true; */
		return false;
	}

	@Override
	public boolean isMethod(String str) {
		// TODO Auto-generated method stub
		return false;
	}

	public class Header
	{
		private int[] order = { 1, 2, 3, 4, 5 };
		
		public static final int NAME = 0, 
														ACCESS = 1,
														MODS = 2, 
														PARAMS = 3,
														EXTRA = 4;
		
		public Header ( String description, String pattern )
		{
			order[NAME] = get_index ( description, "name" );
			order[ACCESS] = get_index ( description, "access" );
			order[MODS] = get_index ( description, "mods" );
			order[PARAMS] = get_index ( description, "params" );
			order[EXTRA] = get_index ( description, "extra" );
			
			order = get_lowest();
		}

		public int order ( int type )
		{
			if ( type < 0 || type >= order.length ) return -1;
			return order[type];
		}
		
		/*
		 * orders the types if -1 keeps as -1
		 */
		private int[] get_lowest ( )
		{
			int[] ret = new int[order.length];
			int bound = -1, cur_lowest = 0;
			
			for ( int i = 0; i < order.length; i++ )
				{
					int n = 0;
					while ( n < order.length )
						{
							if ( bound <= order[n] )
								cur_lowest = n;
						}
					
					ret[cur_lowest] = (order[cur_lowest] == -1)? -1 : i+1;
				}
			
			return ret;
		}
		
		private int get_index ( String str, String key )
		{
			int index = str.indexOf( "$" + key );
			return (index==-1)?str.indexOf("${"+key+"}") : index;
		}
	}
}
