package edu.uwm.cs361.classdiagram;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.UMLClass;

import static edu.uwm.cs361.Util.*;

public class AddAttributeAction extends AbstractAction
{

	public static final String		ID		= "actions.addAttribute";
	private static final boolean	DEBUG	= true;

	private ClassFigure						data;

	public AddAttributeAction ( ClassFigure c )
	{
		data = c;
	}

	@Override
	public void actionPerformed ( ActionEvent e )
	{
		data.addAttribute ( "_default:int" );
	}
	/*
	 * public AddAttributeAction ( DrawingEditor edit ) { super ( edit ); }
	 * 
	 * public void updateEnabledState ( ) { if ( getView ( ) != null ) {
	 * Set<Figure> figs = getView ( ).getSelectedFigures ( ); boolean
	 * classselected = figs.size ( ) == 1 && figs.iterator ( ).next ( ) instanceof
	 * UMLClass;
	 * 
	 * setEnabled ( getView ( ).isEnabled ( ) && classselected ); } setEnabled (
	 * false ); }
	 * 
	 * @Override public void actionPerformed ( ActionEvent arg0 ) { if ( DEBUG )
	 * System.out.println ( "Add Action has been called" ); ClassFigure selected =
	 * (ClassFigure)getView ( ).getSelectedFigures ( ) .iterator ( ).next ( );
	 * selected.addAttribute ( "_default:int" ); } //
	 */
}
