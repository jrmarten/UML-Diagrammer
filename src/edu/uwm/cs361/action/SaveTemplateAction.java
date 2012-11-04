package edu.uwm.cs361.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.file.SaveFileAction;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;

public class SaveTemplateAction extends SaveFileAction
{
	private static final long	serialVersionUID	= -5846294040633741021L;
	protected URI uri;
	protected Application a;
	protected View v;
	
	public SaveTemplateAction(Application app, @Nullable View view) {
		super(app, view, false);
		
	}
	
	@Override
	public void actionPerformed ( ActionEvent e )
	{
		String filename = UMLApplicationModel.prompt ( "file.saveTemplate.prompt", "Save Template" );
		String dirName = UMLApplicationModel.getProjectSettings().getString( "templateDir", 
				UMLApplicationModel.getProgramDirectory() + "Template" + System.getProperty( "file.separator" ) );
		File dir = new File ( dirName );
		if ( !dir.exists() ) dir.mkdir();
		Util.dprint( dirName );
		filename = dirName + 
				filename + ".xml";
		
		Util.dprint( filename );
		
		uri = URI.create( "file://" + filename );
		
		
		saveViewToURI ( getActiveView(), uri, null );
		end_hook ( );
	}
	
	protected void end_hook ( ) { }
}
