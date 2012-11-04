package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.dprint;
import static edu.uwm.cs361.Util.join;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

import edu.uwm.cs361.Util;

public class UMLClass implements Serializable
{

	protected String								myName;
	protected LinkedList<Attribute>	myAttributes;
	protected LinkedList<Method>		myMethods;
	
	
	protected LinkedList<NamedType> aggregation;
	protected LinkedList<UMLClass>	myAssociatedClasses;
	protected UMLClass							superClass;
	protected LinkedList<UMLClass>	myInterfaces;
	protected LinkedList<UMLClass>	myDependClasses;
	private boolean									abstractp					= false;

	static final long								serialVersionUID	= -3748332488864682801L;

	/**
	 * Create a new JModellerClass instance
	 */
	public UMLClass()
	{
		myName = "Class";
		myAttributes = new LinkedList<Attribute>();
		aggregation = new LinkedList<NamedType> ( );
		myMethods = new LinkedList<Method>();
		myAssociatedClasses = new LinkedList<UMLClass>();
		myInterfaces = new LinkedList<UMLClass>();
		myDependClasses = new LinkedList<UMLClass>();
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

	public LinkedList<NamedType> getAggregation ( )
	{
		return aggregation;
	}


	public Collection<UMLClass> getAssociations() {
		return myAssociatedClasses;
	}
	

	public Collection<UMLClass> getSuperclasses() {
		LinkedList<UMLClass> result = new LinkedList<UMLClass>(myInterfaces);
		if (superClass != null)
			result.add(superClass);

		return result;
	}
	

	public Collection<UMLClass> getDependencies() {
		return myDependClasses;
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

	// **********************************************

	// Associations

	// not much to do

	public boolean addAss(UMLClass newAssociatedClass) {
		if (newAssociatedClass == null)
			return false;
		return myAssociatedClasses.add(newAssociatedClass);
	}

	public boolean removeAssociation(UMLClass oldAssociatedClass) {
		if (oldAssociatedClass == null)
			return false;
		return myAssociatedClasses.remove(oldAssociatedClass);
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

	// *******************************************************************

	// Dependencies

	public boolean addDependency(UMLClass newDependency) {
		if (newDependency == null)
			return false;
		return myDependClasses.add(newDependency);
	}

	public boolean removeDependency(UMLClass oldDependency) {
		if (oldDependency == null)
			return false;
		return myDependClasses.remove(oldDependency);
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

	// TODO: add implement and extends functionallity
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
		result.aggregation = (LinkedList<NamedType>) aggregation.clone();
		result.myAttributes = (LinkedList<Attribute>) myAttributes.clone();
		result.myMethods = (LinkedList<Method>) myMethods.clone();
		result.myAssociatedClasses = (LinkedList<UMLClass>) myAssociatedClasses
				.clone();
		result.myDependClasses = (LinkedList<UMLClass>) myDependClasses.clone();
		result.myInterfaces = (LinkedList<UMLClass>) myInterfaces.clone();

		return result;
	}

	
	public boolean add ( UMLClass type, Connection con )  { return add ( type, "", con ); }
	public boolean add ( UMLClass type, String name, Connection con )
	{
		if ( type == null || name == null || con == null ) return false;
		
		if ( con == Connection.INHERITANCE )
			{
				return addSuper ( type );
			}
		if ( con == Connection.AGGREGATION )
			{
				return addAgg ( type, name );
			}
		if ( con == Connection.ASSOCIATION )
			{
				return addAss( type, name );
			}
		
		
		
		return false;
	}
	
	private boolean addSuper ( UMLClass type )
	{
		if ( type instanceof UMLInterface )
			{
				if ( myInterfaces.contains( type ) ) return false;
				return myInterfaces.add( type );
			}
		else
			{
				if ( isSuper ( this, type ) ) return false;
				superClass = type;
				return true;
			}
	}
	
	private boolean addAgg ( UMLClass type, String name )
	{
		NamedType tmp = new NamedType();
		tmp.type = type;
		if ( Util.contains( aggregation, tmp , NamedType.getTypeComp()) ) return false;
		tmp.name = name;
		return aggregation.add( tmp );
	}
	
	private boolean addAss(UMLClass type, String name) {
		if (type == null)
			return false;
		return myAssociatedClasses.add(type);
	}
	
	public static class NamedType 
	{
		public String name;
		public UMLClass type;
		
		private static TypedComparer typeComp = new TypedComparer ( ); 
		
		public static Comparator getTypeComp ( ) { return typeComp; }
		
		private static class TypedComparer implements Comparator
		{
			public static int ERROR = -1;
			
			@Override
			public int compare(Object a, Object b) {
				if ( a instanceof NamedType && b instanceof NamedType )
					{
						NamedType aT = (NamedType) a;
						NamedType bT = (NamedType) b;
						
						return (aT.type.getName().equals(bT.type.getName()))?0:-1;
					}
				return ERROR;
			}
		}
	}
}
