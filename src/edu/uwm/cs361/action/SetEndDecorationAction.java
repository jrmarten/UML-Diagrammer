package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.decoration.ArrowTip;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.AssociationFigure;
import edu.uwm.cs361.classdiagram.LineDecorationChooser;

@SuppressWarnings("serial")
public class SetEndDecorationAction extends AssociationFigureAction
{
	public static final String	ID	= "actions.setEndDecoration";

	public SetEndDecorationAction(AssociationFigure c)
	{
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.willChange();
		LineDecorationChooser chooser = new LineDecorationChooser("end");
		chooser.setVisible(true);
		data.setAttributeEnabled(AttributeKeys.END_DECORATION, true);
		data.set(AttributeKeys.END_DECORATION, new ArrowTip());
		data.setAttributeEnabled(AttributeKeys.END_DECORATION, false);
		data.changed();
	}
}
