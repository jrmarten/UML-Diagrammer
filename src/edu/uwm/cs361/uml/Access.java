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
    
    public String toString()
    {
        return name;
    }
}