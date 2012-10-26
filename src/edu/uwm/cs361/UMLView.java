package edu.uwm.cs361;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.jhotdraw.app.AbstractView;
import org.jhotdraw.app.action.edit.RedoAction;
import org.jhotdraw.app.action.edit.UndoAction;
import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;
import org.jhotdraw.draw.io.ImageOutputFormat;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.gui.PlacardScrollPaneLayout;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.undo.UndoRedoManager;

import edu.umd.cs.findbugs.annotations.Nullable;


public class UMLView extends AbstractView
{
	public final static String GRID_VISIBLE_PROPERTY = "gridVisible";
	
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
		DOMStorableInputOutputFormat ioFormat = 
				new DOMStorableInputOutputFormat ( new UMLFactory ( ) );
		
		
		LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
    inputFormats.add(ioFormat);
    drawing.setInputFormats(inputFormats);
    LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
    outputFormats.add(ioFormat);
    outputFormats.add(new ImageOutputFormat());
    drawing.setOutputFormats(outputFormats);
		
		return drawing;
	}
	
	public boolean isGridVisible ( )
	{
		return view.isConstrainerVisible();
	}
	
	public void setGridVisible(boolean newValue) {
    boolean oldValue = isGridVisible();
    view.setConstrainerVisible(newValue);
    firePropertyChange(GRID_VISIBLE_PROPERTY, oldValue, newValue);
    preferences.putBoolean("view.gridVisible", newValue);
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
		 final Drawing newDrawing = createDrawing();
     try {
         SwingUtilities.invokeAndWait(new Runnable() {

             @Override
             public void run() {
                 view.getDrawing().removeUndoableEditListener(undo);
                 view.setDrawing(newDrawing);
                 view.getDrawing().addUndoableEditListener(undo);
                 undo.discardAllEdits();
             }
         });
     } catch (InvocationTargetException ex) {
         ex.printStackTrace();
     } catch (InterruptedException ex) {
         ex.printStackTrace();
     }
	}

	@Override public void write(URI uri, @Nullable URIChooser chooser) throws IOException {
		Drawing draw = view.getDrawing();
		draw.getOutputFormats().get(0).write ( uri, draw );
	}

	@Override public void read(URI uri, @Nullable URIChooser chooser) throws IOException {
		try
			{
				final Drawing draw = createDrawing();
				draw.getInputFormats().get(0).read(uri, draw, true);

				SwingUtilities.invokeAndWait(new Runnable()
					{

						@Override
						public void run() {
							view.getDrawing().removeUndoableEditListener( undo );
							view.setDrawing(draw);
              view.getDrawing().addUndoableEditListener(undo);
              undo.discardAllEdits();
						}

					});
			} catch (InterruptedException e)
			{
				InternalError error = new InternalError();
				e.initCause(e);
				throw error;
			} catch (InvocationTargetException e)
			{
				InternalError error = new InternalError();
				e.initCause(e);
				throw error;
			}
	}

	public DrawingEditor getEditor() {
		return edit;
	}
}