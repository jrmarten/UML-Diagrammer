package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.decoration.ArrowTip;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.AssociationFigure;

@SuppressWarnings("serial")
public class SetStartDecorationAction extends AssociationFigureAction
{
	public static final String	ID	= "actions.setStartDecoration";

	public SetStartDecorationAction(AssociationFigure c)
	{
		super(ID, c);
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		data.willChange();
		data.setAttributeEnabled(AttributeKeys.START_DECORATION, true);
		data.set(AttributeKeys.START_DECORATION, new ArrowTip());
		data.setAttributeEnabled(AttributeKeys.START_DECORATION, false);
		data.changed();
	}
}
