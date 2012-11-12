package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.dprint;
import static edu.uwm.cs361.Util.join;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

import edu.uwm.cs361.Util;

//TODO: organize methods

@SuppressWarnings ( "all" )
public class UMLClass implements Serializable
{

	protected String											myName;
	protected LinkedList<Attribute>				myAttributes;
	protected LinkedList<Method>					myMethods;
	protected UMLClass										superClass;
	protected LinkedList<UMLClass>				myInterfaces;
	private boolean												abstractp					= false;
	
	protected LinkedList<Connection> 			_cons;

	static final long											serialVersionUID	= -3748332488864682801L;

	/**
	 * Create a new JModellerClass instance
	 */
	public UMLClass()
	{
		myName = "Class";
		myAttributes = new LinkedList<Attribute>();
		myMethods = new LinkedList<Method>();
		myInterfaces = new LinkedList<UMLClass>();
		_cons = new LinkedList<Connection>();
	}

	/**
	 * Create a new JModellerClass instance with a given name
	 * 
	 * @param newClassName
	 *          name of the class
	 */
	public UMLClass(String newClassName)
	{
		this();
		setName(newClassName);
	}

	/**
	 * Set the name of the class. The name can be altered after a JModellerClass
	 * has been created.
	 * 
	 * @param newName
	 *          new name of the class
	 */
	public void setName(String newName) {
		if (newName == null)
			return;
		myName = newName;
	}

	/**
	 * Return the name of the class
	 * 
	 * @return name of the class
	 */
	public String getName() {
		return myName;
	}
	
	public boolean isAbstractClass() {
		return false;
	}

	public boolean isAbstract() {
		return abstractp;
	}
	
	//*********************************************************************

	public LinkedList<Connection> getConnections ( )
	{
		return _cons;
	}
	
	public void addConnection ( Connection new_con )
	{
		_cons.add( new_con );
	}
	
	public void removeConnection ( Connection con )
	{
		_cons.remove( con );
	}
	
	
	//*********************************************************************

	public Collection<UMLClass> getSuperclasses() {
		LinkedList<UMLClass> result = new LinkedList<UMLClass>(myInterfaces);
		if (superClass != null)
			result.add(superClass);

		return result;
	}
	
	// ************************************************************

	// Attribute Methods

	public boolean addAttribute(Attribute newAttribute) {
		if (newAttribute == null)
			return false;
		for (Attribute attr : myAttributes)
			{
				if (attr.getName().equals(newAttribute.getName()))
					return false;
			}
		myAttributes.add(newAttribute);
		return true;
	}

	public boolean removeAttribute(Attribute oldAttribute) {
		if (oldAttribute == null)
			return false;
		return myAttributes.remove(oldAttribute);
	}

	public Collection<Attribute> getAttributes() {
		return myAttributes;
	}

	// *********************************************************************************

	// Methods

	public boolean addMethod(Method newMethod) {
		if (newMethod == null)
			return false;

		for (Method meth : myMethods)
			{
				if (newMethod.getName().equals(meth.getName())
						&& !Method.overloaded(meth, newMethod))
					{
						dprint("Same name, but is not overloaded: " + newMethod + ", "
								+ meth);
						return false;
					}
			}

		if (newMethod.isAbstract())
			abstractp = true;

		return myMethods.add(newMethod);
	}

	public boolean removeMethod(Method oldMethod) {
		if (oldMethod == null)
			return false;
		boolean result = myMethods.remove(oldMethod);
		
		Util.dprint( oldMethod + " is abstract: " + oldMethod.isAbstract() );
		
		if (oldMethod.isAbstract() && abstractp)
			{
				Util.dprint( "Checking for non-abstractness" );
				boolean is_abstract = false;
				for (Method m : myMethods)
					{
						Util.dprint( m );
						if (m.isAbstract())
							{
								is_abstract = true;
								break;
							}
					}
				if (!is_abstract)
					{
						Util.dprint( "droping abstractness" );
						abstractp = false;
					}
			}
		return result;
	}

	public LinkedList<Method> getMethods() {
		return myMethods;
	}
	
	// *********************************************

	// Inheritance

	// TODO: Test
	// XXX: not completely correct
	public boolean addSuperclass(UMLClass par) {
		if (par == null)
			return false;
		boolean interfacep = par instanceof UMLInterface;

		if (!interfacep)
			{
				if (isSuper(this, par))
					return false;
				superClass = par;
				return true;
			}

		if (myInterfaces.contains(par))
			return false;
		return myInterfaces.add(par);

	}

	public static boolean isSuper(UMLClass par, UMLClass child) {
		if (par == null || child == null)
			return false;

		if (par == child)
			return false;

		for (UMLClass tmp : child.getSuperclasses())
			{
				if (tmp.equals(par))
					return true;
				if (isSuper(par, tmp))
					return true;
			}
		return false;
	}

	public boolean removeSuperclass(UMLClass oldSuperclass) {
		if (oldSuperclass == null)
			return false;
		if (superClass != null && oldSuperclass.equals(superClass))
			{
				superClass = null;
				return true;
			}
		return myInterfaces.remove(oldSuperclass);
	}

	// *********************************************************************

	// helps java generator

	protected String getInheritence() {
		String buffer = "";

		buffer += (superClass == null) ? "" : (" extends " + superClass.getName());

		if (myInterfaces.size() > 0)
			{
				buffer += " implements " + join(myInterfaces, ", ");
			}
		return buffer;
	}

	public String getDeclaration() {
		return ((abstractp) ? "abstract " : "") + "class " + getName()
				+ getInheritence();
	}
	
	public String getType ( )
	{
		return "class";
	}

	@Override
	public String toString() {
		return getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		UMLClass result = new UMLClass(getName());
		result.abstractp = abstractp;
		result.myAttributes = (LinkedList<Attribute>) myAttributes.clone();
		result.myMethods = (LinkedList<Method>) myMethods.clone();
		result.myInterfaces = (LinkedList<UMLClass>) myInterfaces.clone();
		result._cons = (LinkedList<Connection>) _cons.clone();
		
		return result;
	}
}
