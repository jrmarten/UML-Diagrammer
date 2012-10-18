package edu.uwm.cs361.action;

import javax.swing.AbstractAction;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public abstract class ClassFigureAction extends AbstractAction
{
	protected ClassFigure	data;

	public ClassFigureAction(String str, ClassFigure fig)
	{
		super(UMLApplicationModel.getProjectResources().getString(str));
		data = fig;
	}

}
