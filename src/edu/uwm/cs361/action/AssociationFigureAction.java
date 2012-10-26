package edu.uwm.cs361.action;

import javax.swing.AbstractAction;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.AssociationFigure;

@SuppressWarnings("serial")
public abstract class AssociationFigureAction extends AbstractAction
{
	protected AssociationFigure data;

//	public static final String	method_prompt			= "actions.method.prompt";
//	public static final String	attribute_prompt	= "actions.attribute.prompt";

	public AssociationFigureAction(String str, AssociationFigure fig)
	{
		super(UMLApplicationModel.getProjectResources().getString(str));
		data = fig;
	}

	public void setData(AssociationFigure fig) {
		data = fig;
	}

}
