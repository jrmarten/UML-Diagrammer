package edu.uwm.cs361.classdiagram.data;

public enum Access {
	PRIVATE("private", '-'), PUBLIC("public", '+'), DEFAULT("default", '~'), PROTECTED(
			"protected", '#');

	private static final char[] symbols = { '-', '+', '~', '#' };

	private String name = "";
	private char sym = 0;

	private Access(String str, char symbol) {
		name = str;
		sym = symbol;
	}

	public char getSymbol() {
		return sym;
	}

	public static char[] symbols() {
		return symbols;
	}

	public static Access fromSymbol(char c) {
		switch (c) {
		case '+':
			return Access.PUBLIC;
		case '-':
			return Access.PRIVATE;
		case '#':
			return Access.PROTECTED;
		case '~':
			return Access.DEFAULT;
		default:
			return null;
		}
	}

	public static Access fromString(String perm) {
		perm = perm.trim();
		if (perm.length() == 1)
			return fromSymbol(perm.charAt(0));
		if (perm.equalsIgnoreCase("public"))
			return PUBLIC;
		if (perm.equalsIgnoreCase("protected"))
			return PROTECTED;
		if (perm.equalsIgnoreCase("private"))
			return PRIVATE;
		return DEFAULT;
	}

	@Override
	public String toString() {
		return name;
	}
}