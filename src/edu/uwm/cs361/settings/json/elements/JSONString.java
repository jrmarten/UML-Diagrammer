package edu.uwm.cs361.settings.json.elements;

public class JSONString extends AbstractJSONElement
{
	private String _val;

	public JSONString ( String val )
	{
		_val = val.substring(1).substring( 0, val.length() - 2);
		_val.replaceAll( "\\n" , "\n" );
	}
	
	@Override
	public String toString ( ) 
	{
		return "\"" + _val + "\"";
	}
	
	@Override
	public Object getElement ( ) 
	{
		return _val;
	}
	
	@Override
	public boolean isString ( )
	{
		return true;
	}
}
