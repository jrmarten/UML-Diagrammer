package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import edu.uwm.cs361.classdiagram.ClassFigure;

public class RemoveAttribute extends ClassFigureAction
{
	public static final String	ID	= "action.removeAttribute.title";

	public RemoveAttribute(ClassFigure fig)
	{
		super(ID, fig);
	}

	public void actionPerformed(ActionEvent e) {
		String meth = JOptionPane.showInputDialog("Method: ");

	}

}
