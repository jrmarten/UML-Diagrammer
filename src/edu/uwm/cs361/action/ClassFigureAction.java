package edu.uwm.cs361.action;

import javax.swing.AbstractAction;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public abstract class ClassFigureAction extends AbstractAction
{
	protected ClassFigure				data;

	public static final String	method_prompt			= "actions.method.prompt";
	public static final String	attribute_prompt	= "actions.attribute.prompt";

	public ClassFigureAction(String str, ClassFigure fig)
	{
		super(UMLApplicationModel.getProjectResources().getString(str));
		data = fig;
	}

}
