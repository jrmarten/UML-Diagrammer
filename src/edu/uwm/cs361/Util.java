package edu.uwm.cs361;

import java.util.Iterator;
import java.util.List;

public class Util
{

	private static final boolean	DEBUG	= true;

	public static boolean debug() {
		return DEBUG;
	}

	public static void dprint(String line) {
		if (line.equals(""))
			return;
		if (DEBUG)
			System.out.println(line);
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
}
