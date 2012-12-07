package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.undo.AbstractUndoableEdit;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.figure.ConnectionFigure;

public class EditMultiplicityAction extends ConnectionFigureAction {
	public static final String ID = "actions.editMult";

	public EditMultiplicityAction(ConnectionFigure fig) {
		super(ID, fig);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = UMLApplicationModel.prompt("actions.editMult.prompt",
				"Edit Multiplicity");

		if (name == null) {
			Util.dprint("User Canceled");
			return;
		}

		int index = name.indexOf(':');
		String a_mult, b_mult;

		if (index == -1) {
			a_mult = name;
			b_mult = "";
		} else {
			a_mult = name.substring(0, index);
			b_mult = name.substring(index + 1);
		}

		_data.setMult(a_mult, b_mult);
	}

	public static class Edit extends AbstractUndoableEdit {
		private String a, b, old_a, old_b;
		private ConnectionFigure cfig;

		public Edit(ConnectionFigure cfig, String a, String b, String old_a,
				String old_b) {
			this.cfig = cfig;
			this.a = a;
			this.b = b;
			this.old_a = old_a;
			this.old_b = old_b;
		}

		@Override
		public void undo() {
			super.undo();
			cfig.setMult(old_a, old_b);
		}

		@Override
		public void redo() {
			super.redo();
			cfig.setMult(a, b);
		}
	}
}
