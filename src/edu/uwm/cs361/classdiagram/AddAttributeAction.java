package edu.uwm.cs361.classdiagram;

import java.awt.event.ActionEvent;
import java.util.Set;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;

import edu.uwm.cs361.classdiagram.data.UMLClass;


public class AddAttributeAction extends AbstractSelectedAction
{
	
	public static final String ID = "Class.AddAttribute";
	private static final boolean DEBUG = true;
	
	public AddAttributeAction ( DrawingEditor edit )
	{
		super( edit );
		
	}
	
	public void updateEnabledState ( ) 
	{
		if ( getView ( ) != null )
			{
				Set<Figure> figs = getView().getSelectedFigures();
				boolean classselected = figs.size() == 1 && 
						figs.iterator ( ).next() instanceof UMLClass;
						
				setEnabled ( getView().isEnabled ( ) &&  classselected );
			}
		setEnabled ( false );
	}

	@Override
	public void actionPerformed ( ActionEvent arg0 )
	{
		if ( DEBUG) System.out.println ( "Add Action has been called");
		ClassFigure selected = (ClassFigure) getView().getSelectedFigures().iterator ( ).next ( );
		selected.addAttribute ( "_default:int" );
	}
}
