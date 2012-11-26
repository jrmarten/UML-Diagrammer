package edu.uwm.cs361.lang;

import java.util.ArrayList;
import java.util.Collection;

import edu.uwm.cs361.settings.json.elements.JSONArray;
import edu.uwm.cs361.settings.json.elements.JSONElement;

public class Keywords
{
	Collection<String> col = new ArrayList<String> ( );
	
	public Keywords ( JSONElement list )
	{
		if ( list != null && list.isArray() ) for ( JSONElement e : (JSONArray) list )
				if ( e.isString() ) col.add( (String) e.getElement() );
	}
	public boolean contains ( String word )
	{
		return col.contains( word );
	}
	
	public String[] getList ( )
	{
		return (String[]) col.toArray();
	}
}
