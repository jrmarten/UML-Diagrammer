package edu.uwm.cs361.classdiagram;

import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Action;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.LabeledLineConnectionFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.layouter.LocatorLayouter;
import org.jhotdraw.draw.locator.BezierLabelLocator;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.action.EditRoleAction;
import edu.uwm.cs361.action.SetEndDecorationAction;
import edu.uwm.cs361.action.SetStartDecorationAction;
import edu.uwm.cs361.classdiagram.data.Connection;
import edu.uwm.cs361.classdiagram.data.ConnectionType;
import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.settings.CSSRule;
import edu.uwm.cs361.settings.Style;

/**
 * AssociationFigure.
 */
public class ConnectionFigure extends LabeledLineConnectionFigure implements PropertyChangeListener
{

	private static final long	serialVersionUID	= -1729547106413248257L;
	private static Color for_color = Color.black;
	private static final String DEFAULT_CONNECTION = "Association";
	
	private Connection con;
	
	private TextFigure a_role = new TextFigure ( );
	private TextFigure b_role = new TextFigure ( );
	
	/** Creates a new instance. */
	public ConnectionFigure()
	{
		a_role.addPropertyChangeListener( this );
		b_role.addPropertyChangeListener( this );
		
		init();	
	}
	
	private void init( )
	{
		setLayouter ( new LocatorLayouter ( ) );
		
		a_role.setEditable( false );
		b_role.setEditable( false );
		
		a_role.set( LocatorLayouter.LAYOUT_LOCATOR, new BezierLabelLocator ( 0, -Math.PI / 4, 8 ) );
		b_role.set( LocatorLayouter.LAYOUT_LOCATOR, new BezierLabelLocator ( 1, -Math.PI / 4, 8 ) );
		
		a_role.set( AttributeKeys.TEXT_COLOR, for_color );
		b_role.set( AttributeKeys.TEXT_COLOR, for_color );
		
		a_role.setText( "" );
		b_role.setText( "" );
		
		add ( a_role );
		add ( b_role );
		
		set(STROKE_COLOR, for_color);
		set(STROKE_WIDTH, 1d);
		set(END_DECORATION, null);
		set(START_DECORATION, null);
		
		setAttributeEnabled(STROKE_COLOR, false);
		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);
	}
	
	static { config(); }
	private static void config ( )
	{
		Style style = UMLApplicationModel.getProgramStyle();
		if ( style == null ) return;
		CSSRule association_rule = style.get( "Association" );
		if ( association_rule == null ) return;
		
		for_color = association_rule.getColor( "forground-color", Color.black);
	}

	/**
	 * Checks if two figures can be connected. Implement this method to constrain
	 * the allowed connections between figures.
	 */
	@Override
	public boolean canConnect(Connector start, Connector end) {
		
		return (start.getOwner() instanceof ClassFigure) &&
				(end.getOwner() instanceof ClassFigure );

	}

	@Override
	public boolean canConnect(Connector start) {
		return (start.getOwner() instanceof ClassFigure);
	}

	/**
	 * Handles the disconnection of a connection. Override this method to handle
	 * this event.
	 */
	@Override
	protected void handleDisconnect(Connector start, Connector end) {
		ClassFigure sf = (ClassFigure) start.getOwner();
		ClassFigure ef = (ClassFigure) end.getOwner();

		sf.getData().removeConnection( con );
		ef.getData().removeConnection( con );
	}
	
	/**
	 * Handles the connection of a connection. Override this method to handle this
	 * event.
	 */
	@Override
	protected void handleConnect(Connector start, Connector end) {
		
		/*
		 * after reading in from a file a connection will exist
		 * but this method is still called after reading
		 * 
		 */
		if ( con != null ) return;
		
		ClassFigure sf = (ClassFigure) start.getOwner();
		ClassFigure ef = (ClassFigure) end.getOwner();
		
		con = new Connection ( sf.getData(), ef.getData() );
		
		sf.getData().addConnection( con );
		ef.getData().addConnection( con );
	}
	
	public void setRoles ( String start_role, String end_role )
	{
		con.setRole( con.getStart(), start_role);
		con.setRole( con.getEnd(), end_role);
		
		willChange();
		a_role.setText( start_role );
		b_role.setText( end_role );
		changed();
	}

	@Override
	public ConnectionFigure clone() {
		
		ConnectionFigure that = (ConnectionFigure) super.clone();
		if ( con != null ) that.con = (Connection) con.clone();
		that.a_role = a_role.clone();
		that.b_role = b_role.clone();
		
		that.add( that.a_role );
		that.add( that.b_role );
		return that;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void removeNotify(Drawing d) 
	{
		if  ( con != null ) 
			{
				//if not null connection will have endings.
				con.getStart().removeConnection( con );
				con.getEnd().removeConnection( con );
			}
		super.removeNotify(d);
	}
	
	@Override
	public Collection<Action> getActions(Point2D.Double p) {
		Collection<Action> col = new ArrayList<Action>();
		col.add(new SetStartDecorationAction(this));
		col.add(new SetEndDecorationAction(this));
		col.add(new EditRoleAction ( this ) );
		return col;
	}
	
	@Override
	public void write ( DOMOutput out ) throws IOException
	{
		super.write( out );
		//writeAttributes ( out );
		
		out.openElement( "connection" );
		
		out.openElement( "connectionend" );
		out.addAttribute( "role", con.getRole(con.getStart()));
		out.addAttribute( "type" , con.getConnectionType( con.getStart() ).toString());
		out.closeElement();
		
		out.openElement( "connectionend" );
		out.addAttribute( "role", con.getRole(con.getEnd()));
		out.addAttribute( "type", con.getConnectionType( con.getEnd() ).toString() );
		
	}
	
	@Override
	public void read ( DOMInput in ) throws IOException
	{
		Util.dprint( "Reading Connection Figure" );
		
		super.read( in );
		//readAttributes ( in );
		
		UMLClass a, b;
		
		a = ((ClassFigure)getStartFigure()).getData();
		b = ((ClassFigure)getEndFigure()).getData();
		con = new Connection ( a, b );
		con.register();
		
		in.openElement( "connection" );
		
		in.openElement( "connectionend", 0 );
		con.setRole( a, in.getAttribute( "role", "") );
		con.setConnectionType(a, ConnectionType.parse( in.getAttribute("type", DEFAULT_CONNECTION)));
		in.closeElement();
		
		in.openElement( "connectionend", 1 );
		con.setRole( b, in.getAttribute ( "role", "" ) );
		con.setConnectionType( b, ConnectionType.parse( in.getAttribute("type", DEFAULT_CONNECTION)));
		in.closeElement();
		
		in.closeElement();
		
		willChange();
		a_role.setText( con.getRole ( a ) );
		b_role.setText( con.getRole ( b ) );
		changed();
		
		Util.dprint( a.getName() + ": " + con.getRole ( a ) );
		Util.dprint( b.getName() + ": " + con.getRole ( b ) );
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if ( !(e.getNewValue() instanceof String) ) return;
		
		Util.dprint( "Check PropertyName in ConnectionFigure " + e.getPropertyName() );
		
		if ( e.getSource().equals( a_role ) )
			{
				con.setRole( con.getStart() , (String)e.getNewValue());
			}
		if ( e.getSource().equals( b_role ) )
			{
				con.setRole( con.getEnd(), (String) e.getNewValue() ); 
			}
	}
	
	public String debug ( )
	{
		UMLClass a = con.getStart();
		UMLClass b = con.getEnd();
		
		return a.getName() + ": " + con.getRole( a ) + "\n" +
		b.getName() + ": " + con.getRole( b );
	}
}
