package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.decoration.ArrowTip;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.AssociationFigure;

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
		data.setAttributeEnabled(AttributeKeys.END_DECORATION, true);
		data.set(AttributeKeys.END_DECORATION, new ArrowTip());
		data.setAttributeEnabled(AttributeKeys.END_DECORATION, false);
		data.changed();
	}
}
