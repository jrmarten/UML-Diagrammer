package edu.uwm.cs361.classdiagram.decorations;

import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.decoration.AbstractLineDecoration;

public class InheritenceDecoration extends AbstractLineDecoration
{
	private double	angle, outerRadius, innerRadius;

	public InheritenceDecoration()
	{
		this(.35, 12, 11.3);
	}

	public InheritenceDecoration(double angle, double outer, double inner)
	{
		this(angle, outer, inner, false, false, false);
	}

	public InheritenceDecoration(double angle, double outer, double inner,
			boolean isFilled, boolean isStroked, boolean isSolid)
	{
		super(isFilled, isStroked, isSolid);
		this.angle = angle;
		innerRadius = inner;
		outerRadius = outer;
	}

	@Override
	protected Double getDecoratorPath(Figure f) {
		double offset = (isStroked()) ? 1 : 0;

		Path2D.Double path = new Path2D.Double();

		path.moveTo((outerRadius * Math.sin(-angle)),
				(offset + outerRadius * Math.cos(-angle)));
		path.lineTo(0, offset);
		path.lineTo((outerRadius * Math.sin(angle)),
				(offset + outerRadius * Math.cos(angle)));

		path.moveTo((outerRadius * Math.sin(angle)),
				(offset + outerRadius * Math.cos(angle)));

		path.lineTo(0, offset + 2 * Math.sin(angle));

		path.lineTo(outerRadius * Math.sin(-angle),
				offset + outerRadius * Math.cos(-angle));

		if (innerRadius != 0)
			{
				path.lineTo(0, (innerRadius + offset));
				path.closePath();
			}

		return path;
	}

	@Override
	protected double getDecoratorPathRadius(Figure f) {
		double offset = (isStroked()) ? 0.5 : -0.1;

		return innerRadius + offset;
	}

}
