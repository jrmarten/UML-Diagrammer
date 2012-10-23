package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.join;
import static edu.uwm.cs361.Util.report;

import java.util.Iterator;
import java.util.LinkedList;

public class Method
{

	private String								name;
	private String								type;
	private LinkedList<String>		params		= new LinkedList<String>();
	private Access								access		= Access.DEFAULT;
	private boolean								abstractp	= false;
	private boolean								staticp		= false;

	private static final String[]	mods			= { "private", "public", "protected",
			"default", "abstract", "static", "#", "+", "-", "~" };

	private Method()
	{
		type = "";
		name = "";
	}

	private Method(String[] mods, String type, String name, String[] arglist)
	{
		this.name = name;
		this.type = type;

		for (String arg : arglist)
			{
				arg = arg.trim();
				if (!arg.equals(""))
					params.add(arg);
			}

		for (String mod : mods)
			{
				mod = mod.trim();
				if (mod.equals(""))
					continue;

				char tmp = mod.charAt(0);

				switch (tmp)
					{
						case 's':
						case 'S':
							staticp = true;
						break;

						case 'a':
						case 'A':
							abstractp = true;
						break;

						default:
							if (access != Access.DEFAULT)
								continue;
							access = Access.fromString(mod);
					}
			}
	}

	public static Method Create(String str) {
		if (str == null)
			return null;
		if (str.contains(":"))
			return fromUML(str);
		else
			return fromSignature(str);
	}

	private static Method fromUML(String str) {

		String[] parts = str.split(":");
		if (parts.length != 2)
			return (Method) report("Too many colons in Method delcaration");
		String type = parts[1].trim();
		if (type.contains(" "))
			return (Method) report("Type has space in Method delcaration");

		String tmp = parts[0];
		parts = parseModParam(tmp);

		String[] mods = parts[0].split(" ");
		String[] params = parts[1].split(", ");

		int index = mods.length - 1;
		String name = mods[index];
		mods[index] = "";

		Access a = Access.fromSymbol(name.charAt(0));
		if (a != null)
			{
				mods[index] = a.toString();
				name = name.substring(1);
			}

		return filter(mods, type, name, params);
	}

	private static Method fromSignature(String str) {

		String[] parts = parseModParam(str);

		String[] mods = parts[0].split(" ");
		String[] params = parts[1].split(", ");

		int index = mods.length - 1;

		String name = mods[index];
		String type = mods[index - 1];

		mods[index] = "";
		mods[index - 1] = "";

		Access a = Access.fromSymbol(name.charAt(0));
		if (a != null)
			{
				mods[index] = a.toString();
				name = name.substring(1);
			}

		return filter(mods, type, name, params);
	}

	private static String[] parseModParam(String in) {
		String mods = in.substring(0, in.indexOf('('));
		String params = in.substring(in.indexOf('(') + 1, in.indexOf(')'));
		String[] result = new String[2];
		result[0] = mods;
		result[1] = params;
		return result;
	}

	private static Method filter(String[] mods, String type, String name,
			String[] params) {

		for (String mod : mods)
			{
				if (!isValid(mod))
					return (Method) report("Invalid Modifier in Method Declaration: "
							+ mod);
			}

		boolean let = false;
		boolean sym = false;
		boolean num = false;

		if (Character.isDigit(name.charAt(0)))
			return (Method) report("Invalid name in Method Declaration");

		for (char ch : name.toCharArray())
			{
				let = Character.isLetter(ch);
				sym = ch == '_' || ch == '$';
				num = Character.isDigit(ch);

				if (!(let || sym || num))
					{
						return (Method) report("Name is Ill formated in Method Declaration");
					}
			}

		return new Method(mods, type, name, params);
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

	public static boolean overloaded(Method p, Method q) {
		if (!p.name.equals(q.name))
			return false;
		if (!p.type.equals(q.type))
			return false;

		if (p.params.size() != q.params.size())
			return true;

		Iterator<String> pit = p.params.iterator();
		Iterator<String> qit = q.params.iterator();

		while (pit.hasNext())
			{
				if (!pit.next().equals(qit.next()))
					return true;
			}

		return false;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public LinkedList<String> getParameters() {
		return params;
	}

	public Access getAccess() {
		return access;
	}

	public boolean isAbstract() {
		return abstractp;
	}

	public boolean isStatic() {
		return staticp;
	}

	@Override
	public String toString() {
		return getAccess().getSymbol() + getName() + "(" + join(params, ",") + "):"
				+ getType();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Method)
			{
				Method other = (Method) o;

				return other.name.equals(name) && other.type.equals(type)
						&& other.params.equals(params);
			}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Method clone() {
		Method result = new Method();
		result.access = access;
		result.abstractp = abstractp;
		result.staticp = staticp;
		result.name = getName();
		result.type = getType();
		result.params = (LinkedList<String>) params.clone();
		return result;
	}

	private String getModSig() {
		return "" + ((abstractp) ? "abstract " : "") + ((staticp) ? "static " : "");
	}

	public String getSignature() {
		return getAccess().toString() + " " + getModSig() + " " + getType() + " "
				+ getName() + " ( " + join(params, ", ") + " ) ";
	}
}
