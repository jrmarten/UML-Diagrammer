package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class RemoveMethodAction extends ClassFigureAction
{

	public static final String	ID	= "actions.removeMethod.title";

	public RemoveMethodAction(ClassFigure c)
	{
		super(ID, c);
	}

	public void actionPerformed(ActionEvent e) {
		data.removeMethod(UMLApplicationModel.prompt(method_prompt));
	}
}