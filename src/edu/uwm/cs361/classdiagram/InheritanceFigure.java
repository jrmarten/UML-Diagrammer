package edu.uwm.cs361.classdiagram;

import java.awt.Color;

import org.jhotdraw.draw.connector.Connector;

import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.classdiagram.decorations.InheritenceDecoration;

import static org.jhotdraw.draw.AttributeKeys.*;
import static edu.uwm.cs361.Util.dprint;

public class InheritanceFigure extends AssociationFigure
{

	public InheritanceFigure()
	{
		set(STROKE_COLOR, Color.BLUE);
		set(STROKE_WIDTH, 1d);
		set(END_DECORATION, new InheritenceDecoration());

		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);
	}

	public boolean canConnect(Connector start, Connector end) {
		if (!(start.getOwner() instanceof ClassFigure && end.getOwner() instanceof ClassFigure))
			return false;

		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();

		boolean isSuper = UMLClass.isSuper(par, child);

		dprint((isSuper) ? "Cannot have cyclic inheritence" : "");

		return !isSuper;
	}

	public boolean canConnect(Connector end) {
		return (end.getOwner() instanceof ClassFigure);
	}

	protected void handleConnect(Connector start, Connector end) {
		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();

		child.addSuperclass(par);
	}

	protected void handleDisconnect(Connector start, Connector end) {
		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();
		child.removeSuperclass(par);
	}

}
