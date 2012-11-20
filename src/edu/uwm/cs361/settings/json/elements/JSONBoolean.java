package edu.uwm.cs361.settings.json.elements;

public class JSONBoolean extends AbstractJSONElement
{
	private boolean _val;
	
	public JSONBoolean ( String val )
	{
		_val = val.toLowerCase().equals( "true" );
	}
	
	@Override
	public String toString ( )
	{
		return (_val)? "true" : "false";
	}
	
	@Override
	public Object getElement ( ) 
	{
		return _val;
	}
	
	@Override
	public boolean isBool ( )
	{
		return true;
	}
}
