package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.figure.ClassFigure;

/**
 * This class provides an action for the GUI
 * to add method to a class figure.
 */
@SuppressWarnings("serial")
public class AddMethodAction extends ClassFigureAction {

	public static final String ID = "actions.addMethod";

	public AddMethodAction(ClassFigure c) {
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.addMethod(UMLApplicationModel.prompt(method_prompt));
	}

}
