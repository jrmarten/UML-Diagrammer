package edu.uwm.cs361.classdiagram;

import static edu.uwm.cs361.Util.dprint;
import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

import java.awt.Color;

import org.jhotdraw.draw.connector.Connector;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.classdiagram.decorations.InheritanceDecoration;

public class InheritanceFigure extends AssociationFigure
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5339743993660576339L;

	public InheritanceFigure()
	{
		set(STROKE_COLOR, Color.red );
		set(STROKE_WIDTH, 1d);
		set(END_DECORATION, new InheritanceDecoration());
		set(START_DECORATION, new InheritanceDecoration ( ) );
		
		setAttributeEnabled(STROKE_COLOR, false);
		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);
		
		Util.dprint( "Creating Inheritance" );
	}

	public boolean canConnect(Connector start, Connector end) {
		if (!(start.getOwner() instanceof ClassFigure && end.getOwner() instanceof ClassFigure))
			return false;
		if (start.getOwner() == end.getOwner())
			return false;

		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();

		boolean isSuper = UMLClass.isSuper(par, child);

		dprint((isSuper) ? "Cannot have cyclic inheritance" : "");

		return !isSuper;
	}

	public boolean canConnect(Connector end) {
		return (end.getOwner() instanceof ClassFigure);
	}

	protected void handleConnect(Connector start, Connector end) {
		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();

		Util.dprint( "Connecting" );
		
		child.addSuperclass(par);
	}
	
	@Override
	public int getLayer ( )
	{
		return 1;
	}

	protected void handleDisconnect(Connector start, Connector end) {
		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();
		child.removeSuperclass(par);
	}
}
