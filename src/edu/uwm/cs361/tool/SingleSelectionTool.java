package edu.uwm.cs361.tool;

import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.SelectionTool;

import edu.uwm.cs361.action.ClassFigureAction;
import edu.uwm.cs361.classdiagram.ClassFigure;

public class SingleSelectionTool extends SelectionTool
{
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
			return;

		Figure fig = selected.iterator().next();

		if (fig instanceof ClassFigure && p_func instanceof ClassFigureAction)
			{
				((ClassFigureAction) fig).setData((ClassFigure) fig);
			}
	}
}
