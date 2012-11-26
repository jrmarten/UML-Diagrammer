package edu.uwm.cs361.lang;

import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;

public interface Language
{
	
	
	public String getHeader ( Attribute attr );
	public String getHeader ( Method meth );
	
	public Attribute parseAttribute ( String str );
	public Method parseMethod ( String str );
	
	public boolean isAttribute ( String str );
	public boolean isMethod ( String str );
	
	public boolean accessp ( String word );
	public boolean keywordp ( String word );
	public boolean primitivep ( String word );
}
