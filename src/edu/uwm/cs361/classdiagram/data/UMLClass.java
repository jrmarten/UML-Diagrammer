package edu.uwm.cs361.classdiagram.data;

import java.util.*;
import java.io.Serializable;

public class UMLClass implements Serializable
{

	private String								myName;
	private LinkedList<String>		generics;
	private LinkedList<Attribute>	myAttributes;
	private LinkedList<Method>		myMethods;
	private LinkedList<UMLClass>	myAssociatedClasses;
	private LinkedList<UMLClass>	mySuperClasses;
	private LinkedList<UMLClass>	myDependClasses;
	private boolean								abstractp						= false;
	private boolean								abstract_declaired	= false;

	public static final String		idreg								= "[A-Za-z_$][A-Za-z0-9_$]*";
	public static final String		classreg						= ".*";

	static final long							serialVersionUID		= -3748332488864682801L;

	public static Object[] cat ( Object[] obja, Object[] objb )
	{
		Object[] result = new Object[ obja.length + objb.length ];
		int i = 0;
		for ( Object obj : obja )
			result[i++] = obj;
		for ( Object obj : objb )
			result[i++] = obj;
		return result;
	}

	/**
	 * Create a new JModellerClass instance
	 */
	public UMLClass ( )
	{
		myName = "Class";
		generics = new LinkedList<String> ( );
		myAttributes = new LinkedList<Attribute> ( );
		myMethods = new LinkedList<Method> ( );
		myAssociatedClasses = new LinkedList<UMLClass> ( );
		mySuperClasses = new LinkedList<UMLClass> ( );
		myDependClasses = new LinkedList<UMLClass> ( );
	}

	/**
	 * Create a new JModellerClass instance with a given name
	 * 
	 * @param newClassName
	 *          name of the class
	 */
	public UMLClass ( String newClassName )
	{
		this ( );
		setName ( newClassName );
	}

	/**
	 * Set the name of the class. The name can be altered after a JModellerClass
	 * has been created.
	 * 
	 * @param newName
	 *          new name of the class
	 */
	public void setName ( String newName )
	{
		myName = newName;

		generics.clear ( );
		if ( newName.contains ( "<" ) )
			{
				String tmp;
				myName = newName.substring ( 0, newName.indexOf ( "<" ) );
				tmp = newName.substring ( newName.indexOf ( "<" ) + 1,
						newName.indexOf ( ">" ) );
				String[] classnames = tmp.split ( " , " );
				for ( String cl : classnames )
					{
						cl = cl.trim ( );
						if ( !cl.equals ( "" ) ) generics.add ( cl );
					}
			}
	}

	/**
	 * Return the name of the class
	 * 
	 * @return name of the class
	 */
	public String getName ( )
	{
		return myName;
	}

	public String getGenerics ( )
	{
		Iterator<String> it = generics.iterator ( );
		String buf = "";
		while ( it.hasNext ( ) )
			{
				buf += it.next ( );
				if ( !it.hasNext ( ) ) break;
				buf += ", ";
			}
		return "<" + buf + ">";
	}

	public boolean isAbstract ( )
	{
		return abstractp;
	}

	// ************************************************************

	// Attribute Methods

	public boolean addAttribute ( Attribute newAttribute )
	{
		if ( newAttribute == null ) return false;
		for ( Attribute attr : myAttributes )
			{
				if ( attr.getName ( ).equals ( newAttribute.getName ( ) ) ) return false;
			}
		myAttributes.add ( newAttribute );
		return true;
	}

	public boolean removeAttribute ( Attribute oldAttribute )
	{
		return myAttributes.remove ( oldAttribute );
	}

	public boolean removeAttribute ( String attrname )
	{
		if ( attrname == null ) return false;
		for ( Attribute attr : myAttributes )
			{
				if ( attr.getName ( ).equals ( attrname ) ) return myAttributes
						.remove ( attr );
			}
		return false;
	}

	public Iterable<Attribute> getAttributes ( )
	{
		return myAttributes;
	}

	// *********************************************************************************

	// Methods

	public boolean addMethod ( Method newMethod )
	{
		if ( newMethod == null ) return false;

		for ( Method meth : myMethods )
			{
				if ( newMethod.getName ( ).equals ( meth.getName ( ) )
						&& !Method.overloaded ( meth, newMethod ) ) return false;
			}

		if ( newMethod.isAbstract ( ) ) abstractp = true;
		myMethods.add ( newMethod );

		return true;
	}

	public void removeMethod ( Method oldMethod )
	{
		myMethods.remove ( oldMethod );

		if ( oldMethod.isAbstract ( ) && abstractp && !abstract_declaired )
			{
				boolean is_abstract = false;
				for ( Method m : myMethods )
					{
						if ( m.isAbstract ( ) )
							{
								is_abstract = false;
								break;
							}
					}
				if ( !is_abstract ) abstractp = false;
			}
	}

	public Iterable<Method> getMethods ( )
	{
		return myMethods;
	}

	// **********************************************

	// Associations

	// not much to do

	public boolean addAssociation ( UMLClass newAssociatedClass )
	{
		return myAssociatedClasses.add ( newAssociatedClass );
	}

	public boolean removeAssociation ( UMLClass oldAssociatedClass )
	{
		return myAssociatedClasses.remove ( oldAssociatedClass );
	}

	public Iterable<UMLClass> getAssociations ( )
	{
		return myAssociatedClasses;
	}

	// *********************************************

	// Inheritence

	//TODO: Test
	public boolean addSuperclass ( UMLClass par )
	{
		if ( par.isSuper ( this ) ) return false;
		return mySuperClasses.add ( par );
	}
	
	public boolean isSuper ( UMLClass par )
	{
		for ( UMLClass cl : par.getSuperclasses ( ) )
			{
				if ( cl.equals ( par )) return true;
				if ( cl.isSuper ( par ) ) return true;
			}
		return false;
	}

	public void removeSuperclass ( UMLClass oldSuperclass )
	{
		mySuperClasses.remove ( oldSuperclass );
	}

	public Iterable<UMLClass> getSuperclasses ( )
	{
		return mySuperClasses;
	}

	//*******************************************************************
	
	//Dependencies

	public boolean addDependency ( UMLClass newDependency )
	{
		return myDependClasses.add ( newDependency );
	}
	
	public boolean removeDependency ( UMLClass oldDependency )
	{
		return myDependClasses.remove ( oldDependency );
	}

	public Iterable<UMLClass> getDependencies ( )
	{
		return myDependClasses;
	}
}
