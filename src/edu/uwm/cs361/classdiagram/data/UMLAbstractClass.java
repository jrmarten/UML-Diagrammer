package edu.uwm.cs361.classdiagram.data;

import java.util.LinkedList;
//TODO: clean commented out segments

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

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		UMLClass result = new UMLAbstractClass(getName());
		result.myAttributes = (LinkedList<Attribute>) myAttributes.clone();
		result.myMethods = (LinkedList<Method>) myMethods.clone();
		result.myInterfaces = (LinkedList<UMLClass>) myInterfaces.clone();
		return result;
	}
	
	@Override
	public String getType ( )
	{
		return "abstract";
	}
}
