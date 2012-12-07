package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.undo.AbstractUndoableEdit;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.figure.ConnectionFigure;

public class EditRoleAction extends AssociationFigureAction {
	public static final String ID = "actions.editRole";

	public EditRoleAction(ConnectionFigure c) {
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = UMLApplicationModel.prompt("actions.editRole.prompt",
				"Edit Role name");

		if (name == null) {
			Util.dprint("User canceled");
			return;
		}

		int index = name.indexOf(':');
		String role_a, role_b;

		if (index == -1) {
			role_a = name;
			role_b = "";
		} else {
			role_a = name.substring(0, index);
			role_b = name.substring(index + 1);
		}

		_data.setRoles(role_a, role_b);
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
			cfig.setRoles(old_a, old_b);
		}

		@Override
		public void redo() {
			super.redo();
			cfig.setRoles(a, b);
		}
	}
}
