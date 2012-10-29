package edu.uwm.cs361;

import java.util.Iterator;

public class Util
{

	private static final boolean	DEBUG	= true;

	public static boolean debug() {
		return DEBUG;
	}

	public static void dprint(String line) {
		if ( !DEBUG ) return;
		if ( line == null ) 
			return;
		if (line.equals(""))
			return;
		
		System.out.println(line);
	}
	
	public static void dprint ( Object o )
	{
		if ( ! DEBUG ) return;
		if ( o == null )
			System.out.println ( o );
	}
	
	public static <T> void dprint ( Iterable<T> it )
	{
		if ( ! DEBUG ) return;
		if ( it == null ) return;
		
		for ( T cur : it )
			{
				System.out.print( cur );
			}
	}

	public static <E> void printIterable(Iterable<E> col) {
		for (E e : col)
			{
				System.out.println(e);
			}
	}

	public static <E> void printIterable(Iterable<E> col, boolean debug) {
		if (debug)
			printIterable(col);
	}

	public static <E> String join(Iterable<E> col, String sep) {
		Iterator<E> it = col.iterator();
		String result = "";

		while (it.hasNext())
			{
				result += it.next().toString();
				if (!it.hasNext())
					break;
				result += sep;
			}
		return result;
	}
	
	public static <T> boolean equals ( Iterable<T> a, Iterable<T> b )
	{
		Iterator<T> ait = a.iterator();
		Iterator<T> bit = b.iterator();
		
		while ( bit.hasNext() && ait.hasNext() )
			{
				if (!ait.next().equals( bit.next() ) ) return false;
			}
		
		return ! ( bit.hasNext() || ait.hasNext() );
	}

	public static Object report(String line) {
		System.out.println(line);
		return null;
	}
	
	public static boolean isEmpty ( String str )
	{
		return str.trim().equals ( "" );
	}

	public static void dprint(String line, boolean debug) {
		if (debug)
			System.out.println(line);
	}
	
	
	//XXX:TEST!!!!
	public static boolean containsIgnoreCase ( String full, String part )
	{
		int part_cur = 0;
		for ( int full_cur = 0; full_cur < full.length(); full_cur++ )
			{
				char full_val = full.charAt ( full_cur );
				
				if ( equalsIgnoreCase ( full_val, part.charAt( part_cur ) ) ) part_cur++;
				
				if ( part_cur == part.length() ) return true;
				
			}
		return false;
	}
	
	private static boolean equalsIgnoreCase ( char a, char b )
	{
		return Character.toLowerCase(a) == Character.toLowerCase(b); 
	}
	
	public static int countInstancesOf ( String str, char ch )
	{
		int i = 0;
		for ( int n = 0; n < str.length(); n++ )
			{
				if ( str.charAt( n ) == ch ) i++;
			}
		return i;
	}
}
