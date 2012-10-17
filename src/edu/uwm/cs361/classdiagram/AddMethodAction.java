package edu.uwm.cs361.classdiagram;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.UMLClass;

import static edu.uwm.cs361.Util.*;

public class AddMethodAction extends AbstractAction
{

	public static final String		ID		= "actions.addMethod";
	private static final boolean	DEBUG	= true;

	private ClassFigure						data;

	public AddMethodAction ( ClassFigure c )
	{
		data = c;
	}

	@Override
	public void actionPerformed ( ActionEvent e )
	{
		data.addAttribute ( "_default()" );
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
