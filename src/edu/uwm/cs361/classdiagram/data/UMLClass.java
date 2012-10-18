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

	protected UMLClass							superClass;
	protected LinkedList<UMLClass>	myInterfaces;

	protected LinkedList<UMLClass>	myDependClasses;
	private boolean									abstractp					= false;

	public static final String			idreg							= "[A-Za-z_$][A-Za-z0-9_$]*";
	public static final String			classreg					= ".*";

	static final long								serialVersionUID	= -3748332488864682801L;

	/**
	 * Create a new JModellerClass instance
	 */
	public UMLClass()
	{
		myName = "Class";
		generics = new LinkedList<String>();
		myAttributes = new LinkedList<Attribute>();
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

		generics.clear();
		if (newName.contains("<"))
			{
				String tmp;
				myName = newName.substring(0, newName.indexOf("<"));
				tmp = newName.substring(newName.indexOf("<") + 1, newName.indexOf(">"));
				String[] classnames = tmp.split(" , ");
				for (String cl : classnames)
					{
						cl = cl.trim();
						if (!cl.equals(""))
							generics.add(cl);
					}
			}
	}

	/**
	 * Return the name of the class
	 * 
	 * @return name of the class
	 */
	public String getName() {
		return myName;
	}

	public String getGenerics() {
		Iterator<String> it = generics.iterator();
		String buf = "";
		while (it.hasNext())
			{
				buf += it.next();
				if (!it.hasNext())
					break;
				buf += ", ";
			}
		return "<" + buf + ">";
	}

	public boolean isAbstractClass() {
		return false;
	}

	public boolean isAbstract() {
		return abstractp;
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

	public boolean removeAttribute(String attrname) {
		if (attrname == null)
			return false;
		for (Attribute attr : myAttributes)
			{
				if (attr.getName().equals(attrname))
					return myAttributes.remove(attr);
			}
		return false;
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

		if (oldMethod.isAbstract() && abstractp)
			{
				boolean is_abstract = false;
				for (Method m : myMethods)
					{
						if (m.isAbstract())
							{
								is_abstract = false;
								break;
							}
					}
				if (!is_abstract)
					abstractp = false;
			}
		return result;
	}

	public Collection<Method> getMethods() {
		return myMethods;
	}

	// **********************************************

	// Associations

	// not much to do

	public boolean addAssociation(UMLClass newAssociatedClass) {
		if (newAssociatedClass == null)
			return false;
		return myAssociatedClasses.add(newAssociatedClass);
	}

	public boolean removeAssociation(UMLClass oldAssociatedClass) {
		if (oldAssociatedClass == null)
			return false;
		return myAssociatedClasses.remove(oldAssociatedClass);
	}

	public Collection<UMLClass> getAssociations() {
		return myAssociatedClasses;
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

	public Collection<UMLClass> getSuperclasses() {
		LinkedList<UMLClass> result = new LinkedList<UMLClass>(myInterfaces);
		if (superClass != null)
			result.add(superClass);

		return result;
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

	public Collection<UMLClass> getDependencies() {
		return myDependClasses;
	}

	// *********************************************************************

	// helps java generator

	// TODO: add implement and extends functionallity
	public String getDeclaration() {
		String buffer = "";

		buffer += (superClass == null) ? "" : (" extends " + superClass.getName());

		if (myInterfaces.size() > 0)
			{
				buffer += " implements " + join(myInterfaces, ", ");
			}

		return ((abstractp) ? "abstract " : "") + "class " + getName() + buffer;
	}

	@Override
	public String toString() {
		return getName();
	}
}
