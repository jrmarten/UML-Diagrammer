package edu.uwm.cs361.action;

import javax.swing.AbstractAction;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ConnectionFigure;

@SuppressWarnings("serial")
public abstract class AssociationFigureAction extends AbstractAction
{
	protected ConnectionFigure _data;

//	public static final String	method_prompt			= "actions.method.prompt";
//	public static final String	attribute_prompt	= "actions.attribute.prompt";

	public AssociationFigureAction(String str, ConnectionFigure fig)
	{
		super(UMLApplicationModel.getProjectResources().getString(str));
		_data = fig;
	}

	public void setData(ConnectionFigure fig) {
		_data = fig;
	}

}
