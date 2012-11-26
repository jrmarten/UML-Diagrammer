package edu.uwm.cs361.settings.format;


public class CCScopeFormatter extends AbstractFormatter
{
	private int nests = 0;
	
	private final static String INDENT = "  ";
	private final static char start = '{';
	private final static char end = '}';
	
	
	@Override
	public String format ( String pipe_in )
	{
		String[] lines = pipe_in.split( "\n" );
		StringBuilder sb = new StringBuilder ( );
		
		for ( String line : lines )
			{
				line = line.trim();
				nests -= delim ( line, end );
				sb.append( indent() + line + '\n' );
				nests += delim ( line, start );
			}
		
		
		return sb.toString();
	}
	
	public String indent ( )
	{
		String str = "";
		for ( int i = 0; i < nests; i++ ) str += INDENT;
		return str;
	}

	private int delim ( String line, char delim )
	{
		int c = 0;
		
		boolean in_str = false;
		
		for ( int i = 0; i < line.length(); i++ )
			{
				if ( line.charAt ( i ) == delim && !in_str ) c++;
				if ( line.charAt( i ) == '\"' && !escaped ( line, i ) )
						in_str = !in_str;
			}
		
		return c;
	}
}
