package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.figure.ClassFigure;

/**
 * This class provides an action for the GUI
 * to remove an attribute from a class figure
 */
@SuppressWarnings("serial")
public class RemoveAttributeAction extends ClassFigureAction {
	public static final String ID = "actions.removeAttribute";

	public RemoveAttributeAction(ClassFigure fig) {
		super(ID, fig);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.removeAttribute(UMLApplicationModel.prompt(attribute_prompt));
	}

}
