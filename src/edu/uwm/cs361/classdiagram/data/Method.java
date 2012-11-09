package edu.uwm.cs361.classdiagram.data;

import static edu.uwm.cs361.Util.join;
import static edu.uwm.cs361.Util.report;

import java.util.Iterator;
import java.util.LinkedList;

public class Method
{

	private String								name;
	private String								type;
	private LinkedList<Argument>		params		= new LinkedList<Argument>();
	private Access								access		= Access.DEFAULT;
	private boolean								abstractp	= false;
	private boolean								staticp		= false;

	private static final String[]	mods			= { "private", "public", "protected",
			"default", "abstract", "static", "#", "+", "-", "~", "a", "s" };

	private Method()
	{
		type = "";
		name = "";
	}

	private Method(String[] mods, String type, String name, Argument[] arglist)
	{
		this.name = name;
		this.type = type;

		for (Argument arg : arglist)
			{
				if (arg != null)
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
		if ( str.trim().equals( "" ) )
			return null;
		
		if ( !(str.contains("(") && str.contains(")") ) )
			return null;
		
		String tmp = str.substring( str.indexOf( ')' ) );
		if (tmp.contains(":"))
			return fromUML(str);
		else
			return fromSignature(str);
	}

	private static Method fromUML(String str) {
		int index = str.lastIndexOf( ':' );
		String type = str.substring( index + 1).trim();
		str = str.substring(0, index);
		String[] parts = str.split(":");
/*		if (parts.length != 2)
			return (Method) report("Too many colons in Method delcaration");
		String type = parts[1].trim();*/
		if (type.contains(" "))
			return (Method) report("Type has space in Method delcaration");

		String tmp = str;
		parts = parseModParam(tmp);

		String[] mods = parts[0].split(" ");
		String[] params = parts[1].split(", ");
		
		Argument[] args = new Argument [ params.length ];
		
		int i = 0;
		for ( String param_str : params )
			{
				args[i++] = Argument.Create( param_str );
			}

		index = mods.length - 1;
		String name = mods[index];
		mods[index] = "";

		Access a = Access.fromSymbol(name.charAt(0));
		if (a != null)
			{
				mods[index] = a.toString();
				name = name.substring(1);
			}

		return filter(mods, type, name, args);
	}
	
	
	public static Argument[] extractParams ( String str )
	{
		String[] args = str.split( "," );
		
		Argument[] ret = new Argument [ args.length ];
		
		int i = 0;
		for ( String arg : args )
			{
				ret[i++] = Argument.Create( arg );
			}
		return ret;
	}
	
	private static Method fromSignature(String str) {
		String[] parts = parseModParam(str);

		String[] mods = parts[0].split(" ");
		String[] params_str = parts[1].split(", ");
		Argument[] args = new Argument [ params_str.length ];
		
		int i = 0;
		for ( String param_str : params_str )
			{
				args[i++] = Argument.Create( param_str );
			}
		
		int index = mods.length - 1;

		String name = mods[index];
		mods [ index ] = "";
		String type;
		if ( mods.length > 1 ) 
			{
				type = mods[index - 1];
				mods[index - 1] = "";
			}
		else type = "void";
		
		//could check the type if access is given but type is not.

		Access a = Access.fromSymbol(name.charAt(0));
		if (a != null)
			{
				mods[index] = a.toString();
				name = name.substring(1);
			}

		return filter(mods, type, name, args);
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
			Argument[] params) {

		for (String mod : mods)
			{
				if (!isValid(mod))
					return (Method) report("Invalid Modifier in Method Declaration: "
							+ mod);
			}
		
		boolean abs = false;
		boolean stat = false;
		
		for ( String tmp : mods )
			{
				if ( tmp.equals("") ) continue;
				switch ( tmp.charAt(0) )
				{
					case 's':
					case 'S':
						stat = true;
						break;
						
					case 'a':
					case 'A':
						abs = true;
						break;
				}
			}
		
		if ( abs && stat )
			return (Method) report ( "Method can not be static and abstract" );
		
		for ( Argument param : params )
			{
				if ( param == null ) continue;
				if ( Keywords.keywordp( param.getType ( ) ) ||
							Keywords.reservedp( param.getName() ))
					return (Method) report ( "Invalid parameter type: " + param.getType() + " " + param.getName() );
			}
		
		if ( Keywords.keywordp ( type ) )
			return (Method) report ( "Invalid return type: " + type );

		if ( Keywords.reservedp( name ) )
			return (Method) report ( "Invalid name: " + name );
		
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

		Iterator<Argument> pit = p.params.iterator();
		Iterator<Argument> qit = q.params.iterator();

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

	public LinkedList<Argument> getParameters() {
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
		result.params = (LinkedList<Argument>) params.clone();
		return result;
	}

	private String getModSig() {
		return "" + ((abstractp) ? "abstract " : "") + ((staticp) ? "static " : "");
	}

	public String getSignature() {
		String buf = "(";
		Iterator<Argument> it = params.iterator();
		
		while ( it.hasNext() )
			{
				Argument tmp = it.next();
				buf += tmp.getType() + " " + tmp.getName();
				if ( !it.hasNext() ) break;
				buf += ",";
			}
		buf += ")";
		
		
		return getAccess().toString() + " " + getModSig() + " " + getType() + " "
				+ getName() + buf;
	}
}
