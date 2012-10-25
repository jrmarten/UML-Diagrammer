package edu.uwm.cs361.classdiagram.io;

import java.io.*;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.*;

public class JavaGenerator
{
	private String						tab				= "\t";
	private int								tab_count	= 0;
	private PrintWriter				fout;

	private static String[][]	defaults	= { { "byte", "0" }, { "int", "0" },
			{ "double", "0.0" }, { "float", "0.0f" }, { "short", "0" },
			{ "long", "0L" }, { "char", "0" }, { "boolean", "false" }, { "void", "" } };

	public JavaGenerator(File file) throws FileNotFoundException
	{
		fout = new PrintWriter(file);
	}

	private String get_indent() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tab_count; i++)
			{
				sb.append(tab);
			}
		return sb.toString();
	}

	public void close() {
		if (fout == null)
			return;
		fout.close();
		fout = null;
	}

	private void write(String buffer) {
		if (fout == null)
			return;
		String[] lines = buffer.split("\n");

		for (String line : lines)
			{
				tab_count += -count(line, '}');

				fout.println(get_indent() + line.trim());
				tab_count += (count(line, '{'));
			}
	}

	public static void write(String directiory, UMLClass umlclass) {

		File java_src;
		JavaGenerator genny;

		try
			{
				java_src = new File(directiory + System.getProperty("file.separator")
						+ umlclass.getName() + ".java");
				genny = new JavaGenerator(java_src);
			} catch (Exception e)
			{
				UMLApplicationModel.error("file.JavaGeneration.error", "IO Error");
				return;
			}

		Util.dprint(java_src.getAbsolutePath());

		String start = "\n{\n", end = "\n}\n\n";

		genny.write(umlclass.getDeclaration() + start);

		for (Attribute attr : umlclass.getAttributes())
			{
				/*
				 * String sig = ""; sig += attr.getAccess().toString() + " "; sig +=
				 * (attr.isStatic()) ? "static " : ""; sig += (attr.isFinal()) ?
				 * "final " : ""; sig += attr.getType() + " "; sig += attr.getName() +
				 * ";\n\n";
				 */

				String sig = attr.getSignature();
				sig += ";\n\n";
				genny.write(sig);
			}

		for (Method meth : umlclass.getMethods())
			{
				/*
				 * String sig = ""; sig += meth.getAccess().toString() + " "; sig +=
				 * (meth.isStatic()) ? "static " : ""; sig += (meth.isAbstract()) ?
				 * "abstract " : ""; sig += meth.getType() + " "; sig += meth.getName()
				 * + " ( ";
				 * 
				 * Iterator<String> it1 = meth.getParameters().iterator(); int index =
				 * 0; for (; it1.hasNext();) { sig += it1.next() + " arg" + index++; if
				 * (!it1.hasNext()) break; sig += ", "; }
				 */
				String sig = meth.getSignature();
				sig += start;
				sig += " return " + genny.getDefault(meth.getType()) + ";" + end;
				genny.write(sig);
			}

		genny.write(end);

		genny.close();
	}

	public String getDefault(String type) {
		type = type.trim();

		for (String[] pair : defaults)
			{
				if (type.equals(pair[0]))
					return pair[1];
			}
		return "null";
	}

	private static int count(String line, char ch) {
		int sum = 0;

		for (int i = 0; i < line.length(); i++)
			if (line.charAt(i) == ch)
				sum++;

		return sum;
	}
}