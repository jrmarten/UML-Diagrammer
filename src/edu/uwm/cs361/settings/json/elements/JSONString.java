package edu.uwm.cs361.settings.json.elements;


public class JSONString extends AbstractJSONElement
{
	private String _val;

	public JSONString ( String val )
	{
		_val = val.trim().substring(1).substring( 0, val.length() - 2);
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

	@Override
	public boolean equals ( Object o )
	{
		if ( o instanceof String )
			{
				return _val.equals ( o );
			}
		if ( o instanceof JSONString )
			{
				return _val.equals( ((JSONString)o)._val );
			}
		return false;
	}
}
