package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.gui.JFileURIChooser;

import edu.uwm.cs361.classdiagram.ClassFigure;
import edu.uwm.cs361.classdiagram.io.*;

public class JavaGenerationAction extends AbstractAction
{
	DrawingView	data;

	JavaGenerationAction(DrawingView view)
	{
		data = view;
	}

	public void actionPerformed(ActionEvent e) {
		List<Figure> figs = data.getDrawing().getFiguresFrontToBack();

		JFileURIChooser c = new JFileURIChooser();

		FileFilter phil = new FileFilter()
			{
				public boolean accept(File f) {
					return f.isDirectory();
				}

				@Override
				public String getDescription() {
					return "Directory Filter";
				}
			};

		c.addChoosableFileFilter(phil);

		File file = c.getSelectedFile();

		if (!file.isDirectory())
			{
				JOptionPane.showMessageDialog(null, "Selection was not a directory",
						"Choose error", JOptionPane.ERROR_MESSAGE);

			}

		for (Figure fig : figs)
			{
				if (fig instanceof ClassFigure)
					{

						JavaGenerator.write(file.getAbsolutePath(),
								((ClassFigure) fig).getData());
					}
			}

	}
}
