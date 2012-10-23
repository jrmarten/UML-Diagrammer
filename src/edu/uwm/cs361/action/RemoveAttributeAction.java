package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class RemoveAttributeAction extends ClassFigureAction
{
	public static final String	ID	= "actions.removeAttribute";

	public RemoveAttributeAction(ClassFigure fig)
	{
		super(ID, fig);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	public void actionPerformed(ActionEvent e) {
		data.removeMethod(UMLApplicationModel.prompt(attribute_prompt));
	}

}
