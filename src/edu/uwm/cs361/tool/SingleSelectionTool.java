package edu.uwm.cs361.tool;

import static edu.uwm.cs361.Util.dprint;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.SelectionTool;

import edu.uwm.cs361.action.ClassFigureAction;
import edu.uwm.cs361.classdiagram.figure.ClassFigure;

public class SingleSelectionTool extends SelectionTool
{
	private static final long	serialVersionUID	= -822275011885177964L;
	Action	p_func;

	public SingleSelectionTool(AbstractAction aa)
	{
		p_func = aa;
	}

	@Override
	public void deactivate(DrawingEditor edit) {
		super.deactivate(edit);
		Set<Figure> selected = edit.getActiveView().getSelectedFigures();

		if (selected.size() != 1)
			{
				dprint((selected.size() > 1) ? "Too many Figures selected"
						: "No Figures Selected");
				return;
			}

		Figure fig = selected.iterator().next();

		if (fig instanceof ClassFigure && p_func instanceof ClassFigureAction)
			{
				((ClassFigureAction) p_func).setData((ClassFigure) fig);
			}

		p_func.actionPerformed(new ActionEvent(this, 0, "Selected"));
	}
}
