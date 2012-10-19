package edu.uwm.cs361.tool;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.SelectionTool;

public class ClickTool extends SelectionTool
{
	AbstractAction	p_func;

	public ClickTool(AbstractAction a)
	{
		p_func = a;
	}

	@Override
	public void activate(DrawingEditor edit) {
		ActionEvent ae = new ActionEvent(this, 0, "Clicked");
		p_func.actionPerformed(ae);
		deactivate(edit);
	}
}
