package edu.uwm.cs361.figures;

import java.awt.Graphics2D;
import static org.jhotdraw.draw.AttributeKeys.*;
import java.awt.geom.*;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.geom.*;

public class SeperationFigure
extends RectangleFigure
{
    public SeperationFigure ( ) { }

    @Override public Dimension2DDouble getPreferredSize ( )
    {
	double width = Math.ceil ( STROKE_WIDTH.get( this ) );
	return new Dimension2DDouble ( width, width );
    }

    @Override protected void drawFill ( Graphics2D g ) { }

    @Override protected void drawStroke ( Graphics2D g )
    {
	Rectangle2D.Double r = (Rectangle2D.Double) rectangle.clone();
	double grow = AttributeKeys.getPerpendicularDrawGrowth ( this );
	Geom.grow ( r, grow, grow );
	g.draw (  new Line2D.Double ( r.x, r.y, r.x+r.width-1, r.y) );
    }

}