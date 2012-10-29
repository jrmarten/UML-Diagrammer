package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;
import java.net.URI;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.file.SaveFileAction;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.Settings;

public class SaveTemplateAction extends SaveFileAction
{

	public SaveTemplateAction(Application app, @Nullable View view) {
		super(app, view, false);
	}
	
	
	public void actionPerformed ( ActionEvent e )
	{
		String filename = UMLApplicationModel.prompt ( "file.saveTemplate.prompt", "Save Template" );
		filename = Settings.getGlobal().getString("templateDir", Settings.getProgDir() + "Template/") + 
				filename + ".xml";
		
		Util.dprint( filename );
		
		URI uri = URI.create( "file://" + filename );
		
		saveViewToURI ( getActiveView(), uri, null );
	}
}
