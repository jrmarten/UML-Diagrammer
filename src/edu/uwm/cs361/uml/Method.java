package edu.uwm.cs361.uml;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class Method
{
    private String name;
    private String type;
    private LinkedList<String> params;
    private Access access = Access.DEFAULT;
    private boolean abstractp = false;
    private boolean staticp = false;

    private static final String[] METHOD_MODS = { "abstract", "static" };
    
    

    private static final Pattern regex = Pattern.compile(
    				
    				"^^ *" +
    				"([#~+-]|(public|private|default|protected))? *" +
    				"((s|static)|(a|abstract))? *" +
    				UMLClass.idreg +
    				" *\\( *" +
    				"("+UMLClass.classreg+"( *, *"+UMLClass.classreg+")?)?" +
    				" *\\) *" +
    				": *" + UMLClass.classreg +
    				" *$$"
    		);

    private Method() { }
    
    private Method ( String str )
    {
        params = new LinkedList<String>();
        String regex = "[():]";
        String[] parts = str.split ( regex );

        name = parts[0].trim();
        
        access ( );
        mods ( );
        
        type = parts[3].trim();
        String[] tmp = parts[1].split(",");

        for ( String s : tmp )
            {
                params.add( s.trim() );
            }
    }

    private void access ( )
    {
    	char accesssym = name.charAt(0);
        switch ( accesssym )
            {
            case '+':
            case '-':
            case '#':
            case '~':
                access = Access.fromSymbol(accesssym);
                name = name.substring(1).trim();
                break;
            default:
            }
        
        for ( Access a : Access.values())
        {
        	String accessName = a.toString();        	
        	if ( name.startsWith(accessName))
        	{
        		access = a;
        		name = name.substring( accessName.length() ).trim();
        	}
        }
    }
    
    private void mods ( )
    {
    	for ( String mod : METHOD_MODS )
    	{
    		String shorthand = mod.charAt(0) + " ";
    		if ( name.startsWith ( mod ) || name.startsWith( shorthand ))
    		{
    			int substart = (name.startsWith(shorthand)) ? shorthand.length() : mod.length();
    			switch ( mod.charAt(0))
    			{
    			case 's':
    				staticp = true;
    				break;
    			case 'a':
    				abstractp = true;
    				break;
    			}
    			name = name.substring(substart).trim();
    		}
    	}
    }
    
    private Method ( String[] mods, String type, String name, String[] arglist )
    {
	this.name = name;
	this.type = type;

	for ( String arg : arglist )
	    {
		params.add ( arg.trim() );
	    }

	for ( String mod : mods )
	    {
		mod = mod.trim();
		if ( mod.equals ( "" ) ) continue;

		char tmp = mod.charAt(0);

		switch ( tmp )
		    {
		    case 's':
		    case 'S':
			staticp = true;
			break;

		    case 'a':
		    case 'A':
			abstractp == true;
			break;

		    default:
			if ( access != Access.DEFAULT ) continue;
			access = Access.fromString ( mod );
		    }
	    }
    }

    public static Method Create ( String str )
    {
	if ( ! regex.mather ( str ).find ( ) ) return null;
	String tmp;
	String[] tmpa;
	int index;
	String signature;

	String type;
	String name;
	String[] args;
	String[] mods;

	tmpa = str.split ( ":" );
        type = tmpa[1].trim();
	tmp = tmpa[0];

	index = tmp.indexOf ( '(' );
	signature = tmp.substring ( 0, index - 1 );
	tmp = tmp.substring ( index+1, tmp.indexOf ( ')' ) - 1 );
	
	args = tmp.split ( "," );
	mods = signature.split( " " );
	index = mods.length - 1;
	name = mods[ index ];
	
	char sym = name.charAt ( 0 );
	for ( Access ac : Access.values ( ) )
	    {
		if ( ac.getSymbol() == sym )
		    {
			mods [ index ] = (""+sym);
			name = name.substring ( 1 );
		    }
	    }

	if ( Keyword.blackListp ( name ) ) return null;
	if ( keyWord.blackListp ( type ) ) return null;
	for ( String arg : args )
	    {
		if ( Keyword.blackListp ( arg.trim() ) ) return null;
	    }

	return new Method ( mods, type, name, args );
    }


    public String getName ( ) { return name; }
    public String getType ( ) { return type; }
    public Iterator<String> getParams ( ) { return params.iterator(); }
    public Access getAccess ( ) { return access; }
    public boolean isAbstract ( ) { return abstractp; }
    public boolean isStatic ( ) { return staticp; }

    public static String join ( Collection<String> parts, String delim)
    {
        Iterator<String> it = parts.iterator();
        StringBuilder sb = new StringBuilder( );
        while ( it.hasNext() )
            {
                sb.append( it.next() );
                if ( ! it.hasNext() ) break;
                sb.append( delim );
            }
        return sb.toString();
    }

    @Override public String toString ( )
    {
        return getAccess().getSymbol() + getName() + "(" + join( params, "," ) + "):" + getType ();
    }

    @Override public boolean equals ( Object o )
    {
        if ( o instanceof Method )
            {
                Method other = (Method) o;

                return
                    other.name.equals(name) &&
                    other.type.equals(name) &&
                    other.params.equals(params);
            }
        return false;
    }

    @SuppressWarnings("unchecked")
        @Override public Method clone ( )
    {
        Method result = new Method ( );
        result.name = getName();
        result.type = getType();
        result.params = (LinkedList<String>)params.clone();
        return result;
    }

    public void write ( DOMOutput fout ) throws IOException
    {
        fout.openElement ( "method" );
        fout.addAttribute ( "name", name );
        fout.addAttribute ( "type", type );

        for ( String param : params )
            {
                fout.openElement ( "param" );
                fout.addAttribute ( "type", param );
                fout.closeElement ( );
            }
        fout.closeElement ();
    }

    public void read ( DOMInput fin ) throws IOException
    {
        fin.openElement ( "method" );
        name = fin.getAttribute ( "name", "Read" );
        type = fin.getAttribute ( "type", "Error" );

        try
            {
                while ( true )
                    {
                        fin.openElement ( "param" );
                        params.add ( fin.getAttribute ( "type", "" ) );
                        fin.closeElement ( );
                    }
            } catch ( IOException e ) { /** no more elements to read. do nothing */ }
    }




}
