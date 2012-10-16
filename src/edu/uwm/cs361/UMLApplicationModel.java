package edu.uwm.cs361;

import java.util.HashMap;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultApplicationModel;
import org.jhotdraw.app.View;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.uwm.cs361.classdiagram.ClassFigure;
import edu.uwm.cs361.sequencediagram.ObjectLifelineFigure;

import org.jhotdraw.draw.tool.ConnectionTool;
import org.jhotdraw.draw.tool.DelegationSelectionTool;
import org.jhotdraw.draw.tool.TextAreaCreationTool;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.draw.tool.CreationTool;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import org.jhotdraw.app.*;
import org.jhotdraw.app.action.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.samples.pert.figures.DependencyFigure;
import org.jhotdraw.util.*;

public class UMLApplicationModel extends DefaultApplicationModel
{

	private DefaultDrawingEditor		sharedEditor;
	private HashMap<String, Action>	actions;

	public UMLApplicationModel()
	{
	}

	// TODO: FINISH
	@Override
	public ActionMap createActionMap(Application a, @Nullable View v) {
		ActionMap m = super.createActionMap(a, v);
		ResourceBundleUtil drawLabels = ResourceBundleUtil
				.getBundle("edu.uwm.cs361.Labels");
		
		return m;

	}

	public DefaultDrawingEditor getSharedEditor() {
		if (sharedEditor == null)
			sharedEditor = new DefaultDrawingEditor();
		return sharedEditor;
	}

	@Override
	public java.util.List<JToolBar> createToolBars(Application app,
			@Nullable View view) {
		ResourceBundleUtil Labels = ResourceBundleUtil
				.getBundle("edu.uwm.cs361.Labels");
		UMLView umlv = (UMLView) view;

		DrawingEditor edit;
		edit = (umlv == null) ? getSharedEditor() : umlv.getEditor();

		LinkedList<JToolBar> list = new LinkedList<JToolBar>();

		JToolBar tmp = new JToolBar();
		addCreationButtonsTo(tmp, edit);
		tmp.setName(Labels.getString("window.drawToolBar.title"));

		list.add(tmp);

		return list;
	}

	// NOTE:might not make sense for this application
	@Override
	public void initView(Application a, @Nullable View p) {
		if (a.isSharingToolsAmongViews())
			((UMLView) p).setEditor(getSharedEditor());
	}

	@Override
	protected MenuBuilder createMenuBuilder() {
		return new UMLMenuBuilder();
	}

	private void addCreationButtonsTo(JToolBar tb, final DrawingEditor edit) {
		HashMap<AttributeKey, Object> attributes;

		ResourceBundleUtil labels = ResourceBundleUtil
				.getBundle("edu.uwm.cs361.Labels");
		ResourceBundleUtil drawLabels = ResourceBundleUtil
				.getBundle("org.jhotdraw.draw.Labels");
		// Resource bundles

		ButtonFactory.addSelectionToolTo(tb, edit);
		
		tb.addSeparator();

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new ClassFigure(),
				attributes), "edit.createClass", labels);

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.STROKE_COLOR, new Color(0x000099));
		ButtonFactory.addToolTo(tb, edit, new ConnectionTool(
				new DependencyFigure(), attributes), "edit.createDependency", labels);
		
		tb.addSeparator(); /**/
		
		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new ObjectLifelineFigure(),
				attributes), "edit.createLifeline", labels);
		
		tb.addSeparator();
		
		ButtonFactory.addToolTo(tb, edit, new TextAreaCreationTool(
				new TextAreaFigure()), "edit.createTextArea", drawLabels);

		ButtonFactory.addToolTo ( tb, edit, new DelegationSelectionTool()
				, "", labels );
	}

	@Override
	public URIChooser createOpenChooser(Application app, @Nullable View v) {
		JFileURIChooser c = new JFileURIChooser();
		c.addChoosableFileFilter(new ExtensionFileFilter("UML Diagram", "xml"));
		return c;
	}

	@Override
	public URIChooser createSaveChooser(Application a, @Nullable View v) {
		JFileURIChooser c = new JFileURIChooser();
		c.addChoosableFileFilter(new ExtensionFileFilter("UML Diagram", "xml"));
		return c;
	}

	private static class UMLMenuBuilder extends DefaultMenuBuilder
	{
		@Override
		public void addOtherViewItems(JMenu menu, Application app,
				@Nullable View view) {
			ActionMap am = app.getActionMap(view);
			JCheckBoxMenuItem check;
			check = new JCheckBoxMenuItem(am.get("view.toggleGrid"));
			ActionUtil.configureJCheckBoxMenuItem(check, am.get("view.toggleGrid"));
			menu.add(check);
		}

	}

	private static class ToolButtonListener implements ItemListener
	{
		private Tool					tool;
		private DrawingEditor	editor;

		public ToolButtonListener(Tool t, DrawingEditor e)
		{
			tool = t;
			editor = e;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED)
				{
					editor.setTool(tool);
				}
		}
	}
}
