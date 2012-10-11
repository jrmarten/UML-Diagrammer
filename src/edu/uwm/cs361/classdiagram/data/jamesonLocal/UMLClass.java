package edu.uwm.cs361.classdiagram.data.jamesonLocal;

import java.util.*;
import java.io.Serializable;
import edu.uwm.cs361.classdiagram.data.*;

public class UMLClass implements Serializable {

    private String myName;
    private LinkedList<Attribute> myAttributes;
    private LinkedList<Method> myMethods;
    private LinkedList<UMLClass> myAssociatedClasses;
    private LinkedList<UMLClass> mySuperClasses;
    private LinkedList<UMLClass> myDependClasses;

    public static final String idreg = "[A-Za-z_$][A-Za-z0-9_$]*";
    public static final String classreg = ".*";
    
    static final long serialVersionUID = -3748332488864682801L;

    public static Object[] cat ( Object[] obja, Object[] objb )
    {
    	Object[] result = new Object[obja.length+objb.length];
    	int i = 0;
    	for ( Object obj : obja ) result[i++] = obj;
    	for ( Object obj : objb ) result[i++] = obj;
    	return result;
    }
    
    /**
     * Create a new JModellerClass instance
     */
    public UMLClass() {
        myAttributes = new LinkedList<Attribute>();
        myMethods = new LinkedList<Method> ();
        myAssociatedClasses = new LinkedList<UMLClass>();
        mySuperClasses = new LinkedList<UMLClass> ( );
        myDependClasses = new LinkedList<UMLClass> ( );
    }

    /**
     * Create a new JModellerClass instance with a given name
     *
     * @param newClassName name of the class
     */
    public UMLClass(String newClassName) {
        this ( );
    	setName(newClassName);
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
    public void addAttribute(Attribute newAttribute) {
        myAttributes.add(newAttribute);
    }

    /**
     * Remove an attribute with a given name
     *
     * @param oldAttribute name of the attribute to be removed
     */
    public void removeAttribute(Attribute oldAttribute) {
        myAttributes.remove(oldAttribute);
    }

    /**
     * Rename an attribute with a given name if the attribute exists.
     *
     * @param oldAttribute name of the attribute to be renamed
     * @param newAttribute new attribute name
     */
    public void renameAttribute(Attribute oldAttribute, Attribute newAttribute) {
        int attributeIndex = myAttributes.indexOf(oldAttribute);
        if (attributeIndex >= 0) {
            myAttributes.remove(attributeIndex);
            myAttributes.add(attributeIndex, newAttribute);
        }
    }

    /**
     * Return an iterator over all attribute names
     *
     * @return iterator over all attribute names
     */
    public final Iterator<Attribute> getAttributes() {
        return myAttributes.iterator();
    }

    /**
     * Test whether an attribute with a specific name exists in this class already
     *
     * @return true, if the attribute exists, false otherwise
     */
    public boolean hasAttribute(Attribute checkAttributeName) {
        return myAttributes.contains(checkAttributeName);
    }

    /**
     * Add a method with a given name
     *
     * @param newMethod name of a method to be added
     */
    public void addMethod(Method newMethod) {
        myMethods.add(newMethod);
    }

    /**
     * Remove an method with a given name
     *
     * @param oldMethod name of the method to be removed
     */
    public void removeMethod(Method oldMethod) {
        myMethods.remove(oldMethod);
    }

    /**
     * Rename an method with a given name if the method exists.
     *
     * @param oldMethod name of the method to be renamed
     * @param newMethod new method name
     */
    public void renameMethod(Method oldMethod, Method newMethod) {
        int methodIndex = myMethods.indexOf(oldMethod);
        if (methodIndex >= 0) {
            myMethods.remove(methodIndex);
            myMethods.add(methodIndex, newMethod );
        }
    }

    /**
     * Return an iterator over all method names
     *
     * @return iterator over all method names
     */
    public final Iterator<Method> getMethods() {
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
        return myMethods.contains(checkMethodName);
    }

    /**
     * Add another class as associated class.
     *
     * @param newAssociatedClass associated class
     */
    public void addAssociation(UMLClass newAssociatedClass) {
        myAssociatedClasses.add(newAssociatedClass);
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
    public Iterator<UMLClass> getAssociations() {
        return myAssociatedClasses.iterator();
    }

    /**
     * Add another class as superclass. This class becomes a subclass of
     * the other class.
     *
     * @param newSuperclass superclass to be added
     */
    public void addSuperclass(UMLClass newSuperclass) {
        mySuperClasses.add(newSuperclass);
    }

    /**
     * Remove a superclass so this class is not longer a subclass of the other class
     *
     * @param oldSuperclass superclass to be removed
     */
    public void removeSuperclass(UMLClass oldSuperclass) {
        mySuperClasses.remove(oldSuperclass);
    }

    /**
     * Return an iterator containing all superclasses
     *
     * @return iterator over superclasses
     */
    public Iterator<UMLClass> getSuperclasses() {
        return mySuperClasses.iterator();
    }

    /**
     * Checks whether class has an inheritance cycle. A inheritance
     * cycle is encountered
     * - if the possible subclass it the same as the current class
     * - if the possible subrclass is already a superclass of the current class
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
        if (possibleSubclass.mySuperClasses.contains(this)) {
            return true;
        }

        Iterator<UMLClass> i = possibleSubclass.getSuperclasses();
        while (i.hasNext()) {
            Object currentObject = i.next();
            if (isSuperclass((UMLClass) currentObject)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add another class so this class becomes a dependend class of the other
     *
     * @param newDependency class upon which this class should depend
     */
    public void addDependency(UMLClass newDependency) {
        myDependClasses.add(newDependency);
    }

    /**
     * Remove a class on which this class is dependend
     *
     * @param oldDependency dependency class to be removed
     */
    public void removeDependency(UMLClass oldDependency) {
        myDependClasses.remove(oldDependency);
    }

    /**
     * Test whether this class is dependend on the given class
     *
     * @return true, if this class is dependend, false otherwise
     */
    public boolean hasDependency(UMLClass checkDependency) {
        return myDependClasses.contains(checkDependency);
    }

    /**
     * Return an iterator containing all classes on which this class is dependend
     *
     * @return iterator over all classes on which this class depends
     */
    public Iterator<UMLClass> getDependencies() {
        return myDependClasses.iterator();
    }
}

