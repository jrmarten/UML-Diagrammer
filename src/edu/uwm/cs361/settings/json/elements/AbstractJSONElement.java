package edu.uwm.cs361.settings.json.elements;

public abstract class AbstractJSONElement implements JSONElement
{
	@Override
	public boolean isString ( ) { return false; }
	
	@Override
	public boolean isNumber( ) { return false; }
	
	@Override
	public boolean isBool ( ) { return false; }
	
	@Override
	public boolean isNull ( ) { return false; }
	
	@Override
	public boolean isArray ( ) { return false; }
	
	@Override
	public boolean isObject ( ) { return false; }

	
	private static Object[][] CONTAINER_LIMITS = 
		{
				{ '\"',	'\"', JSONString.class },
				{ '[', 	']',	JSONArray.class },
				{ '{',	'}',	JSONObject.class }
		};
	private static final String 		neg = 				"-?", 
																	num = 				"[0-9]+",
																	decimal = 		"\\.?[0-9]*",
																	exp =					"((e|E)(+|-)?[0-9]+";
	public static Class<? extends JSONElement> getType ( String str )
	{
		str = str.trim();
		if ( str.equalsIgnoreCase("null") ) return JSONNull.class;
		
		if ( str.equalsIgnoreCase("true") ||
				str.equalsIgnoreCase ( "false" ) ) return JSONBoolean.class;
		
		
		if ( str.matches( neg+num+decimal+exp ) ) return JSONNumber.class;
		
		char start = str.charAt( 0 );
		char end = str.charAt( str.length() - 1 );
		
		for ( Object[] pair : CONTAINER_LIMITS )
			{
				if ( 	pair[0].equals( start ) &&
							pair[1].equals( end ) )
					return (Class<? extends JSONElement>) pair[2];
			}
		
		return JSONNull.class;
	}

}
