package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.figure.ClassFigure;

/**
 * This class provides an action for the GUI
 * to add an attribute to a class figure
 */
@SuppressWarnings("serial")
public class AddAttributeAction extends ClassFigureAction {
	public static final String ID = "actions.addAttribute";

	public AddAttributeAction(ClassFigure c) {
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String attr_rep = UMLApplicationModel.prompt(attribute_prompt);
		data.addAttribute(attr_rep);
	}

	/**
	 * Makes the action undoable.
	 */
	public static class Edit extends AbstractUndoableEdit {
		private ClassFigure fig;
		private String attr_rep;

		public Edit(ClassFigure fig, String attr_rep) {
			this.fig = fig;
			this.attr_rep = attr_rep;
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();
			fig.addAttribute(attr_rep);
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();
			fig.removeAttribute(attr_rep);
		}
	}
}
