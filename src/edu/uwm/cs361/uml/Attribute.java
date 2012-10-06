package edu.uwm.cs361.uml;

import java.io.*;
import org.jhotdraw.xml.*;
import java.util.regex.*;

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

    @Override public boolean equals ( Object obj )
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
		return (regex.matcher(str).find() ) ? new Attribute ( str ) : null;
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
