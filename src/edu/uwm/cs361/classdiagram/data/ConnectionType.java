package edu.uwm.cs361.classdiagram.data;

import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.decoration.LineDecoration;

public enum ConnectionType
{
	DEPENDANCY, 
	ASSOCIATION, 
	AGGREGATION, 
	//INHERITANCE, Handled else where
	COMPOSITION;

	
	private static final LineDecoration DEFAULT_DECORATOR = null;
	private static final Object[][] DECORATOR_MAPPINGS = 
		{
				{ COMPOSITION, new ArrowTip ( 0.4, 15.0, 30.0 ) },
				{ AGGREGATION, new ArrowTip ( 0.4, 15.0, 30.0, false, true, true ) },
				{ ASSOCIATION, new ArrowTip ( .35, 20, 18.4 ) }
		};
	
	public LineDecoration getDecoration ( )
	{
		for ( Object[] pair : DECORATOR_MAPPINGS )
			{
				if ( this == pair[0] ) return ( LineDecoration ) pair [1];
			}
		return DEFAULT_DECORATOR;
	}
}
