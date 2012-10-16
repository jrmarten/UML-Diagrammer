package edu.uwm.cs361.classdiagram.data;

public class UMLAbstractClass extends UMLClass
{

	public UMLAbstractClass ( )
	{
		super ( );
	}

	public UMLAbstractClass ( String name )
	{
		super ( name );
	}

	// short cut the abstract testing in
	// umlclass
	@Override
	public boolean removeMethod ( Method m )
	{
		return myMethods.remove ( m );
	}

	@Override
	public boolean isAbstractClass ( )
	{
		return true;
	}

	@Override
	public boolean isAbstract ( )
	{
		return true;
	}

	@Override
	public String getDeclaration ( )
	{
		return "abstract class " + getName ( );
	}
}
