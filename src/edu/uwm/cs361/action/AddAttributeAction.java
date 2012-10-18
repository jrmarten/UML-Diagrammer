package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import edu.uwm.cs361.classdiagram.ClassFigure;

@SuppressWarnings("serial")
public class AddAttributeAction extends ClassFigureAction
{
	public static final String	ID	= "actions.addAttribute.title";

	public AddAttributeAction(ClassFigure c)
	{
		super(ID, c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String attr = JOptionPane.showInputDialog(("Attribute: "));
		data.addAttribute(attr);
	}
}
