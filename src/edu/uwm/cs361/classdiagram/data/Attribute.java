package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.report;

public class Attribute
{

	private String								name;
	private String								type;
	private Access								access	= Access.DEFAULT;
	private boolean								staticp	= false;
	private boolean								finalp	= false;

	private static final String[]	mods		= { "private", "-", "public", "+",
			"default", "~", "protected", "#", "static", "final", "s", "f" };

	public Attribute()
	{
	}

	private Attribute(String[] perms, String name, String type)
	{
		this.name = name;
		this.type = type;

		for (String perm : perms)
			{
				perm = perm.trim();
				if (perm.equals(""))
					continue;

				char tmp = perm.charAt(0);

				switch (tmp)
					{
						case 's':
						case 'S':
							staticp = true;
						break;

						case 'f':
						case 'F':
							finalp = true;
						break;

						default:
							if (access != Access.DEFAULT)
								continue;
							access = Access.fromString(perm);
					}
			}
		if ( finalp )
			this.name = this.name.toUpperCase();
	}

	public static Attribute Create(String str) {
		if (str == null)
			return null;
		if (str.trim().equals(""))
			return null;
		try
			{
				if (str.contains(":"))
					return fromUML(str);
				else
					return fromSignature(str);
			} catch (Exception e)
			{
				return null;
			}
	}

	private static Attribute fromUML(String str) {
		String[] parts = str.split(":");
		if (parts.length != 2)
			return (Attribute) report("More than one colon in attribute declaration");

		String type = parts[1].trim();
		if (type.contains(" "))
			return (Attribute) report("(type) contains a space in attribute delcaration");

		parts = parts[0].split(" ");
		int index = parts.length - 1;
		String name = parts[index];
		parts[index] = "";

		Access a = Access.fromSymbol(name.charAt(0));
		if (a != null)
			{
				parts[index] = a.toString();
				name = name.substring(1);
			}

		return filter(parts, name, type);
	}

	private static Attribute fromSignature(String str) {
		String[] parts = str.split(" ");

		int nameIndex = parts.length - 1;

		String name = parts[nameIndex];
		String type = parts[nameIndex - 1];

		parts[nameIndex] = "";
		parts[nameIndex - 1] = "";

		Access a = Access.fromSymbol(name.charAt(0));
		if (a != null)
			{
				parts[nameIndex] = a.toString();
				name = name.substring(1);
			}
		return filter(parts, name, type);
	}

	private static boolean isValid(String in) {
		if (in.equals(""))
			return true;
		for (String mod : mods)
			{
				if (mod.equalsIgnoreCase(in))
					return true;
			}
		return false;
	}

	private static Attribute filter(String[] mods, String name, String type) {
		for (String mod : mods)
			{
				if (!isValid(mod))
					return (Attribute) report("Invalid Modifier in Attribute Declaration: "
							+ mod);
			}
		
		if ( Keywords.keywordp( type ) )
			return (Attribute) report ( "Invalid return type Attribute: " + type );

		boolean let = false;
		boolean sym = false;
		boolean num = false;

		if (Character.isDigit(name.charAt(0)))
			return (Attribute) report("Invalid name in Attribute Declaration");

		for (char ch : name.toCharArray())
			{
				let = Character.isLetter(ch);
				sym = ch == '_' || ch == '$';
				num = Character.isDigit(ch);

				if (!(let || sym || num))
					{
						return (Attribute) report("Name is Ill formated in Attribute Declaration");
					}
			}

		return new Attribute(mods, name, type);
	}

	@Override
	public Attribute clone() {
		Attribute result = new Attribute();
		result.access = access;
		result.finalp = finalp;
		result.staticp = staticp;
		result.type = type;
		result.name = name;
		return result;
	}

	@Override
	public String toString() {
		return access.getSymbol() + name + ":" + type;
	}

	public String getSignature() {
		return access.toString() + " " + ((staticp) ? "static " : "")
				+ ((finalp) ? "final " : "") + type + " " + name;
	}

	public boolean sigEquals(Attribute attr) {
		return name.equals(attr.name) && type.equals(attr.type);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute)
			{
				Attribute that = (Attribute) obj;

				boolean x = name.equals(that.name);
				x = x && type.equals(that.type);
				x = x && finalp == that.finalp && staticp == that.staticp;
				x = x && access.equals(that.access);
				return x;
			}
		return false;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public Access getAccess() {
		return access;
	}

	public boolean isStatic() {
		return staticp;
	}

	public boolean isFinal() {
		return finalp;
	}
}
