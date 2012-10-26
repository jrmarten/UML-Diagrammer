package edu.uwm.cs361.classdiagram.data;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class UMLAbstractClass extends UMLClass
{

	public UMLAbstractClass()
	{
		super();
	}

	public UMLAbstractClass(String name)
	{
		super(name);
	}

	// short cut the abstract testing in
	// umlclass
	@Override
	public boolean removeMethod(Method m) {
		if (m == null)
			return false;
		return myMethods.remove(m);
	}

	@Override
	public boolean isAbstractClass() {
		return true;
	}

	@Override
	public boolean isAbstract() {
		return true;
	}

	@Override
	public String getDeclaration() {
		return "abstract class " + getName() + getInheritence();
	}

	@Override
	public Object clone() {
		UMLClass result = new UMLAbstractClass(getName());
		result.generics = (LinkedList<String>) generics.clone();
		result.myAttributes = (LinkedList<Attribute>) myAttributes.clone();
		result.myMethods = (LinkedList<Method>) myMethods.clone();
		result.myAssociatedClasses = (LinkedList<UMLClass>) myAssociatedClasses
				.clone();
		result.myDependClasses = (LinkedList<UMLClass>) myDependClasses.clone();
		result.myInterfaces = (LinkedList<UMLClass>) myInterfaces.clone();
		return result;
	}
	
	public String getType ( )
	{
		return "abstract";
	}
}
