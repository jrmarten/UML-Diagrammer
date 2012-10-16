package edu.uwm.cs361.classdiagram;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import edu.uwm.cs361.classdiagram.MySelectionTool.ClassFigureEditor;

public class PromptFrame extends JFrame
{

	JLabel						prompt;
	JTextField				ans;
	ClassFigure				cf;
	ClassFigureEditor	edit;

	public PromptFrame ( String prompt, ClassFigure fig, ClassFigureEditor edit )
	{
		this ( prompt, prompt, fig, edit );
	}

	public PromptFrame ( String title, String prompt, ClassFigure fig,
			ClassFigureEditor edit )
	{
		super ( title );
		this.prompt = new JLabel ( prompt );
		ans = new JTextField ( );
		setAlwaysOnTop ( true );
		cf = fig;
		ans.addActionListener ( new EnterListener ( ) );
		this.edit = edit;

		config ( );

		setVisible ( true );
	}

	public void config ( )
	{
		add ( prompt );
		add ( ans );
	}

	public void done ( )
	{
		edit.edit ( cf, ans.getText ( ) );
		dispose ( );
	}

	private class EnterListener implements ActionListener
	{

		@Override
		public void actionPerformed ( ActionEvent arg0 )
		{
			done ( );
		}

	}
}
