package edu.uwm.cs361.locator;

import java.awt.geom.Point2D;

import org.jhotdraw.draw.BezierFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.locator.Locator;

public class MiddleBezierLabelLocator implements  Locator
{
	private double relPos;
	
	private static final double ERROR = 0.00001;
	
	public MiddleBezierLabelLocator ( double relPos )
	{
		if ( relPos < 0.0 ) relPos = 0.0;
		if ( relPos > 1.0 ) relPos = 1.0;
		
		this.relPos = relPos;
	}

	@Override
	public Point2D.Double locate(Figure owner) {
		return getLoc ( (BezierFigure) owner );
	}

	@Override
	public Point2D.Double locate(Figure owner, Figure dependent) {
		Point2D.Double ret = getLoc ( (BezierFigure) owner );
		ret.x -= dependent.getBounds().width / 2;
		
		return ret;
	}
	
	private Point2D.Double getLoc ( BezierFigure own )
	{
		int node_count = own.getNodeCount();
		
		double node_approx = ( (node_count-1) * relPos );
		int specific_node = (int) node_approx;
		
		if ( node_approx - specific_node < ERROR )
			{
				return own.getNode( specific_node ).getControlPoint(0);
			}
		else
			{
				Point2D.Double ret = new Point2D.Double();
				
				Point2D.Double a = own.getNode( specific_node ).getControlPoint( 0 );
				Point2D.Double b = own.getNode( specific_node + 1).getControlPoint( 0 );
				
				ret.x = (a.x + b.x) / 2;
				ret.y = (a.y + b.y) / 2;
				
				return ret;
			}
	}
}
