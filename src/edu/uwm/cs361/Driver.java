package edu.uwm.cs361;

import org.jhotdraw.app.*;



class Driver
{
    public static void main ( String[] args )
    {
	Application app = new SDIApplication();
	DefaultApplicationModel model = new DefaultApplicationModel();

	model.setName("UMLDiagrammer");
	model.setVersion("1.0");
	model.setCopyright("CopyLeft?");
	model.setViewClassName("edu.uwm.cs361.UMLView");

	app.setModel(model);
	app.launch(args);
    }
}