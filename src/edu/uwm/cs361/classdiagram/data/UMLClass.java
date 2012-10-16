package edu.uwm.cs361.classdiagram.data;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import static edu.uwm.cs361.Util.*;

public class UMLClass implements Serializable
{

	protected String								myName;
	protected LinkedList<String>		generics;
	protected LinkedList<Attribute>	myAttributes;
	protected LinkedList<Method>		myMethods;
	protected LinkedList<UMLClass>	myAssociatedClasses;
	protected LinkedList<UMLClass>	mySuperClasses;
	protected LinkedList<UMLClass>	myDependClasses;
	private boolean									abstractp					= false;
	private int											superclasses			= 0;

	public static final String			idreg							= "[A-Za-z_$][A-Za-z0-9_$]*";
	public static final String			classreg					= ".*";

	static final long								serialVersionUID	= -3748332488864682801L;

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

	public boolean isAbstractClass ( )
	{
		return false;
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

	public Collection<Attribute> getAttributes ( )
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
						&& !Method.overloaded ( meth, newMethod ) )
					{
						dprint ( "Same name, but is not overloaded: " + newMethod + ", "
								+ meth );
						return false;
					}
			}

		if ( newMethod.isAbstract ( ) ) abstractp = true;

		return myMethods.add ( newMethod );
	}

	public boolean removeMethod ( Method oldMethod )
	{
		boolean result = myMethods.remove ( oldMethod );

		if ( oldMethod.isAbstract ( ) && abstractp )
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
		return result;
	}

	public Collection<Method> getMethods ( )
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

	public Collection<UMLClass> getAssociations ( )
	{
		return myAssociatedClasses;
	}

	// *********************************************

	// Inheritance

	// TODO: Test
	// XXX: not completely correct
	public boolean addSuperclass ( UMLClass par )
	{
		boolean interfacep = par instanceof UMLInterface;
		if ( superclasses > 0 && !interfacep ) return false;
		if ( isSuper ( this, par ) ) return false;
		if ( !interfacep ) superclasses++;
		return mySuperClasses.add ( par );
	}

	public boolean isSuper ( UMLClass par, UMLClass child )
	{
		for ( UMLClass tmp : child.getSuperclasses ( ) )
			{
				if ( tmp.equals ( par ) ) return true;
				if ( isSuper ( par, tmp ) ) return true;
			}
		return false;
	}

	public boolean removeSuperclass ( UMLClass oldSuperclass )
	{
		if ( ! ( oldSuperclass instanceof UMLInterface ) ) superclasses--;
		return mySuperClasses.remove ( oldSuperclass );
	}

	public Collection<UMLClass> getSuperclasses ( )
	{
		return mySuperClasses;
	}

	// *******************************************************************

	// Dependencies

	public boolean addDependency ( UMLClass newDependency )
	{
		return myDependClasses.add ( newDependency );
	}

	public boolean removeDependency ( UMLClass oldDependency )
	{
		return myDependClasses.remove ( oldDependency );
	}

	public Collection<UMLClass> getDependencies ( )
	{
		return myDependClasses;
	}

	// *********************************************************************

	// helps java generator

	// TODO: add implement and extends functionallity
	public String getDeclaration ( )
	{
		String implementbuffer = "";
		String extendbuffer = "";

		for ( UMLClass tmp : getSuperclasses ( ) )
			{
				if ( tmp instanceof UMLInterface ) implementbuffer += tmp.getName ( )
						+ ", ";
			}

		return ( ( abstractp )? "abstract " : "" ) + "class " + getName ( );
	}

	@Override
	public String toString ( )
	{
		return getDeclaration ( );
	}
}
