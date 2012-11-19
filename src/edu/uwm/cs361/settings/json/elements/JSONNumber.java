package edu.uwm.cs361.settings.json.elements;

public class JSONNumber extends AbstractJSONElement
{

	double _val;
	
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
