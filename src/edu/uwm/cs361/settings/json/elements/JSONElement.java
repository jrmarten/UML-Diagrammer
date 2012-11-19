package edu.uwm.cs361.settings.json.elements;

public interface JSONElement
{
	
	public Object getElement ( );

	public boolean isString ( );
	public boolean isNumber( );
	public boolean isBool ( );
	public boolean isNull ( );
	public boolean isArray ( );
	public boolean isObject ( );
}
