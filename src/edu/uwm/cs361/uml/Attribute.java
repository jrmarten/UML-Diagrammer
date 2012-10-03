
class Attribute
{
    private String name;
    private String type;
    private Access access;

    public Attribute ( )
    {
        name = "";
        type = "";
    }

    private Attribute ( String str )
    {
        String parts = str.split( ":" );
        name = parts[0];
        type = parts[1];
    }

    @Override
        public Attribute clone ( )
    {
        Attribute result = new Attribute ();
        result.type = type;
        result.name = name;
        return result;
    }

    @Override
        public String toString ( )
    {
        return name + ":" + type;
    }

    @Override
        public boolean equals ( Object obj )
    {
        if ( obj instanceof Attribute )
            {
                return (((Attribute)obj).name.equals ( name ) &&
                        ((Attribute)obj).type.equals ( type ));
            }
        return false;
    }

    public static Attribute Create ( String str )
    {
	String regex = 
	    " *[+-]? *" //Access
	    "[A-Za-z]+[A-Za-z0-9]*" //Method name
	    " *: *" //seporator
	    "[A-Za-z][A-Za-z0-9]*" //TypeName
	    
	    

        str = str.trim();
        for ( char ch : str )
            {
                if ( Character.isWhitespace ( ch ) ) return null;
            }
        return new Attribute ( str );
    }

    public String getName()
    {
        return name;
    }

    public String getType()
    {
        return type;
    }

    public void write ( DOMOutput fout ) throws IOException
    {
        out.openElement ( "attribute" );
        out.addAttribute ( "name", getName() );
        out.addAttribute ( "type", getType() );
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
