package edu.uwm.cs361.classdiagram;

import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
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
import edu.uwm.cs361.action.AssociationFigureAction;
import edu.uwm.cs361.action.SetEndDecorationAction;
import edu.uwm.cs361.action.SetStartDecorationAction;
import edu.uwm.cs361.locator.MiddleBezierLabelLocator;
import edu.uwm.cs361.settings.CSSRule;
import edu.uwm.cs361.settings.Style;

/**
 * AssociationFigure.
 */
public class ConnectionFigure extends LabeledLineConnectionFigure
{

	private static final long	serialVersionUID	= -1729547106413248257L;
	private static Color for_color = Color.black;
	
	private TextFigure a_mult;
	private TextFigure b_mult;
	private TextFigure role;
	
	/** Creates a new instance. */
	public ConnectionFigure()
	{
		setLayouter ( new LocatorLayouter ( ) );
		
		set(STROKE_COLOR, for_color);
		set(STROKE_WIDTH, 1d);
		set(END_DECORATION, null);
		set(START_DECORATION, null);
		
		a_mult = new TextFigure ( "" );
		a_mult.set( AttributeKeys.TEXT_COLOR, for_color );
		a_mult.set( LocatorLayouter.LAYOUT_LOCATOR, new BezierLabelLocator ( 0, Math.PI / 4, 1 ) );
		a_mult.setAttributeEnabled( LocatorLayouter.LAYOUT_LOCATOR, false);
		a_mult.setAttributeEnabled( AttributeKeys.TEXT_COLOR, false);
		
		b_mult = new TextFigure ( "" );
		b_mult.set( AttributeKeys.TEXT_COLOR, for_color );
		b_mult.set( LocatorLayouter.LAYOUT_LOCATOR, new BezierLabelLocator ( 1, -Math.PI/4, 8) );
		b_mult.setAttributeEnabled( AttributeKeys.TEXT_COLOR, false);
		
		role = new TextFigure ( "" );
		
		role.set( AttributeKeys.TEXT_COLOR, for_color );
		role.set( LocatorLayouter.LAYOUT_LOCATOR, new MiddleBezierLabelLocator ( 0.5) );//new BezierLabelLocator ( 0.5, Math.PI / 2, 1 ) );
		role.setAttributeEnabled ( AttributeKeys.TEXT_COLOR, false );
		role.setEditable( false );
		
		
		setAttributeEnabled(STROKE_COLOR, false);
		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);
		
		add ( a_mult );
		add ( b_mult );
		add ( role );
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
		/*
		 * if ((start.getOwner() instanceof ClassFigure) && (end.getOwner()
		 * instanceof ClassFigure)) {
		 * 
		 * ClassFigure sf = (ClassFigure) start.getOwner(); ClassFigure ef =
		 * (ClassFigure) end.getOwner();
		 * 
		 * // Disallow multiple connections to same dependent if
		 * (ef.getPredecessors().contains(sf)) { return false; }
		 * 
		 * // Disallow cyclic connections return !sf.isDependentOf(ef); }
		 */
		
		return (start.getOwner() instanceof ClassFigure) &&
				(end.getOwner() instanceof ClassFigure );

	}
	
	public String getRoleName ( )
	{
		return role.getText();
	}
	
	public void setRoleName ( String str )
	{
		willChange ( );
		role.willChange();
		role.setAttributeEnabled( AttributeKeys.TEXT, true);
		role.set ( AttributeKeys.TEXT, str );
		role.setAttributeEnabled( AttributeKeys.TEXT, false);
		invalidate();
		role.changed();
		changed();
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

		sf.removeDependency(this);
		ef.removeDependency(this);
	}
	
	/**
	 * Handles the connection of a connection. Override this method to handle this
	 * event.
	 */
	@Override
	protected void handleConnect(Connector start, Connector end) {
		ClassFigure sf = (ClassFigure) start.getOwner();
		ClassFigure ef = (ClassFigure) end.getOwner();

		sf.addDependency(this);
		ef.addDependency(this);
	}

	@Override
	public ConnectionFigure clone() {
		ConnectionFigure that = (ConnectionFigure) super.clone();
		that.a_mult = (TextFigure) a_mult.clone();
		that.b_mult = (TextFigure) b_mult.clone();
		that.role = (TextFigure) role.clone();
		
		that.add( that.a_mult );
		that.add( that.b_mult );
		that.add( that.role );

		return that;
	}

	@Override
	public int getLayer() {
		return -1;
	}

	@Override
	public void removeNotify(Drawing d) {
		if (getStartFigure() != null)
			{
				((ClassFigure) getStartFigure()).removeDependency(this);
			}
		if (getEndFigure() != null)
			{
				((ClassFigure) getEndFigure()).removeDependency(this);
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
		writeAttributes ( out );
	}
	
	@Override
	public void read ( DOMInput in ) throws IOException
	{
		readAttributes ( in );
		
	}
	
	private static class EditRoleAction extends AssociationFigureAction
	{
		public static final String ID = "actions.editRoleAction";
		
		public EditRoleAction ( ConnectionFigure c )
		{
			super ( ID, c );
			UMLApplicationModel.getProjectResources().configureAction(this, ID);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name = UMLApplicationModel.prompt( "actions.editRoleAction.prompt", "Edit Role name" );
			_data.setRoleName( name );
		}
		
	}
}
