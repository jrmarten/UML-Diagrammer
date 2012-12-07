package edu.uwm.cs361.classdiagram.data;

import edu.uwm.cs361.classdiagram.data.connection.Multiplicity;

public class Connection {
	private static final String ILLEGAL_ACCESS = "Object should not be able to access Connections internals";

	private ConnectionEnd a, b;

	public Connection(UMLClass class_a, UMLClass class_b) {
		a = new ConnectionEnd();
		a.class_type = class_a;

		b = new ConnectionEnd();
		b.class_type = class_b;
	}

	public UMLClass getStart() {
		return a.class_type;
	}

	public UMLClass getEnd() {
		return b.class_type;
	}

	public void register() {
		if (!a.class_type._cons.contains(this))
			a.class_type._cons.add(this);

		if (!b.class_type._cons.contains(this))
			b.class_type._cons.add(this);
	}

	// Shallow copy is needed.
	@Override
	public Object clone() {
		Connection that = new Connection(a.class_type, b.class_type);
		that.a.con_type = a.con_type;
		that.b.con_type = b.con_type;
		that.a.role_name = a.role_name;
		that.b.role_name = b.role_name;
		return that;
	}

	public ConnectionType getConnectionType(UMLClass ref) {
		return getThisEnd(ref).con_type;
	}

	public void setConnectionType(UMLClass ref, ConnectionType type) {
		getThisEnd(ref).con_type = type;
	}

	public String getRole(UMLClass ref) {
		return getThisEnd(ref).role_name;
	}

	public void setRole(UMLClass ref, String new_role) {
		getThisEnd(ref).role_name = new_role;
	}

	public void setMultiplicity(UMLClass ref, String new_mult) {
		if (new_mult.equals(""))
			getThisEnd(ref).mult = null;
		else
			getThisEnd(ref).mult = Multiplicity.Create(new_mult);
	}

	public String getMultiplicity(UMLClass ref) {
		Multiplicity mult = getThisEnd(ref).mult;
		if (mult == null)
			return "";
		return mult.toString();
	}

	public UMLClass getOther(UMLClass umlc) {
		return getOtherEnd(umlc).class_type;
	}

	private ConnectionEnd getOtherEnd(UMLClass umlc) {
		if (umlc == null)
			throw new IllegalArgumentException(ILLEGAL_ACCESS);
		if (a.class_type == umlc)
			return b;
		if (b.class_type == umlc)
			return a;

		throw new IllegalArgumentException(ILLEGAL_ACCESS);
	}

	private ConnectionEnd getThisEnd(UMLClass umlc) {
		if (umlc == null)
			return null;

		if (a.class_type == umlc)
			return a;
		if (b.class_type == umlc)
			return b;

		return null;
	}

	private static class ConnectionEnd {
		public String role_name = "";
		public UMLClass class_type;
		public ConnectionType con_type = ConnectionType.ASSOCIATION;
		public Multiplicity mult = null;
	}
}
