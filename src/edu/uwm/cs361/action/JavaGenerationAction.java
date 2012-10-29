package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.gui.JFileURIChooser;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.ClassFigure;
import edu.uwm.cs361.classdiagram.io.JavaGenerator;

@SuppressWarnings("serial")
public class JavaGenerationAction extends AbstractAction
{
	DrawingView	data;

	public JavaGenerationAction(DrawingView view)
	{
		data = view;
	}

	public void actionPerformed(ActionEvent e) { 
		List<Figure> figs = data.getDrawing().getFiguresFrontToBack();

		JFileURIChooser c = new JFileURIChooser();
		c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int result = c.showDialog(null, "Generate");

		if (result != JFileChooser.APPROVE_OPTION)
			{
				Util.dprint("JFileChooser failed to return properly");
				return;
			}

		File file = c.getSelectedFile();

		if (!file.isDirectory())
			{
				JOptionPane.showMessageDialog(null, "Selection was not a directory",
						"Choose error", JOptionPane.ERROR_MESSAGE);
				return;
			}

		Util.dprint(file.getAbsolutePath());

		for (Figure fig : figs)
			{
				if (fig instanceof ClassFigure)
					{
						ClassFigure cfig = (ClassFigure) fig;

						Util.dprint(cfig.getData().getDeclaration());

						JavaGenerator.write(file.getAbsolutePath(), cfig.getData());
					}
			}

	}
}
