package edu.uwm.cs361.uml;

public enum Access
{
    PRIVATE ( "private", '-' ),
    PUBLIC ( "public", '+' ),
    DEFAULT ( "default", '~' ),
    PROTECTED ("protected", '#');
    	
    
    private String name = "";
    private char sym = 0;
    
    private Access ( String str, char symbol )
        {
            name = str;
            sym = symbol;
        }

    public char getSymbol ( )
    {
    	return sym;
    }
    
    public static Access fromSymbol ( char c )
    {
    	switch ( c )
    	{
    	case '+': return Access.PUBLIC;
    	case '-': return Access.PRIVATE;
    	case '#': return Access.PROTECTED;
    	case '~': return Access.DEFAULT;
    	default: return Access.DEFAULT;
    	}
    }
    
    
    
    public static Access fromString ( String perm )
    {
    	if ( perm.equalsIgnoreCase ( "public" ) ) return PUBLIC;
    	if ( perm.equalsIgnoreCase ( "protected" ) ) return PROTECTED;
    	if ( perm.equalsIgnoreCase ( "private" ) ) return PRIVATE;
    	return DEFAULT;
    }
    
    public String toString()
    {
        return name;
    }
}