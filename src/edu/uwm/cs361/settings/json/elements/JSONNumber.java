package edu.uwm.cs361.settings.json.elements;


public class JSONNumber extends AbstractJSONElement
{

	private double _val;
	
	public JSONNumber (  String val )
	{
		try
		{
			_val = Double.parseDouble( val );
		}
		catch	( NumberFormatException e )
		{
			_val = 0.0;
		}
	}
	
	@Override
	public boolean equals ( Object o )
	{
		if ( o instanceof JSONNumber ) return _val == ((JSONNumber)o)._val;
		if ( o instanceof Double ) return _val == ((Double)o).doubleValue();
		if ( o instanceof Integer ) return _val == ((Integer)o).intValue();
		return false;
	}
	
	@Override
	public String toString ( )
	{
		return "" + _val;
	}
	
	@Override
	public Object getElement ( )
	{
		return _val;
	}
	
	@Override
	public boolean isNumber ( )
	{
		return true;
	}
}
