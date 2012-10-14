package edu.uwm.cs361.classdiagram.data;


public class AbstractUMLClass extends UMLClass
{
	public AbstractUMLClass ( ) { super ( ); }
	public AbstractUMLClass ( String name )
	{
		super ( name );
	}
	
	
	@Override public boolean removeMethod ( Method m )
	{
		return myMethods.remove ( m );
	}
	
	@Override public boolean isAbstractClass ( )
	{
		return true;
	}
	
	@Override public boolean isAbstract ( )
	{
		return true;
	}

}
