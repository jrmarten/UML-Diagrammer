package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.*;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class UMLInterface extends UMLClass
{
	public UMLInterface()
	{
		super();
	}

	public UMLInterface(String name)
	{
		super(name);
	}

	@Override
	public boolean addAttribute(Attribute attr) {
		return false;
	}

	@Override
	public boolean removeAttribute(Attribute attr) {
		return false;
	}

	@Override
	public boolean addSuperclass(UMLClass c) {
		if (c == null)
			return false;
		boolean interfacep = c instanceof UMLInterface;

		if (!interfacep)
			return false;
		if (isSuper(this, c))
			return false;

		return myInterfaces.add(c);
	}

	@Override
	public String getDeclaration() {
		String buffer = "";
		if (myInterfaces.size() > 0)
			{
				buffer += " implements " + join(myInterfaces, ", ");
			}

		return "interface " + getName() + buffer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		UMLClass result = new UMLInterface(getName());
		result.myAttributes = (LinkedList<Attribute>) myAttributes.clone();
		result.myInterfaces = (LinkedList<UMLClass>) myInterfaces.clone();
		return result;
	}
	
	@Override
	public String getType ( )
	{
		return "interface";
	}
}
