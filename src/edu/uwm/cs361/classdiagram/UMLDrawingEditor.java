package edu.uwm.cs361.classdiagram;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.KeyStroke;

import org.jhotdraw.draw.DefaultDrawingEditor;

import edu.uwm.cs361.action.AddAttributeAction;


public class UMLDrawingEditor extends DefaultDrawingEditor
{

	public InputMap createInputMap ( )
	{
		InputMap result = super.createInputMap ( );
		
		result.put ( KeyStroke.getKeyStroke ( KeyEvent.VK_A, InputEvent.ALT_DOWN_MASK ), AddAttributeAction.ID );
		
		
		return result;
	}
}
