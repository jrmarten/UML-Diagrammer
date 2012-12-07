package edu.uwm.cs361.classdiagram.data;

public class Argument {
	private String type, name;

	private Argument() {
	}

	public Argument(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	// @Override
	// public boolean equals ( Object o )
	// {
	// if ( !( o instanceof Argument) ) return false;
	//
	// Argument that = (Argument) o;
	//
	// return that.name.equals(this.name) && that.type.equals(this.type);
	// }

	@Override
	public String toString() {
		return name + " : " + type;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Argument))
			return false;

		Argument that = (Argument) o;

		return type.equals(that.type);
	}

	@SuppressWarnings("all")
	public static Argument Create(String str) {
		Argument arg = new Argument();
		String[] parts;
		boolean uml = false;
		str = str.trim();
		if ((uml = str.contains(":")))
			parts = str.split(":");
		else
			parts = str.split(" ");

		try {
			if (uml) {
				arg.name = parts[0].trim();
				arg.type = parts[1].trim();
			} else {
				arg.name = parts[1].trim();
				arg.type = parts[0].trim();
			}

			if (arg.name.contains(" "))
				return null;
			if (arg.type.contains(" "))
				return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return arg;
	}
}
