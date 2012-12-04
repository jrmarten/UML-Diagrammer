package edu.uwm.cs361.settings;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import edu.uwm.cs361.Util;

public class Style {
	private LinkedList<CSSRule> rules = new LinkedList<CSSRule>();

	public CSSRule get(String selector) {
		for (CSSRule tmp : rules) {
			if (selector.equals(tmp.getName()))
				return tmp;
		}
		return null;
	}

	public Color getColor(String selector, Color defaultColor) {
		if (Util.countInstancesOf(selector, '.') != 1)
			throw new IllegalArgumentException(
					"selector.property is malformed: " + selector);

		String[] parts = new String[2];
		parts[0] = selector.substring(0, selector.indexOf('.'));
		parts[1] = selector.substring(selector.indexOf('.') + 1);

		CSSRule tmp_rule = get(parts[0]);
		return (tmp_rule != null) ? tmp_rule.getColor(parts[1], defaultColor)
				: null;
	}

	public static Style extractStyle(File file) {
		if (!file.exists())
			return null;

		Style ret = new Style();
		LinkedList<CSSRule> rules = ret.rules;
		try {
			Scanner in = new Scanner(file);

			String buffer = "";
			String input = "";

			while (in.hasNext()) {
				input = handleComments(in.nextLine(), in).trim();

				if (Util.countInstancesOf(input, '}') == 1) {
					buffer += input.substring(0, input.indexOf('}') + 1);
					CSSRule[] rule = make(buffer);
					for (CSSRule next : rule) {
						if (!rules.contains(next))
							rules.add(next);
						else {

							CSSRule existing = rules.get(rules.indexOf(next));
							for (String tmpKey : next.props.keySet()) {
								existing.set(tmpKey, next.props.get(tmpKey));
							}
						}
					}
					buffer = input.substring(input.indexOf('}') + 1);
				} else
					buffer += input;
			}

		} catch (FileNotFoundException e) {
			Util.dprint("File not found: StyleSheet");
		}
		return ret;
	}

	private static String handleComments(String input, Scanner in) {

		if (input.contains("//")) {
			return input.substring(0, input.indexOf("//"));
		}
		if (input.contains("/*")) {
			String xinput = input.substring(input.indexOf("/*") + 2);
			input = input.substring(0, input.indexOf("/*"));

			while (!xinput.contains("*/")) {
				if (!in.hasNext())
					break;
				xinput = in.nextLine();
			}
			input = xinput.substring(xinput.indexOf("*/") + 2);
		}

		return input;
	}

	private static CSSRule[] make(String in) {
		if (Util.countInstancesOf(in, '{') != 1)
			return (CSSRule[]) Util.report("Error Style definintion: " + in);
		if (Util.countInstancesOf(in, '}') != 1)
			return (CSSRule[]) Util.report("Error Style definintion: " + in);

		String name = in.substring(0, in.indexOf('{'));
		String def = in.substring(in.indexOf('{') + 1, in.indexOf('}'));
		HashMap<String, String> map = new HashMap<String, String>();

		name = name.trim();

		String[] ids = name.split(" ");
		CSSRule[] ret = new CSSRule[ids.length];

		for (int i = 0; i < ids.length; i++) {
			ret[i] = new CSSRule(ids[i]);
		}

		String[] entries = def.split(";");
		for (String entry : entries) {
			String[] parts = entry.split(":");
			if (parts.length != 2)
				continue;

			String key = parts[0].trim();
			String val = parts[1].trim();

			map.put(key, val);
		}

		for (CSSRule tmp : ret) {
			for (String key : map.keySet()) {
				tmp.set(key, map.get(key));
			}
		}

		return ret;
	}
}
