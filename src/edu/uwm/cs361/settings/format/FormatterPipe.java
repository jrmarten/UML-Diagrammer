package edu.uwm.cs361.settings.format;

import java.util.Collection;
import java.util.LinkedList;

public class FormatterPipe extends AbstractFormatter
{
	
	Collection<Formatter> filters = new LinkedList<Formatter> ( );

	public FormatterPipe ( ) { }
	public FormatterPipe ( Collection<Formatter> col )
	{
		if ( col != null )
			{
				for ( Formatter filter : col )
					{
						filters.add( filter );
					}
			}
	}
	
	public FormatterPipe ( Formatter... pipe_lit )
	{
		for ( Formatter filter : pipe_lit )
			{
				filters.add( filter );
			}
	}
	
	public void add ( Formatter filter )
	{
		filters.add( filter );
	}
	
	@Override
	public String format( String base ) {
		String buffer = base;
		
		for ( Formatter filter : filters )
			{
				buffer = filter.format( buffer );
			}
		
		return buffer;
	}

}
