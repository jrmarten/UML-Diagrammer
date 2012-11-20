package edu.uwm.cs361.settings.json.elements;

public class JSONNull extends AbstractJSONElement
{
	public JSONNull ( )
	{
	}
	
	@Override
	public String toString ( )
	{
		return "null";
	}
	
	@Override
	public Object getElement ( )
	{
		return null;
	}

	@Override
	public boolean isNull ( )
	{
		return true;
	}
}
