package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class AddMethodAction extends ClassFigureAction {

	public static final String ID = "actions.addMethod";

	public AddMethodAction(ClassFigure c) {
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.addMethod(UMLApplicationModel.prompt(method_prompt));
	}

	// TODO:link
	public static class Edit extends AbstractUndoableEdit {
		private ClassFigure cfig;
		private String meth;

		public Edit(ClassFigure cfig, String meth) {
			this.cfig = cfig;
			this.meth = meth;
		}

		@Override
		public void redo() throws CannotRedoException {
			super.redo();
			cfig.addMethod(meth);
		}

		@Override
		public void undo() throws CannotUndoException {
			super.undo();
			cfig.removeMethod(meth);
		}

	}

}
