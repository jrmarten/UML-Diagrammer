package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class AddAttributeAction extends ClassFigureAction implements UndoableEdit
{
	public static final String	ID	= "actions.addAttribute";

	public AddAttributeAction(ClassFigure c)
	{
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.addAttribute(UMLApplicationModel.prompt(attribute_prompt));
	}

	@Override
	public boolean addEdit(UndoableEdit anEdit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPresentationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRedoPresentationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUndoPresentationName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSignificant() {
		return true;
	}

	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean replaceEdit(UndoableEdit anEdit) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void undo() throws CannotUndoException {
		Util.dprint( "Trying to undo" );
	}
}
