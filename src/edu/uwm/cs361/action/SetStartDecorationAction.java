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
		Util.dprint("Clicked SetStartDecoration");
		data.set(AttributeKeys.START_DECORATION, new ArrowTip(0.80, 20.0, 20.0));
		data.changed();
	}
}
