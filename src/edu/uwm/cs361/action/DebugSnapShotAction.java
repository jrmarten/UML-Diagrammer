package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import static edu.uwm.cs361.Util.*;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.ClassFigure;

public class DebugSnapShotAction extends AbstractAction
{

	public static final String	ID	= "edit.DebugSnapShot";
	DrawingView									data;

	public DebugSnapShotAction(DrawingView view)
	{
		data = view;
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		List<Figure> d = data.getDrawing().getFiguresFrontToBack();
		for (Figure fig : d)
			{
				if (fig instanceof ClassFigure)
					dprint(((ClassFigure) fig).snapShot());
			}
	}

}
