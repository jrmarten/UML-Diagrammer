package edu.uwm.cs361.action;

import static edu.uwm.cs361.Util.dprint;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.classdiagram.figure.ClassFigure;
import edu.uwm.cs361.classdiagram.figure.ConnectionFigure;

/**
 * This class provides an action for the user to print
 * a text representation of the drawing's data to the terminal
 */
public class DebugSnapShotAction extends AbstractAction {
	private static final long serialVersionUID = 3246670173154654804L;
	public static final String ID = "edit.DebugSnapShot";
	DrawingView data;

	public DebugSnapShotAction(DrawingView view) {
		data = view;
		UMLApplicationModel.getProjectResources().configureAction(this, ID);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		dprint("\n\n+---------------------+\n| Data Representation |\n+---------------------+\n");

		List<Figure> d = data.getDrawing().getFiguresFrontToBack();
		for (Figure fig : d) {
			if (fig instanceof ClassFigure) {
				classfigure_hook((ClassFigure) fig);
			} else if (fig instanceof ConnectionFigure) {
				conSnap((ConnectionFigure) fig);
			} else {
				figure_hook(fig);
			}
		}
	}

	private void classfigure_hook(ClassFigure cfig) {

		classSnap(cfig);
	}

	private void figure_hook(Figure fig) {

	}

	private void classSnap(ClassFigure cfig) {
		UMLClass data = cfig.getData();
		String buffer = data.getDeclaration() + " {\n";
		for (Attribute attr : data.getAttributes()) {
			buffer += "  " + attr.getSignature() + "\n";
		}
		for (Method meth : data.getMethods()) {
			buffer += "  " + meth.getSignature() + "\n";
		}
		buffer += "}\n";
		dprint(buffer);
	}

	private void conSnap(ConnectionFigure fig) {
		dprint("Connection Figure:");
		dprint("  Start (" + fig.getData().getStart().getName() + ") end type: "
				+ fig.getData().getConnectionType(fig.getData().getStart()));
		dprint("  End (" + fig.getData().getEnd().getName() + ") end type: "
				+ fig.getData().getConnectionType(fig.getData().getEnd()));
		dprint("\n");
	}
}
