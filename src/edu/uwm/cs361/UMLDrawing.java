package edu.uwm.cs361;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.Figure;

public class UMLDrawing extends DefaultDrawing
{
	public UMLDrawing ( )
	{
	}
	
	@Override
	public boolean add ( Figure fig )
	{
		Util.dprint( "Adding Figure" );
		fireUndoableEditHappened( new FigureAddedEdit ( this, fig ) );
		return super.add( fig );
	}
	
	public class FigureAddedEdit implements UndoableEdit
	{
		Drawing container;
		Figure fig;
		
		public FigureAddedEdit ( Drawing d, Figure f )
		{
			container = d;
			fig = f;
		}

		@Override
		public boolean addEdit(UndoableEdit arg0) {
			return false;
		}

		@Override
		public boolean canRedo() {
			return false;
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		//XXX: may cause null pointer
		@Override
		public void die() {
			container = null;
			fig = null;
		}

		@Override
		public String getPresentationName() {
			return "";
		}

		@Override
		public String getRedoPresentationName() {
			return "";
		}

		@Override
		public String getUndoPresentationName() {
			return "";
		}

		@Override
		public boolean isSignificant() {
			return true;
		}

		@Override
		public void redo() throws CannotRedoException {
			throw new CannotRedoException ( );
		}

		@Override
		public boolean replaceEdit(UndoableEdit arg0) {
			return false;
		}

		@Override
		public void undo() throws CannotUndoException {
			container.remove( fig );
		}
		
	}
}
