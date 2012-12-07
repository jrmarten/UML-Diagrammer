package edu.uwm.cs361.action;

import javax.swing.AbstractAction;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.figure.ConnectionFigure;

/**
 * This class provides an action to add a connection
 * figure between two classes
 */
@SuppressWarnings("serial")
public abstract class ConnectionFigureAction extends AbstractAction {
	protected ConnectionFigure _data;

	public ConnectionFigureAction(String str, ConnectionFigure fig) {
		super(UMLApplicationModel.getProjectResources().getString(str));
		_data = fig;
	}

	public void setData(ConnectionFigure fig) {
		_data = fig;
	}

}
