package edu.uwm.cs361.classdiagram.data;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

import static edu.uwm.cs361.Util.*;


public class Method
{

	private String								name;
	private String								type;
	private LinkedList<String>		params		= new LinkedList<String> ( );
	private Access								access		= Access.DEFAULT;
	private boolean								abstractp	= false;
	private boolean								staticp		= false;

	private static final String[]	Modifiers	= { "private", "public", "protected",
			"default", "abstract", "static", "#", "+", "-", "~" };

	private static final Pattern	regex			= Pattern.compile (

																					"^^ *(([#~+-]|[a-z]*) *)* *"
																							+ UMLClass.idreg + " *\\( *"
																							+ "(" + UMLClass.classreg
																							+ "( *, *" + UMLClass.classreg
																							+ ")?)?" + " *\\) *" + ": *"
																							+ UMLClass.classreg + " *$$"

																					/*
																					 * "^^ *" +
																					 * "([#~+-]|(public|private|default|protected))? *"
																					 * + "((s|static)|(a|abstract))? *" +
																					 * UMLClass.idreg + " *\\( *" +
																					 * "("+UMLClass
																					 * .classreg+"( *, *"+UMLClass
																					 * .classreg+")?)?" + " *\\) *" +
																					 * ": *" + UMLClass.classreg + " *$$"
																					 */
																					);

	private Method ( )
	{
		type = "";
		name = "";
	}

	private Method ( String[] mods, String type, String name, String[] arglist )
	{
		this.name = name;
		this.type = type;

		for ( String arg : arglist )
			{
				arg = arg.trim ( );
				if ( !arg.equals ( "" ) ) params.add ( arg );
			}

		for ( String mod : mods )
			{
				mod = mod.trim ( );
				if ( mod.equals ( "" ) ) continue;

				char tmp = mod.charAt ( 0 );

				switch ( tmp )
					{
						case 's':
						case 'S':
							staticp = true;
						break;

						case 'a':
						case 'A':
							abstractp = true;
						break;

						default:
							if ( access != Access.DEFAULT ) continue;
							access = Access.fromString ( mod );
					}
			}
	}

	public static Method Create ( String str )
	{
		boolean works = regex.matcher ( str ).find ( );
		if ( !works )
			{
				System.out.println ( "Stoped at regex: " + str );
				return null;
			}
		String tmp;
		String[] tmpa;
		int index;
		String signature;

		String type;
		String name;
		String[] args;
		String[] mods;

		tmpa = str.split ( ":" );
		type = tmpa[1].trim ( );
		tmp = tmpa[0];

		index = tmp.indexOf ( '(' );
		signature = tmp.substring ( 0, index );
		tmp = tmp.substring ( index + 1, tmp.indexOf ( ')' ) );

		args = tmp.split ( "," );
		mods = signature.split ( " " );
		index = mods.length - 1;
		name = mods[index];
		mods[index] = "";

		char tmpch = name.charAt ( 0 );
		for ( char sym : Access.symbols ( ) )
			{
				if ( sym == tmpch ) mods[index] = "" + sym;
			}

		boolean ap = false, sp = false;
		for ( String mod : mods )
			{
				if ( mod.equals ( ""  ) ) continue;
				
				boolean valid = false;
				for ( String keyword : Modifiers )
					{
						if ( keyword.equals ( mod ) ) valid = true;
						if ( mod.equals ( "abstract" ) ) ap = true;
						if ( mod.equals ( "static" ) ) sp = true;
					}
				if ( ! valid ) return (Method) report ( "Illegal Modifier: " + name);
			}
		
			if ( ap && sp ) return null;
		
		if ( Keywords.keywordp ( name ) ) 
			return (Method) report ( "Black Listed: " + name + "(name)" );
		
		//Not valid because primary names are blacklisted
		
			if ( Keywords.reservedp ( type ) ) 
				return (Method) report ( "Black Listed: " + name + "(type)" );
		
		  for ( String arg : args )
			{
				if ( Keywords.reservedp ( arg.trim ( ) ) ) 
					return (Method) report ( "Black Listed: " + name + "(arg)" );
			}
		  
		  
		return new Method ( mods, type, name, args );
	}

	
	
	public String getName ( )
	{
		return name;
	}

	public String getType ( )
	{
		return type;
	}

	public LinkedList<String> getParameters ( )
	{
		return params;
	}

	public Access getAccess ( )
	{
		return access;
	}

	public boolean isAbstract ( )
	{
		return abstractp;
	}

	public boolean isStatic ( )
	{
		return staticp;
	}

	public static String join ( Collection<String> parts, String delim )
	{
		Iterator<String> it = parts.iterator ( );
		StringBuilder sb = new StringBuilder ( );
		while ( it.hasNext ( ) )
			{
				sb.append ( it.next ( ) );
				if ( !it.hasNext ( ) ) break;
				sb.append ( delim );
			}
		return sb.toString ( );
	}

	@Override
	public String toString ( )
	{
		return getAccess ( ).getSymbol ( ) + getName ( ) + "("
				+ join ( params, "," ) + "):" + getType ( );
	}

	@Override
	public boolean equals ( Object o )
	{
		if ( o instanceof Method )
			{
				Method other = (Method)o;

				return other.name.equals ( name ) && other.type.equals ( name )
						&& other.params.equals ( params );
			}
		return false;
	}

	@SuppressWarnings ( "unchecked" )
	@Override
	public Method clone ( )
	{
		Method result = new Method ( );
		result.name = getName ( );
		result.type = getType ( );
		result.params = (LinkedList<String>)params.clone ( );
		return result;
	}

	public void write ( DOMOutput fout ) throws IOException
	{
		fout.openElement ( "method" );
		fout.addAttribute ( "name", name );
		fout.addAttribute ( "type", type );

		for ( String param : params )
			{
				fout.openElement ( "param" );
				fout.addAttribute ( "type", param );
				fout.closeElement ( );
			}
		fout.closeElement ( );
	}

	public void read ( DOMInput fin ) throws IOException
	{
		fin.openElement ( "method" );
		name = fin.getAttribute ( "name", "Read" );
		type = fin.getAttribute ( "type", "Error" );

		try
			{
				while ( true )
					{
						fin.openElement ( "param" );
						params.add ( fin.getAttribute ( "type", "" ) );
						fin.closeElement ( );
					}
			}
		catch ( IOException e )
			{	/** no more elements to read. do nothing */
			}
	}
}
