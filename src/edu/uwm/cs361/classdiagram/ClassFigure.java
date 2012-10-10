package edu.uwm.cs361.figures;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.connector.LocatorConnector;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.ConnectionEndHandle;
import org.jhotdraw.draw.handle.ConnectorHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.layouter.VerticalLayouter;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.geom.Insets2D;
import static org.jhotdraw.draw.AttributeKeys.*;


import edu.uwm.cs361.uml.Attribute;
import edu.uwm.cs361.uml.Method;
import edu.uwm.cs361.uml.UMLClass;

@SuppressWarnings("serial")
public class ClassFigure extends GraphicalCompositeFigure {

    ListFigure nameList;
    ListFigure attrList;
    ListFigure methodList;

    private UMLClass data;

    private class NameAdapter extends FigureAdapter {

        private UMLClass target;

        public NameAdapter(UMLClass target) {
            this.target = target;
        }

        @Override public void attributeChanged(FigureEvent e) {
            data.setName ( (String) e.getNewValue() );
        }
    }

    private static class DurationAdapter extends FigureAdapter {

        private UMLClass target;

        public DurationAdapter(UMLClass target) {
            this.target = target;
        }

        @Override public void attributeChanged(FigureEvent evt) {
            // We could fire a property change event here, in case
            // some other object would like to observe us.
            //target.firePropertyChange("duration", e.getOldValue(), e.getNewValue());
            
        }
    }



    public ClassFigure ( )
    {
        super ( new RectangleFigure ( ) );
        setLayouter( new VerticalLayouter ( ) );

        RectangleFigure nameListPF = new RectangleFigure ( );
        nameListPF.set(STROKE_COLOR, null);
        nameListPF.setAttributeEnabled(STROKE_COLOR, false);
        nameListPF.set(FILL_COLOR, null);
        nameListPF.setAttributeEnabled(FILL_COLOR, false);
        ListFigure nameList = new ListFigure(nameListPF);
        ListFigure attrList = new ListFigure ( );
        ListFigure methodList = new ListFigure ( );
        SeperationFigure separator1 = new SeperationFigure ( );
        SeperationFigure separator2 = new SeperationFigure ( );

        add ( nameList );
        add ( separator1 );
        add ( attrList );
        add ( separator2 );
        add ( methodList );

        Insets2D.Double insets = new Insets2D.Double(4,8,4,8);
        nameList.set ( LAYOUT_INSETS, insets );
        attrList.set ( LAYOUT_INSETS, insets );
        methodList.set ( LAYOUT_INSETS, insets );

        TextFigure tmpFigure = createTextFigure( "Class " );
        nameList.add ( tmpFigure );

        tmpFigure.addFigureListener ( new NameAdapter ( data ) );
	//add Method, and Attribute Listeners
    }

    private TextFigure createTextFigure ( String text )
    {
        TextFigure result = new TextFigure ( );
        result.setText( text );
        result.set( FONT_BOLD, true );
        result.setAttributeEnabled ( FONT_BOLD, false );
        return result;
    }

    @Override public Collection<Handle> createHandles ( int detailLevel )
    {
        List<Handle> handles = new LinkedList<Handle>();

        switch ( detailLevel )
            {
            case -1:
                handles.add( new BoundsOutlineHandle ( getPresentationFigure(), false, true ) );
                break;

            case 0:
                handles.add(new MoveHandle(this, RelativeLocator.northWest()));
                handles.add(new MoveHandle(this, RelativeLocator.northEast()));
                handles.add(new MoveHandle(this, RelativeLocator.southWest()));
                handles.add(new MoveHandle(this, RelativeLocator.southEast()));
                //LocatorConnector lc = new LocatorConnector(this, RelativeLocator.east());
                //ConnectorHandle ch = new ConnectorHandle( lc);
                //ch.setToolTipText( "Drag the connector to a dependate class" );  //dependancy stuff
                break;
            }
        return handles;
    }

    public void setName ( String newName )
    {
        data.setName ( newName );
        getNameFigure().setText ( newName );
    }

    private TextFigure getNameFigure ( )
    {
        return (TextFigure) ((ListFigure) getChild(0)).getChild(0);
    }

    private TextFigure getAttributeFigure ( int index )
    {
        if ( index < 0 ) throw new IndexOutOfBoundsException ( );
        if ( index >= ((ListFigure) getChild(2)).getChildCount() )
            throw new IndexOutOfBoundsException( );

        return (TextFigure) ((ListFigure) getChild(2)).getChild(index);
    }


    private TextFigure getMethodFigure ( int index )
    {
        if ( index < 0 ) throw new IndexOutOfBoundsException ( );
        if ( index >= ((ListFigure) getChild(4)).getChildCount() )
            throw new IndexOutOfBoundsException( );

        return (TextFigure) ((ListFigure) getChild(4)).getChild(index);
    }

    public void addAttribute ( String attr )
    {
    	Attribute tmp = Attribute.Create ( attr );
    	if ( tmp == null ) return; //throw an error popup
    	String tmpText = tmp.toString();
    	tmpText = (tmp.isFinal() ) ? tmpText.toUpperCase(): tmpText;
    	TextFigure tmpFig = createTextFigure ( tmpText );
    	if ( tmp.isStatic() ) tmpFig.set( FONT_UNDERLINE, true);
    	attrList.add( tmpFig );
    }
    
    public void addMethod ( String methtxt )
    {
    	Method tmp = Method.Create( methtxt );
    	if ( tmp == null ) return; //thrwow an error popup
    	TextFigure tmpFig = createTextFigure ( tmp.toString() ) ;
    	if ( tmp.isStatic ( ) ) tmpFig.set( FONT_UNDERLINE, true );
    	if ( tmp.isAbstract ( ) ) tmpFig.set ( FONT_ITALIC, true );
    	methodList.add( tmpFig );
    }

    @Override public void
    read ( DOMInput in )
    throws IOException
    {
	double x = in.getAttribute ( "x", 0d );
	double y = in.getAttribute ( "y", 0d );
	double w = in.getAttribute ( "w", 0d );
	double h = in.getAttribute ( "h", 0d );

	setBounds ( new Point2D.Double ( x, y ), new Point2D.Double ( x+w, y+h ) );

	readAttributes ( in );

	in.openElement ( "class" );
	UMLClass umlclass = new UMLClass ( out.getAttribute ( "name", "class" ) );

	try {
	    while ( true )
		{
		    String attr_sig = "";
		    attr_sig += in.getAttribute ( "access", "default" ) + " ";
		    attr_sig += (in.getAttribute ( "static", false ))? "static ": "";
		    attr_sig += (in.getAttribute ( "final", false))? "final ":"";
		    attr_sig += in.getAttribute ( "name", "attr_name" ) + " ";
		    attr_sig += " : ";
		    attr_sig += in.getAttribute ( "type", "void*" ) + " ";
		    umlclass.addAttribute ( Attribute.Create ( attr_sig ) );
		}
	}catch ( Exception e ) { } 
	
	try {
	    while ( true ) 
		{
		    String meth_sig = "";
		    meth_sig += in.getAttribute ( "access", "default" ) + " ";
		    meth_sig += (in.getAttribute ( "static", false))? "static " : "";
		    meth_sig += (in.getAttribute ( "abstract", false))? "abstract " : "";
		    meth_sig += in.getAttribute ( "name", "meth_name" ) + " ";
		    meth_sig += " : ";
		    meth_sig += in.getAttribute ( "type", "void*" ) + " ";
		    umlclass.addMethod ( Method.Create ( meth_sig ) );
		}
	} catch ( Exception e ) { }

    }


    @Override public void 
    write ( DOMOutput out )
    throws IOException
    {
	Rectangle2D.Double r = getBounds ( );
	out.addAttribute ( "x", r.x );
	out.addAttribute ( "y", r.y );
	writeAttributes ( out );
	out.openElement ( "class" );
	out.addAttribute ( "name", data.getName ( ) );
	for ( Attribute attr : data.getAttributes() )
	    {
		out.openElement ( "attribute" );
		out.addAttribute ( "name", attr.getName ( ) );
		out.addAttribute ( "type", attr.getType ( ) );
		out.addAttributee ("access", attr.getAccess ( ).toString ( ) );
		out.addAttribute ( "final", (attr.isFinal())?"true":"false" );
		out.addAttribute ( "static", (attr.isStatic())?"true":"false" );
	    }
	for ( Method meth : data.getMethods ( ) )
	    {
		out.openElement ( "method" );
		out.addAttribute ( "name", meth.getName ( ) );
		out.addAttribute ( "type", meth.getType ( ) );
		out.addAttribute ( "access", meth.getAccess ( ).toString ( ) );
		out.addAttribute ( "abstract", (meth.isAbstract())?"true":"false");
		out.addAttribute ( "static", (meth.isStatic())?"true":"false");
	    }
    }
}
