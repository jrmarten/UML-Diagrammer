package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.*;

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
	public boolean removeAttribute(String str) {
		return false;
	}

	@Override
	public boolean addAssociation(UMLClass c) {
		return false;
	}

	@Override
	public boolean removeAssociation(UMLClass c) {
		return false;
	}

	@Override
	public boolean addSuperclass(UMLClass c) {
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
}
