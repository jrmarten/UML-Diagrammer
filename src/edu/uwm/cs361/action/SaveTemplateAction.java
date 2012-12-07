package edu.uwm.cs361.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.file.SaveFileAction;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.net.URIUtil;
import org.jhotdraw.util.ResourceBundleUtil;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;

/**
 * This class provides an action for the GUI
 * to save a UML Diagram drawing as a template. 
 * The template will be added to the Templates
 * menu under the File menu.
 */
public class SaveTemplateAction extends SaveFileAction {
	private static final long serialVersionUID = -5846294040633741021L;
	protected URI uri;

	private Component oldFocusOwner;

	public SaveTemplateAction(Application app, @Nullable View view) {
		super(app, view, false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		oldFocusOwner = SwingUtilities.getWindowAncestor(
				getActiveView().getComponent()).getFocusOwner();

		String filename = UMLApplicationModel.prompt(
				"file.saveTemplate.prompt", "Save Template");
		String dirName = UMLApplicationModel.getProjectSettings().getString(
				"templateDir",
				UMLApplicationModel.getProgramDirectory() + "Template"
						+ System.getProperty("file.separator"));
		File dir = new File(dirName);
		if (!dir.exists())
			dir.mkdir();
		Util.dprint(dirName);
		filename = dirName + filename + ".xml";

		filename = filename.replace( '\\', '/');
		
		uri = URI.create("file:///" + filename);

		saveViewToURI(getActiveView(), uri, null);
		end_hook();
	}

	protected void end_hook() {
	}

	// to avoid adding templates to the saved recently list in the application
	@Override
	protected void saveViewToURI(final View view, final URI file,
			@Nullable final URIChooser chooser) {
		view.execute(new Worker() {

			@Override
			protected Object construct() throws IOException {
				view.write(file, chooser);
				return null;
			}

			@Override
			protected void done(Object value) {
				view.setURI(file);
				view.markChangesAsSaved();
				int multiOpenId = 1;
				for (View p : view.getApplication().views()) {
					if (p != view && p.getURI() != null
							&& p.getURI().equals(file)) {
						multiOpenId = Math.max(multiOpenId,
								p.getMultipleOpenId() + 1);
					}
				}
				// getApplication().addRecentURI(file);
				view.setMultipleOpenId(multiOpenId);
			}

			@Override
			protected void failed(Throwable value) {
				value.printStackTrace();
				String message = value.getMessage() != null ? value
						.getMessage() : value.toString();
				ResourceBundleUtil labels = ResourceBundleUtil
						.getBundle("org.jhotdraw.app.Labels");
				JSheet.showMessageSheet(
						getActiveView().getComponent(),
						"<html>"
								+ UIManager.getString("OptionPane.css")
								+ "<b>"
								+ labels.getFormatted(
										"file.save.couldntSave.message",
										URIUtil.getName(file)) + "</b><p>"
								+ ((message == null) ? "" : message),
						JOptionPane.ERROR_MESSAGE);
			}

			@Override
			protected void finished() {
				view.setEnabled(true);
				SwingUtilities.getWindowAncestor(view.getComponent()).toFront();
				if (oldFocusOwner != null) {
					oldFocusOwner.requestFocus();
				}
			}
		});
	}
}
