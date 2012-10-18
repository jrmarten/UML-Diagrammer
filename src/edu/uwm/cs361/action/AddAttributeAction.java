package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class AddAttributeAction extends ClassFigureAction
{
	public static final String	ID	= "actions.addAttribute.title";

	public AddAttributeAction(ClassFigure c)
	{
		super(ID, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.addAttribute(UMLApplicationModel.prompt(attribute_prompt));
	}
}
