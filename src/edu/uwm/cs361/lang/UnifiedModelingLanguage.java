package edu.uwm.cs361.lang;

import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.settings.format.Expansion;
import edu.uwm.cs361.settings.json.JSONQuerier;
import edu.uwm.cs361.settings.json.elements.JSONElement;

public class UnifiedModelingLanguage extends AbstractLanguage
{

	private Expansion vars = new Expansion ( );
	
	private String attr_format;
	private String meth_format;
	
	
	public UnifiedModelingLanguage ( JSONQuerier query )
	{
		super ( query );
		
		JSONElement cur = query.query( "attribute.header" );
		attr_format = (cur.isString())?(String)cur.getElement() : "Error with lang file";
		cur = query.query( "method.header" );
		meth_format = (cur.isString())?(String) cur.getElement() : "Error with lang file";
	}
	
	@Override
	public String getHeader(Attribute attr) {
		vars.startScope();
		
		vars.set ( "name" , attr.getName() );
		vars.set ( "access", attr.getAccess().toString() );
		vars.set ( "mods", "" );
		vars.set ( "type", attr.getType() );
		
		String header = vars.format( attr_format );
		vars.endScope();
		return header;
	}

	@Override
	public String getHeader(Method meth) {
		vars.startScope();
		
		vars.set ( "name" , meth.getName() );
		vars.set ( "access", meth.getAccess().toString() );
		vars.set ( "mods", "" );
		vars.set ( "type", meth.getType() );
		
		String header = vars.format( meth_format );
		vars.endScope();
		return header;
	}

	@Override
	public Attribute parseAttribute(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Method parseMethod(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAttribute(String str) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMethod(String str) {
		// TODO Auto-generated method stub
		return false;
	}

}
