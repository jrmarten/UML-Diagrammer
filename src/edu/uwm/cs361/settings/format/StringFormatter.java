package edu.uwm.cs361.settings.format;

public final class StringFormatter extends AbstractFormatter
{
	
	private static final String[][] SUBS = 
		{
			{ "\\n", "\n" },
			{ "\\\"", "\"" },
			{ "\\$", "$" },
		};

	@Override
	public String format( String base ) 
	{
		for ( String[] rep : SUBS ) base = base.replace( rep[0], rep[1]);
		return base;
	}
}
