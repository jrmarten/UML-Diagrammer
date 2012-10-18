package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class AddMethodAction extends ClassFigureAction
{

	public static final String	ID	= "actions.addMethod.title";

	public AddMethodAction(ClassFigure c)
	{
		super(ID, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String meth = JOptionPane.showInputDialog("Method: ");
		data.addMethod(meth);
	}
}
