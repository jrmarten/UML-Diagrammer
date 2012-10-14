package edu.uwm.cs361.classdiagram.data;


public class UMLInterface extends UMLClass
{
	public UMLInterface ( ) { super(); }
	public UMLInterface ( String name ) { super ( name ); }
	
	@Override public boolean addAttribute ( Attribute attr )
	{
		return false;
	}
	
	@Override public boolean removeAttribute ( Attribute attr )
	{
		return false;
	}
	
	@Override public boolean removeAttribute ( String str )
	{
		return false;
	}
	
	@Override public boolean addAssociation ( UMLClass c )
	{
		return false;
	}
	
	@Override public boolean removeAssociation ( UMLClass c )
	{
		return false;
	}
	
	@Override public boolean addSuperclass ( UMLClass c )
	{
		if ( c == null ) return false;
		if ( !(c instanceof UMLInterface) ) return false;
		return mySuperClasses.add ( c );
	}
}
