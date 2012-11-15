package edu.uwm.cs361;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.undo.UndoableEdit;

import org.jhotdraw.app.View;
import org.jhotdraw.draw.DefaultDrawingEditor;

/*
 * Make hotkeys
 * not linked to program yet.
 */
public class UMLDrawingEditor extends DefaultDrawingEditor
{
	private static final long	serialVersionUID	= -5551884955740023869L;

	@Override
	protected InputMap createInputMap() {
		InputMap iMaps = super.createInputMap(); // lolz
		
		//iMaps.put ( KeyStroke.getKeyStroke ( KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK ), 
		
		return iMaps;
	}
	
	protected ActionMap createActionMap ( ) {
		ActionMap m = super.createActionMap();
		
		
		
		
		//m.put( UndoAction.ID, new UndoAction ( ) );
		
		return m;
	}
}
