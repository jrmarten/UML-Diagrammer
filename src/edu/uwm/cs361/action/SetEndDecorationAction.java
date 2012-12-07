package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.LineDecorationChooser;
import edu.uwm.cs361.classdiagram.figure.ConnectionFigure;

/**
 * This class provides an action for the GUI
 * to set the end decoration of a connection figure.
 */
@SuppressWarnings("serial")
public class SetEndDecorationAction extends ConnectionFigureAction {
	public static final String ID = "actions.setEndDecoration";

	public SetEndDecorationAction(ConnectionFigure c) {
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LineDecorationChooser chooser = new LineDecorationChooser(_data, true);
		chooser.setVisible(true);
	}
}
