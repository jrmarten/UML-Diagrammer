package edu.uwm.cs361;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.jhotdraw.app.*;
import org.jhotdraw.app.action.edit.RedoAction;
import org.jhotdraw.app.action.edit.UndoAction;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.gui.PlacardScrollPaneLayout;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.undo.UndoRedoManager;

import edu.umd.cs.findbugs.annotations.Nullable;


public class UMLView extends AbstractView
{
	private JScrollPane scrollpane = new JScrollPane ( );
	private DefaultDrawingView view = new DefaultDrawingView ( );
	private DrawingEditor edit;
	private UndoRedoManager undo;


	public UMLView ( )
	{
		initScroll ( );
		initEditor ( );
	}

	public void initEditor ( )
	{
		setEditor( new DefaultDrawingEditor());
		undo = new UndoRedoManager ( );
		view.setDrawing( createDrawing ( ) );

		view.getDrawing().addUndoableEditListener ( undo );

		getActionMap().put ( UndoAction.ID, undo.getUndoAction() );
		getActionMap().put ( RedoAction.ID, undo.getRedoAction() );

		undo.addPropertyChangeListener ( new PropertyChangeListener() {
			@Override public void propertyChange ( PropertyChangeEvent e )
			{
				setHasUnsavedChanges ( undo.hasSignificantEdits ( ) );
			}
		});
	}

	public void initScroll ( )
	{
		scrollpane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setViewportView ( view );
		setLayout( new BorderLayout( ) );
		add ( scrollpane );


		scrollpane.setLayout(new PlacardScrollPaneLayout());
		scrollpane.setBorder ( new EmptyBorder ( 0, 0, 0, 0 ));
	}


	public void setEditor ( DrawingEditor newEditor)
	{
		DrawingEditor old = edit;
		if ( old != null ) old.remove(view);

		edit = newEditor;
		if ( newEditor != null ) newEditor.add( view );
	}

	protected DefaultDrawing createDrawing ( )
	{
		DefaultDrawing drawing = new DefaultDrawing ( );
		//io blah
		return drawing;
	}

	public boolean canSaveTo( URI uri )
	{
		return uri.getPath().endsWith(".xml");
	}

	@Override protected void setHasUnsavedChanges ( boolean newValue)
	{
		super.setHasUnsavedChanges( newValue );
		undo.setHasSignificantEdits( newValue );
	}

	@Override public void clear() {
		// TODO Auto-generated method stub

	}

	@Override public void write(URI uri, @Nullable URIChooser chooser) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override public void read(URI uri, @Nullable URIChooser chooser) throws IOException {
		// TODO Auto-generated method stub

	}

	public DrawingEditor getEditor() {
		return edit;
	}
}