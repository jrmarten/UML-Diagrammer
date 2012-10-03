import java.util.regex.Pattern;
import java.util.regex.Matcher;


class Method
{
    private String name;
    private String type;
    private String[] params;
    private Access access = DEFAULT;

    public Method ()
    {
        name = "";
        type = "";
        params = [];
    }

    private Method ( String str )
    {
        String regex = "[():]";
        String[] parts = str.split ( regex );

        name = parts[0];
        type = parts[3];
        params = parts[1].split(",");

        for ( int i = 0; i < params.length; i++ )
            {
                params[i] = params[i].trim();
            }
    }
    

    //@Warning not finished
    public static Method Create ( String str )
    {
	String regex = 
	    "[+-]?[A-Za-z]?[A-Za-z0-9]*" //method name
	    " *" //for space between name and param list
	    "\\(" //start of param list
	    "([A-Za-z]+,?)+" //param list 
	    "\\)" //end of param list
	    ":[A-Za-z]?[a-zA-Z0-9]*"; //return type

	Pattern regexpat = Pattern.compile ( regex );
	Matcher comp = regexpat.matcher( str );

	return ( comp.find() ) ? new Method ( str ) : null;
    }


    public String getName ( ) { return name; }
    public String getType ( ) { return type; }
    public Iterator getParams ( ) { return new Vector( params ).iterator(); }

    @Override public String toString ( )
    {
        return getName() + "(" + ",".join( params ) + "):" + getType ();
    }

    @Override public boolean equals ( Object o )
    {
        if ( o instanceof Method )
            {
                Method other = (Method) o;

                return
                    other.name.equals(name) &&
                    other.type.equals(name)
                    ArrayEquals(other.params, params);
            }
        return false;
    }

    @Override public Method clone ( )
    {
        Method result = new Method ( );
        result.name = getName();
        result.type = getType();
        result.params = new String[ params.length ];

        int i = 0;
        for ( String param : params )
            {
                result.params[i] = param;
                i++;
            }
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

        LinkedList<String> p = new LinkedList<String>();
        try
            {
                while ( true )
                    {
                        fin.openElement ( "param" );
                        p.add ( fin.getAttribute ( "type", "" ) );
                        fin.closeElement ( );
                    }
            } catch ( IOException e ) { /** no more elements to read. do nothing */ }
        params = p.toArray();
    }


    public static boolean ArrayEquals ( Object[] a, Object[] b )
    {
        if ( a.length != b.length ) return false;
        for ( int i = 0; i < a.length; i++ )
            {
                if ( ! a[i].equals(b[i]) ) return false;
            }
        return true;
    }

}
