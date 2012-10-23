package edu.uwm.cs361.tool;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;

public class ClickTool extends AbstractTool
{
	AbstractAction	p_func;

	public ClickTool(AbstractAction a)
	{
		p_func = a;
	}

	@Override
	public void activate(DrawingEditor edit) {
		super.activate(edit);

		fireToolDone();
	}

	public void deactivate(DrawingEditor edit) {
		super.deactivate(edit);

		ActionEvent ae = new ActionEvent(this, 0, "Clicked");
		p_func.actionPerformed(ae);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}
}
