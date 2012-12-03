package edu.uwm.cs361;

import java.util.Collection;

import javax.swing.Action;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.action.ButtonFactory;

public class UMLButtonFactory // extends ButtonFactory
{

	public UMLButtonFactory() {
	}

	public static Collection<Action> createDrawingActions(DrawingEditor edit) {
		Collection<Action> col = ButtonFactory.createDrawingActions(edit);

		return col;
	}
}
