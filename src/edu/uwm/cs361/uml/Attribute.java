package edu.uwm.cs361.uml;

import java.io.*;
import org.jhotdraw.xml.*;
import java.util.regex.*;

public class Attribute
{
    private String name;
    private String type;
    private Access access;
    private boolean staticp;

    public Attribute ( )
    {
        name = "";
        type = "";
    }

    private Attribute ( String str )
    {
        String[] parts = str.split( ":" );
        name = parts[0].trim();
        type = parts[1].trim();
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
	    "^ *[+-#~]? *" +//Access
	    "s? *" + //static
	    "[A-Za-z]+[A-Za-z0-9]*" + //Attribute name
	    " *: *" + //Separator
	    "[A-Za-z][A-Za-z0-9]* *$"; //TypeName
	    
	    Pattern pat = Pattern.compile(regex);

		Matcher mat = pat.matcher(str);
		return ( mat.find() ) ? new Attribute ( str ) : null;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public Access getAccess ( ) { return access; }
    public boolean isStatic ( ) { return staticp; }
    
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
