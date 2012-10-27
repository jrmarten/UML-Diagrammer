package edu.uwm.cs361;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultApplicationModel;
import org.jhotdraw.app.DefaultMenuBuilder;
import org.jhotdraw.app.MenuBuilder;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.ActionUtil;
import org.jhotdraw.app.action.file.LoadRecentFileAction;
import org.jhotdraw.app.action.view.ToggleViewPropertyAction;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.tool.ConnectionTool;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.draw.tool.TextAreaCreationTool;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.util.ResourceBundleUtil;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.uwm.cs361.action.AddAttributeAction;
import edu.uwm.cs361.action.AddMethodAction;
import edu.uwm.cs361.action.DebugSnapShotAction;
import edu.uwm.cs361.action.JavaGenerationAction;
import edu.uwm.cs361.action.SaveTemplateAction;
import edu.uwm.cs361.classdiagram.AssociationFigure;
import edu.uwm.cs361.classdiagram.ClassFigure;
import edu.uwm.cs361.classdiagram.InheritanceFigure;
import edu.uwm.cs361.classdiagram.data.UMLAbstractClass;
import edu.uwm.cs361.classdiagram.data.UMLInterface;
import edu.uwm.cs361.sequencediagram.ActivationFigure;
import edu.uwm.cs361.sequencediagram.LifelineFigure;
import edu.uwm.cs361.settings.Settings;
import edu.uwm.cs361.tool.ClickTool;
import edu.uwm.cs361.tool.SingleSelectionTool;

public class UMLApplicationModel extends DefaultApplicationModel
{

	private static final long	serialVersionUID	= 5744235372635623155L;
	
	private DefaultDrawingEditor	sharedEditor;

	public UMLApplicationModel()
	{
	}

	private static ResourceBundleUtil	projectLabels	= null;

	public static String getProperty ( String id )
	{
		return getProjectResources().getString( id );
	}
	
	public static ResourceBundleUtil getProjectResources() {
		if (projectLabels == null)
			projectLabels = ResourceBundleUtil.getBundle("edu.uwm.cs361.Labels");

		return projectLabels;
	}

	public static String prompt(String id) {
		return JOptionPane.showInputDialog(getProjectResources().getString(id));
	}
	
	public static String prompt(String id, String title) {
		return JOptionPane.showInputDialog(getProjectResources().getString(id), title);
	}

	public static void error(String id, String title) {
		JOptionPane.showMessageDialog(null, getProjectResources().getString(id),
				title, JOptionPane.ERROR_MESSAGE);
	}
	

	// TODO: FINISH
	@Override
	public ActionMap createActionMap(Application a, @Nullable View v) {
		ActionMap m = super.createActionMap(a, v);
		AbstractAction aa;
		
    m.put("view.toggleGrid", aa = new ToggleViewPropertyAction(a, v, UMLView.GRID_VISIBLE_PROPERTY));
    getProjectResources().configureAction(aa, "view.toggleGrid");
		return m;

	}

	public DefaultDrawingEditor getSharedEditor() {
		if (sharedEditor == null)
			sharedEditor = new UMLDrawingEditor();
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
		addGeneralButtonsTo(tmp, edit);
		tmp.setName(Labels.getString("window.generalToolBar.title"));

		list.add(tmp);

		tmp = new JToolBar();
		addClassButtonsTo(tmp, edit);
		tmp.setName(Labels.getString("window.classToolBar.title"));

		list.add(tmp);

		tmp = new JToolBar();
		addSequenceButtonsTo(tmp, edit);
		tmp.setName(Labels.getString("window.sequenceToolBar.title"));

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

	private void addGeneralButtonsTo(JToolBar tb, final DrawingEditor edit) {
		HashMap<AttributeKey, Object> attributes;

		ResourceBundleUtil labels = getProjectResources();

		ButtonFactory.addSelectionToolTo(tb, edit);

		ButtonFactory.addToolTo(tb, edit, new TextAreaCreationTool(
				new TextAreaFigure()), "edit.createTextArea", labels);

	}

	private void addClassButtonsTo(JToolBar tb, final DrawingEditor edit) {
		HashMap<AttributeKey, Object> attributes;

		ResourceBundleUtil labels = getProjectResources();

		ButtonFactory.addSelectionToolTo(tb, edit);

		tb.addSeparator();

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new ClassFigure(),
				attributes), "edit.createClass", labels);

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new ClassFigure(
				new UMLAbstractClass()), attributes), "edit.createAbstractClass",
				labels);

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new ClassFigure(
				new UMLInterface()), attributes), "edit.createInterface", labels);

		tb.addSeparator();

		ButtonFactory.addToolTo(tb, edit, new SingleSelectionTool(
				new AddAttributeAction(null)), "edit.addAttribute", labels);

		ButtonFactory.addToolTo(tb, edit, new SingleSelectionTool(
				new AddMethodAction(null)), "edit.addMethod", labels);

		tb.addSeparator();

		ButtonFactory.addToolTo(tb, edit, new ConnectionTool(
				new AssociationFigure()), "edit.createAssociation", labels);

		ButtonFactory.addToolTo(tb, edit, new ConnectionTool(
				new InheritanceFigure()), "edit.createInheritance", labels);

		if (Util.debug())
			{
				tb.addSeparator();

				ButtonFactory.addToolTo(tb, edit, new ClickTool(
						new DebugSnapShotAction(edit.getActiveView())),
						"edit.DebugSnapShot", labels);

			}
		tb.addSeparator();

		ButtonFactory.addToolTo(tb, edit, new ClickTool(new JavaGenerationAction(
				edit.getActiveView())), "edit.javaGenerator", getProjectResources());
	}

	private void addSequenceButtonsTo(JToolBar tb, final DrawingEditor edit) {
		HashMap<AttributeKey, Object> attributes;

		ResourceBundleUtil labels = getProjectResources();

		ButtonFactory.addSelectionToolTo(tb, edit);

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new LifelineFigure(),
				attributes), "edit.createLifeline", labels);

		attributes = new HashMap<AttributeKey, Object>();
		attributes.put(AttributeKeys.FILL_COLOR, Color.white);
		attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
		attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
		ButtonFactory.addToolTo(tb, edit, new CreationTool(new ActivationFigure(),
				attributes), "edit.createActivation", labels);
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
		public void addLoadFileItems ( JMenu menu, Application app,
				@Nullable View view )
		{
			//ActionMap am = app.getActionMap( view );
			
			JMenu sub = new JMenu ( getProperty ( "file.openTemplate.text" ) );
			
			for ( File tmp : getTemplates() )
				{
					JMenuItem template = new JMenuItem ( );
					String filename = tmp.getName();
					
					if ( filename.endsWith(".xml"))
						{
							filename = filename.substring(0, filename.lastIndexOf( "." ) );
							template.setText( filename );
							URI uri = URI.create( "file://" + tmp.getAbsolutePath() );
							template.addActionListener( new LoadRecentFileAction ( app, view, uri ) );
							sub.add( template );
						}
				}
			
			menu.add( sub );
		}
		
		public void addSaveFileItems ( JMenu menu, Application app,
				@Nullable View view )
		{
			super.addSaveFileItems ( menu, app, view );
			
			JMenuItem template = new JMenuItem (  );
			template.setText( getProperty ( "file.saveTemplate.text" ) );
			template.addActionListener( new SaveTemplateAction ( app, view ) );
			menu.add( template );
		}
		
		public LinkedList<File> getTemplates ( )
		{
			LinkedList<File> templates = new LinkedList<File> ( );
			Settings s = Settings.getGlobal();
			String dir_name = s.getString("templateDir", Settings.getProgDir() + "Templates" );
			File templateDir = new File ( dir_name );
			
			if ( !templateDir.isDirectory() ) return templates;
			
			for ( File tmp : templateDir.listFiles() )
				{
					templates.add( tmp );
				}
			
			return templates;
		}

		@Override
		public void addOtherViewItems(JMenu menu, Application app,
				@Nullable View view) {
			ActionMap am = app.getActionMap(view);
			JCheckBoxMenuItem check;
			check = new JCheckBoxMenuItem(am.get("view.toggleGrid"));
			ActionUtil.configureJCheckBoxMenuItem(check, am.get("view.toggleGrid"));
			check.setText( getProjectResources().getString( "view.toggleGrid.text" ) );
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
