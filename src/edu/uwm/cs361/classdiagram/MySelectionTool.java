package edu.uwm.cs361.classdiagram;

import java.util.Set;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.SelectionTool;

public class MySelectionTool extends SelectionTool
{

	public static interface ClassFigureEditor
	{

		public void edit ( ClassFigure fig );

		public void edit ( ClassFigure fig, String string );
	}

	ClassFigureEditor	p_func;

	public MySelectionTool ( ClassFigureEditor editfunc )
	{
		p_func = editfunc;
	}

	@Override
	public void activate ( DrawingEditor edit )
	{
		super.activate ( edit );
		System.out.println ( "Enabled" );
	}

	@Override
	public void deactivate ( DrawingEditor edit )
	{
		super.deactivate ( edit );
		Set<Figure> figs = edit.getActiveView ( ).getSelectedFigures ( );

		if ( figs.size ( ) != 1 ) return;

		Figure fig = figs.iterator ( ).next ( );

		if ( fig instanceof ClassFigure )
			{
				new PromptFrame ( "Add Attribute", "Attribute:", (ClassFigure)fig,
						p_func );
			}
	}
}
