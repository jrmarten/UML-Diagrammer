package edu.uwm.cs361;

import org.jhotdraw.app.*;

public class UMLDriver {
	public static void main(String[] args) {
		Application app = new SDIApplication();
		DefaultApplicationModel model = new UMLApplicationModel();

		model.setName("UML Diagrammer");
		model.setVersion("1.0");
		model.setCopyright("CopyLeft?");
		model.setViewClassName("edu.uwm.cs361.UMLView");

		app.setModel(model);
		app.launch(args);
	}
}