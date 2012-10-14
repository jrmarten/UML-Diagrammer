package edu.uwm.cs361.classdiagram.data;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class Attribute
{
	private String name;
	private String type;
	private Access access = Access.DEFAULT;
	private boolean staticp = false;
	private boolean finalp = false;

	private static final Pattern regex = Pattern.compile(
			"^^ *" +
					"([#~+-]|(public|private|default|protected))? *" +
					"((((s|static)|(f|final)) )?" +
					"((f|final)|(s|static)))?" +
					" *" +
					UMLClass.idreg +
					" *: *" + UMLClass.classreg +
					" *$$"
			);

	private Attribute ( ) { }

	private Attribute ( String str )
	{
		String[] parts = str.split( ":" );
		name = parts[0].trim();
		type = parts[1].trim();


		parts = name.split(" ");
		name = parts[parts.length - 1];
		parts[ parts.length - 1 ] = "";

		for ( String mod : parts)
			for ( Access ac : Access.values())
				{
					if ( mod.equals(ac.toString())) access = ac;
				}

		for (String mod : parts )
			{
				if ( mod.equals( "static" ) ) staticp = true;
				if ( mod.equals( "final" ) ) finalp = true;
			}
	}

	private Attribute ( String[] perms, String name, String type )
	{
		this.name = name;
		this.type = type;

		for ( String perm : perms )
			{
				perm = perm.trim ( );
				if ( perm.equals ( "" ) ) continue;

				char tmp = perm.charAt ( 0 );

				switch ( tmp )
				{
					case 's':
					case 'S':
						staticp = true;
						break;

					case 'f':
					case 'F':
						finalp = true;
						break;

					default:
						if ( access != Access.DEFAULT ) continue;
						access = Access.fromString ( perm );
				}
			}
	}

	public static Attribute Create ( String str )
	{
		if ( ! regex.matcher(str).find() )
		{
			System.out.println ( "Stoped at regex");
			return null;
		}

		String[] parts = str.split(  ":" );

		String signature = parts[0].trim();
		String type = parts[1].trim();
		parts = signature.split ( " " );
		int tmpIndex = parts.length - 1;
		String name = parts[ tmpIndex ];

		if ( Keywords.keywordp ( type ) ) return null;
		if ( Keywords.reservedp ( name ) ) return null;

		char sym = name.charAt ( 0 );

		for ( Access ac : Access.values() )
			{
				if ( ac.getSymbol() == sym )
					{
						parts [ tmpIndex ] = ("" + sym);
						name = name.substring ( 1 );
					}
			}

		return new Attribute ( parts, name, type ) ;
	}

	@Override public Attribute clone ( )
	{
		Attribute result = new Attribute ();
		result.type = type;
		result.name = name;
		return result;
	}

	@Override public String toString ( )
	{
		return access.getSymbol() + name + ":" + type;
	}
	
	public String getSignature ( )
	{
		return access.toString ( ) + " " + 
				((staticp)?"static ":"") +
				((finalp)?"final ":"") +
				type + " " + name;
	}

	public boolean sigEquals ( Attribute attr )
	{
		return name.equals ( attr.name ) && type.equals ( attr.type );
	}
	
	@Override public boolean equals ( Object obj )
	{
		if ( obj instanceof Attribute )
			{
				Attribute that = (Attribute) obj;
				
				boolean x = name.equals ( that.name );
				x = x && type.equals ( that.type );
				x = x && finalp == that.finalp && staticp == that.staticp;
				x = x && access.equals ( that.access );
				return x;
			}
		return false;
	}



	public String getName() { return name; }
	public String getType() { return type; }
	public Access getAccess ( ) { return access; }
	public boolean isStatic ( ) { return staticp; }
	public boolean isFinal ( ) { return finalp; }

	public void write ( DOMOutput fout ) throws IOException
	{
		fout.openElement ( "attribute" );
		fout.addAttribute ( "name", getName() );
		fout.addAttribute ( "type", getType() );
	}


	/**
	 *if error writing to dom or someone messed with the saved
	 *file this will display Read:Error on the class
	 */
	public void read ( DOMInput fin ) throws IOException
	{
		fin.openElement ( "attribute" );
		name = fin.getAttribute( "name", "Read" );
		type = fin.getAttribute( "type", "Error" );
	}
}
