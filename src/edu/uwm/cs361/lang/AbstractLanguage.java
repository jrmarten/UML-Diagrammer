package edu.uwm.cs361.lang;

import edu.uwm.cs361.settings.json.JSONQuerier;
import edu.uwm.cs361.settings.json.elements.JSONArray;
import edu.uwm.cs361.settings.json.elements.JSONElement;
import edu.uwm.cs361.settings.json.elements.JSONString;

public abstract class AbstractLanguage implements Language
{
	protected Keywords keys, primitives, access;
	
	private static final JSONArray DEFAULT_ACCESS= new JSONArray ( 
			new JSONString ( " public " ),
			new JSONString ( " private " ),
			new JSONString ( " protected " ),
			new JSONString ( " default " ) );
	
	protected JSONQuerier lang_file;
	
	public AbstractLanguage ( JSONQuerier lang_file )
	{
		this.lang_file = lang_file;
		load ( lang_file );
	}
	
	private void load ( JSONQuerier lang_file )
	{
		keys = new Keywords ( lang_file.query( "keywords" ) );
		primitives = new Keywords ( lang_file.query( "primitives" ) );
		
		JSONElement access = lang_file.query ( "access" );
		this.access = new Keywords ( (access.isArray())? access : DEFAULT_ACCESS );
	}
	
	@Override
 	public boolean accessp ( String word ) { return access.contains( word ); }
	@Override
	public boolean keywordp ( String word ) { return keys.contains( word ); }
	@Override
	public boolean primitivep ( String word ) { return primitives.contains( word ); }
}
