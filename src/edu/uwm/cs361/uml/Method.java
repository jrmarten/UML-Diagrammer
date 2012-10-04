package edu.uwm.cs361.uml;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

class Method
{
    private String name;
    private String type;
    private LinkedList<String> params;
    private Access access = Access.DEFAULT;
    private boolean abstractp = false;
    private boolean staticp = false;
    
    public Method ()
    {
        name = "";
        type = "";
        params = new LinkedList<String>();
    }

    private Method ( String str )
    {
        String regex = "[():]";
        String[] parts = str.split ( regex );

        name = parts[0];
        type = parts[3];
        String[] tmp = parts[1].split(",");

        for ( int i = 0; i < tmp.length; i++ )
            {
                params.add ( tmp[i].trim());
            }
    }
    

    public static Method Create ( String str )
    {
	String regex = 
	    "[+-]?[A-Za-z]?[A-Za-z0-9]*" + //method name
	    " *" +//for space between name and param list
	    "\\(" +//start of param list
	    "([A-Za-z]+,?)+" +//param list 
	    "\\)" +//end of param list
	    ":[A-Za-z]?[a-zA-Z0-9]*"; //return type

	Pattern regexpat = Pattern.compile ( regex );
	Matcher comp = regexpat.matcher( str );

	return ( comp.find() ) ? new Method ( str ) : null;
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
        return getName() + "(" + join( params, "," ) + "):" + getType ();
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
