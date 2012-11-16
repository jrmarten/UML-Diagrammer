package edu.uwm.cs361.classdiagram.data;

import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.decoration.LineDecoration;

public enum ConnectionType
{
	DEPENDENCY, 
	ASSOCIATION, 
	AGGREGATION, 
	//INHERITANCE, Handled else where
	COMPOSITION;
	
	private static final LineDecoration DEFAULT_DECORATOR = null;
	private static final Object[][] DECORATOR_MAPPINGS = 
		{
				{ COMPOSITION, new ArrowTip ( 0.4, 15.0, 30.0 ), "Composition" },
				{ AGGREGATION, new ArrowTip ( 0.4, 15.0, 30.0, false, true, true ), "Aggregation" },
				{ ASSOCIATION, new ArrowTip ( .35, 20, 18.4 ), "Association" },
				{ DEPENDENCY, DEFAULT_DECORATOR, "Dependency" }
		};
	
	public LineDecoration getDecoration ( )
	{
		for ( Object[] info : DECORATOR_MAPPINGS )
			{
				if ( this == info[0] ) return ( LineDecoration ) info[1];
			}
		return DEFAULT_DECORATOR;
	}
	@Override
	public String toString()
	{
		for ( Object[] info : DECORATOR_MAPPINGS )
			{
				if ( this == info[0] ) return (String) info[2];
			}
		
		throw new IllegalStateException ( "ConnectionType is not in Mapping" );
	}
	public static ConnectionType parse ( String str )
	{
		for ( Object[] info : DECORATOR_MAPPINGS )
			{
				if ( str.equalsIgnoreCase( (String)info[2] ) )
					return (ConnectionType)info[0];
			}
		return null;
	}
}
