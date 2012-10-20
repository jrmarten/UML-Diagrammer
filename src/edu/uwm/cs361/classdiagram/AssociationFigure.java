package edu.uwm.cs361.classdiagram;

import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;
import java.awt.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.*;

/**
 * AssociationFigure.
 */
public class AssociationFigure extends LineConnectionFigure
{

	/** Creates a new instance. */
	public AssociationFigure()
	{
		set(STROKE_COLOR, new Color(0x000099));
		set(STROKE_WIDTH, 1d);
		set(END_DECORATION, new ArrowTip());

		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);
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

		return true;
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
	public AssociationFigure clone() {
		AssociationFigure that = (AssociationFigure) super.clone();

		return that;
	}

	@Override
	public int getLayer() {
		return 1;
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
}
