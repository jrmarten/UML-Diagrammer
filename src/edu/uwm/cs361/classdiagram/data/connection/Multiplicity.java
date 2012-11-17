package edu.uwm.cs361.classdiagram.data.connection;

import java.util.LinkedList;

import edu.uwm.cs361.Util;

public class Multiplicity
{
	LinkedList<MultiplicityRule> rules = new LinkedList<MultiplicityRule> ( );
	
	private Multiplicity ( ) { }
	
	public boolean inRange ( int x )
	{
		for ( MultiplicityRule rule : rules )
			{
				if ( rule.inRange( x ) ) return true;
			}
		return false;
	}
	
	@Override
	public String toString()
	{
		return Util.join ( rules, "," );
	}
	
	public static Multiplicity Create ( String str )
	{
		String[] parts = str.split ( "," );
		MultiplicityRule tmp;
		Multiplicity result = new Multiplicity ( );
		
		for ( String part : parts )
			{
				tmp = MultiplicityRule.Create( part );
				if ( tmp != null ) result.rules.add( tmp );
			}
		
		return result;
	}
	
	private static class MultiplicityRule
	{
		private static final int NAN = Integer.MAX_VALUE;
		private int start = 0;
		private int end = 0;
		private boolean set = false;
		
		private static Object[][] SYMBOLS = 
			{
					{ "*", 0, NAN },
					{ "+", 1, NAN }
			};
		
		private MultiplicityRule ( ) { }
		
		@Override
		public String toString()
		{			
			if ( set )
				{
					for ( Object[] sym_set : SYMBOLS )
						{							
							if ( 	start == (Integer) sym_set[1] &&
										end == (Integer) sym_set[2] )
								return (String) sym_set[0];
						}
					
					if ( end == NAN ) return start + "-*";
					else return start + "-" + end;
				}
			return "" + start;
		}
		
		public boolean inRange ( int x )
		{
			if ( !set ) return x == start;
			
			return start <= x && x <= end;
		}
		
		public static MultiplicityRule Create ( String str )
		{
			MultiplicityRule mult = new MultiplicityRule ( );

			for ( Object[] sym : SYMBOLS )
				{
					if ( str.equals ( sym[0] ) )
						{
							mult.start = (Integer) sym[1];
							mult.end = (Integer) sym[2];
							mult.set = true;
							return mult;
						}
				}
			
			try
			{
				if ( str.contains( "-" ) )
					{
						Util.dprint( "Set notation" );
						String[] parts = str.split( "-", 2);
						
						mult.start = Integer.parseInt( parts[0] );
						
						if ( parts[1].equals( "*" ) )
							{
								mult.end = NAN;
							}
						else
							{
								mult.end = Integer.parseInt( parts[1] );
							}
						mult.set = true;
						return mult;
					}
				else
					{
						mult.start = Integer.parseInt( str );
						return mult;
					}
			}
			catch ( NumberFormatException e ) 
			{
				Util.dprint( e );
				Util.dprint ( "Cannot parse number" );
				return null;
			}
		}
	}
}
