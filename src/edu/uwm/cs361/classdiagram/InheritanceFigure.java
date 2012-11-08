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

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.LineConnectionFigure;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.classdiagram.data.UMLInterface;
import edu.uwm.cs361.settings.CSSRule;
import edu.uwm.cs361.settings.Style;

public class InheritanceFigure extends LineConnectionFigure
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5339743993660576339L;

	private static Color for_color = Color.black;
	
	public InheritanceFigure()
	{
		set(STROKE_COLOR, for_color );
		set(STROKE_WIDTH, 1d);
		
		set(END_DECORATION, new ArrowTip(0.60, 20.0 , 16.5, false, true, false));
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
		CSSRule inher_rule = style.get ( "Inheritance" );
		if ( inher_rule == null ) return;
		
		for_color = inher_rule.getColor( "forground-color" , for_color);
	}
	
	@Override
	public boolean canConnect(Connector start, Connector end) {
		if (!(start.getOwner() instanceof ClassFigure && end.getOwner() instanceof ClassFigure))
			return false;
		
		if ( ((ClassFigure)start.getOwner()).getData() instanceof UMLInterface &&
				(!(((ClassFigure)end.getOwner()).getData() instanceof UMLInterface)))
			return false;
		
		if (start.getOwner() == end.getOwner())
			return false;

		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();

		boolean isSuper = UMLClass.isSuper(par, child);

		dprint((isSuper) ? "Cannot have cyclic inheritance" : "");

		return !isSuper;
	}

	@Override
	public boolean canConnect(Connector end) {
		return (end.getOwner() instanceof ClassFigure);
	}

	@Override
	protected void handleConnect(Connector start, Connector end) {
		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();

		boolean dashed = ( child instanceof UMLInterface ) 
				||  ( par instanceof UMLInterface );
		
		if ( dashed )
			{
				setAttributeEnabled ( AttributeKeys.STROKE_DASHES, true );
				double[] x = { 3.0 };
				set( AttributeKeys.STROKE_DASHES , x );
				setAttributeEnabled ( AttributeKeys.STROKE_DASHES, false );
			}
		
		setAttributeEnabled ( AttributeKeys.END_DECORATION, true );
		set ( AttributeKeys.END_DECORATION, new ArrowTip(0.60, 20.0 , 16.5, false, true, true) );
		setAttributeEnabled ( AttributeKeys.END_DECORATION, false);
		
		child.addSuperclass(par);
	}
	
	@Override
	public int getLayer ( )
	{
		return 1;
	}

	@Override
	protected void handleDisconnect(Connector start, Connector end) {
		UMLClass child = ((ClassFigure) start.getOwner()).getData();
		UMLClass par = ((ClassFigure) end.getOwner()).getData();
		child.removeSuperclass(par);
	}
}
