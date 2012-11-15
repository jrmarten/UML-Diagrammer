package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class AddAttributeAction extends ClassFigureAction
{
	public static final String	ID	= "actions.addAttribute";
	
	public AddAttributeAction(ClassFigure c)
	{
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String attr_rep = UMLApplicationModel.prompt(attribute_prompt);
		data.addAttribute(attr_rep);
	}

	
	
	public class AddAttributeEdit implements UndoableEdit
	{
		private ClassFigure fig;
		private String attr_rep;
		private boolean alive = true;
		
		public AddAttributeEdit ( ClassFigure fig, String attr_rep )
		{
			this.fig = fig;
			this.attr_rep = attr_rep;
		}
		
		@Override
		public boolean addEdit(UndoableEdit arg0) {
			return false;
		}
		
		@Override
		public String toString ( )
		{
			return fig.getData().getName() + " + " + attr_rep;
		}

		@Override
		public boolean canRedo() { return alive; }

		@Override
		public boolean canUndo() { return alive; }

		@Override
		public void die() {
			fig = null;
			attr_rep = null;
			alive = false;
		}

		@Override
		public String getPresentationName() { return "Add Attribute undo"; }

		@Override
		public String getRedoPresentationName() { return ""; }

		@Override
		public String getUndoPresentationName() { return ""; }

		@Override
		public boolean isSignificant() { return true; }

		@Override
		public void redo() throws CannotRedoException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean replaceEdit(UndoableEdit arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void undo() throws CannotUndoException {
			Util.dprint( "undoing" );
			fig.removeAttribute( attr_rep );
		}
		
	}
}
