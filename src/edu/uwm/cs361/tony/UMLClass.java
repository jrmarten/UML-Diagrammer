package edu.uwm.cs361.tony;

import java.util.*;
import java.io.Serializable;

/**
 * A JModellerClass represents a class in a class diagram. It is known by its name
 * and has attributes and methods and keeps track of its superclasses, associations
 * and dependencies.
 */
public class UMLClass implements Serializable {

	private static class Attribute {
		enum Accessibility { PUBLIC, PRIVATE, PROTECTED, NONE }
		enum Modifier { STATIC, ABSTRACT, NONE }

		Accessibility accessibility;
		Modifier modifier;
		String name;

		/**
		 * Constructors
		 */
		Attribute() {
			this(Accessibility.NONE, Modifier.NONE, "Attribute");
		}

		Attribute(String name) {
			this(Accessibility.NONE, Modifier.NONE, name);
		}

		Attribute(Accessibility access, Modifier mod, String name) {
			this.accessibility = access;
			this.modifier = mod;
			this.name = name;
		}

		Accessibility getAccessibility() {
			return accessibility;
		}

		void setAccessibility(Accessibility accessibility) {
			this.accessibility = accessibility;
		}

		Modifier getModifier() {
			return modifier;
		}

		void setModifier(Modifier modifier) {
			this.modifier = modifier;
		}

		String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}
	}

	private static class Method {
		enum Accessibility { PUBLIC, PRIVATE, PROTECTED, NONE }
		enum Modifier { STATIC, ABSTRACT, NONE }

		Accessibility accessibility;
		Modifier modifier;
		String name;

		/**
		 * Constructors
		 */
		Method() {
			this("Attribute");
		}

		Method(String name) {
			this(Accessibility.NONE, Modifier.NONE, name);
		}

		Method(Accessibility access, Modifier mod, String name) {
			this.accessibility = access;
			this.modifier = mod;
			this.name = name;
		}

		Accessibility getAccessibility() {
			return accessibility;
		}

		void setAccessibility(Accessibility accessibility) {
			this.accessibility = accessibility;
		}

		Modifier getModifier() {
			return modifier;
		}

		void setModifier(Modifier modifier) {
			this.modifier = modifier;
		}

		String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}
	}

	private static class AssociatedClass {
		enum Association { AGGREGATION, COMPOSITION, UNIDIRECTIONAL, BIDIRECTIONAL, NONE }
		
		UMLClass associate;
		Association assocType;

		/**
		 * Constructors
		 */
		AssociatedClass(UMLClass associate) {
			this(associate, Association.NONE);
		}

		AssociatedClass(UMLClass associate, Association type) {
			this.associate = associate;
			this.assocType = type;
		}

		public UMLClass getAssociate() {
			return associate;
		}

		public void setAssociate(UMLClass associate) {
			this.associate = associate;
		}

		public Association getAssocType() {
			return assocType;
		}

		public void setAssocType(Association assocType) {
			this.assocType = assocType;
		}
	}

	/**
	 * Name of the class to represent
	 */
	private String myName;

	/**
	 * Names of attribute in the class
	 */
	private ArrayList<Attribute> myAttributes;

	/**
	 * Names of methods in the class
	 */
	private ArrayList<Method> myMethods;

	/**
	 * Associated classes
	 */
	private ArrayList<AssociatedClass> myAssociatedClasses;

	/**
	 * Direct superclasses (multiple inheritance is possible)
	 */
	private ArrayList<UMLClass> mySuperclasses;

	/**
	 * Classes upon which the current class is dependent
	 */
	private ArrayList<UMLClass> myDependClasses;

	static final long serialVersionUID = -3748332488864682801L;

	/**
	 * Create a new UMLClass instance
	 */
	public UMLClass() {
		this("Class");
	}

	/**
	 * Create a new UMLClass instance with a given name
	 *
	 * @param newClassName name of the class
	 */    
	public UMLClass(String newClassName) {
		setName(newClassName);
		myAttributes = new ArrayList<Attribute>();
		myMethods = new ArrayList<Method>();
		myAssociatedClasses = new ArrayList<AssociatedClass>();
		mySuperclasses = new ArrayList<UMLClass>();
		myDependClasses = new ArrayList<UMLClass>();
	}

	/**
	 * Set the name of the class. The name can be altered after a JModellerClass
	 * has been created.
	 *
	 * @param newName new name of the class
	 */    
	public void setName(String newName) {
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

	/**
	 * Add an attribute with a given name
	 *
	 * @param newAttribute name of an attribute to be added
	 */
	public void addAttribute(String newAttribute) {
		myAttributes.add(new Attribute(newAttribute));
	}

	/**
	 * Remove an attribute with a given name
	 *
	 * @param oldAttribute name of the attribute to be removed
	 */
	public void removeAttribute(String oldAttribute) {
		for (Attribute a : myAttributes) {
			if (a.getName() == oldAttribute)
				myAttributes.remove(a);
		}
	}

	/**
	 * Rename an attribute with a given name if the attribute exists.
	 *
	 * @param oldAttribute name of the attribute to be renamed
	 * @param newAttribute new attribute name
	 */
	public void renameAttribute(String oldAttribute, String newAttribute) {
		for (Attribute a : myAttributes) {
			if (a.getName() == oldAttribute)
				a.setName(newAttribute);
		}
	}

	/**
	 * Return an iterator over all attribute names
	 *
	 * @return iterator over all attribute names
	 */
	public Iterator getAttributes() {
		return myAttributes.iterator();
	}

	/**
	 * Return the number of attributes in this class
	 *
	 * @return number of attributes
	 */
	public int getNumberOfAttributes() {
		return myAttributes.size();
	}

	/**
	 * Test whether an attribute with a specific name exists in this class already
	 *
	 * @return true, if the attribute exists, false otherwise
	 */
	public boolean hasAttribute(String checkAttributeName) {
		for (Attribute a : myAttributes) {
			if (a.getName() == checkAttributeName)
				return true;
		}

		return false;
	}

	/**
	 * Add a method with a given name
	 *
	 * @param newMethod name of a method to be added
	 */        
	public void addMethod(String newMethod) {
		myMethods.add(new Method(newMethod));
	}

	/**
	 * Remove an method with a given name
	 *
	 * @param oldMethod name of the method to be removed
	 */    
	public void removeMethod(String oldMethod) {
		for (Method m : myMethods){
			if (m.getName() == oldMethod)
				myMethods.remove(m);
		}
	}

	/**
	 * Rename an method with a given name if the method exists.
	 *
	 * @param oldMethod name of the method to be renamed
	 * @param newMethod new method name
	 */    
	public void renameMethod(String oldMethod, String newMethod) {
		for (Method m : myMethods) {
			if (m.getName() == oldMethod)
				m.setName(newMethod);
		}
	}

	/**
	 * Return an iterator over all method names
	 *
	 * @return iterator over all method names
	 */
	public Iterator getMethods() {
		return myMethods.iterator();
	}

	/**
	 * Return the number of methods in this class
	 *
	 * @return number of methods
	 */
	public int getNumberOfMethods() {
		return myMethods.size();
	}

	/**
	 * Test whether an method with a specific name exists in this class already
	 *
	 * @return true, if the method exists, false otherwise
	 */
	public boolean hasMethod(String checkMethodName) {
		for (Method m : myMethods) {
			if (m.getName() == checkMethodName)
				return true;
		}
		
		return false;
	}

	/**
	 * Add another class as associated class.
	 *
	 * @param newAssociatedClass associated class
	 */
	public void addAssociation(UMLClass newAssociatedClass) {
		myAssociatedClasses.add(new AssociatedClass(newAssociatedClass));
	}

	/**
	 * Remove an associated class.
	 *
	 * @param oldAssociatedClass associated class to be removed
	 */    
	public void removeAssociation(UMLClass oldAssociatedClass) {
		myAssociatedClasses.remove(oldAssociatedClass);
	}

	/**
	 * Test whether another class is an associated class
	 *
	 * @return true, if the class is associated, false otherwise
	 */
	public boolean hasAssociation(UMLClass checkAssociatedClass) {
		return myAssociatedClasses.contains(checkAssociatedClass);
	}

	/**
	 * Return an iterator containing all associated classes
	 *
	 * @return iterator over associated classes
	 */    
	public Iterator getAssociations() {
		return myAssociatedClasses.iterator();
	}

	/**
	 * Add another class as superclass. This class becomes a subclass of
	 * the other class.
	 *
	 * @param newSuperclass superclass to be added
	 */    
	public void addSuperclass(UMLClass newSuperclass) {
		mySuperclasses.add(newSuperclass);
	}

	/**
	 * Remove a superclass so this class is not longer a subclass of the other class
	 *
	 * @param oldSuperclass superclass to be removed
	 */
	public void removeSuperclass(UMLClass oldSuperclass) {
		mySuperclasses.remove(oldSuperclass);
	}

	/**
	 * Return an iterator containing all superclasses
	 *
	 * @return iterator over superclasses
	 */
	public Iterator getSuperclasses() {
		return mySuperclasses.iterator();
	}   

	/**
	 * Checks whether class has an inheritance cycle. A inheritance
	 * cycle is encountered
	 * - if the possible subclass it the same as the current class
	 * - if the possible subclass is already a superclass of the current class
	 *
	 * @param   possibleSuperclass  class to which should
	 */
	public boolean hasInheritanceCycle(UMLClass possibleSubclass) {
		if (possibleSubclass == this) {
			return true;
		}

		return possibleSubclass.isSuperclass(this);

	}

	/**
	 * Checks whether this class is the superclass of the class to test
	 *
	 * @param   possibleSubclass    class which should be subclass to this class or its superclasses
	 */
	public boolean isSuperclass(UMLClass possibleSubclass) {
		if (possibleSubclass.mySuperclasses.contains(this)) {
			return true;
		}

		Iterator i = possibleSubclass.getSuperclasses();
		while (i.hasNext()) {
			Object currentObject = i.next();
			if (isSuperclass((UMLClass) currentObject)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Add another class so this class becomes a dependent class of the other
	 *
	 * @param newDependency class upon which this class should depend
	 */    
	public void addDependency(UMLClass newDependency) {
		myDependClasses.add(newDependency);
	}

	/**
	 * Remove a class on which this class is dependent
	 *
	 * @param oldDependency dependency class to be removed
	 */
	public void removeDependency(UMLClass oldDependency) {
		myDependClasses.remove(oldDependency);
	}

	/**
	 * Test whether this class is dependent on the given class
	 *
	 * @return true, if this class is dependent, false otherwise
	 */
	public boolean hasDependency(UMLClass checkDependency) {
		return myDependClasses.contains(checkDependency);
	}

	/**
	 * Return an iterator containing all classes on which this class is dependent
	 *
	 * @return iterator over all classes on which this class depends
	 */
	public Iterator getDependencies() {
		return myDependClasses.iterator();
	}
}