package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ConnectionFigure;
import edu.uwm.cs361.classdiagram.LineDecorationChooser;

@SuppressWarnings("serial")
public class SetStartDecorationAction extends AssociationFigureAction
{
	public static final String	ID	= "actions.setStartDecoration";

	public SetStartDecorationAction(ConnectionFigure c)
	{
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LineDecorationChooser chooser = new LineDecorationChooser(_data, false);
		chooser.setVisible(true);
	}
}
