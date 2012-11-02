package edu.uwm.cs361.classdiagram.data;

public class Argument
{
	private String type, name;
	
	private Argument ( ) { }
	
	public Argument ( String type, String name )
	{
		this.type = type;
		this.name = name;
	}
	
	public String getType ( ) { return type; }
	public String getName ( ) { return name; }
	
	@Override
	public String toString ( )
	{
		return name + " : " + type;
	}
	
	@Override
	public boolean equals ( Object o )
	{
		if ( !( o instanceof Argument ) ) return false;
		
		Argument that = (Argument) o;
		
		return type.equals( that.type );
	}
	
	public static Argument Create ( String str )
	{
		Argument arg = new Argument ( );
		String[] parts = str.split( ":" );
		try {
			arg.name = parts[0].trim();
			arg.type = parts[1].trim();

			
			if ( arg.name.contains( " " ) ) return null;
			if ( arg.type.contains( " " ) ) return null;
		} catch ( IndexOutOfBoundsException e )
		{
			return null;
		}
		return arg;
	}
}
