package edu.uwm.cs361;

import org.jhotdraw.draw.DefaultDrawing;
import org.jhotdraw.draw.GroupFigure;
import org.jhotdraw.draw.ListFigure;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.connector.ChopRectangleConnector;
import org.jhotdraw.draw.connector.LocatorConnector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.samples.pert.figures.DependencyFigure;
import org.jhotdraw.samples.pert.figures.SeparatorLineFigure;
import org.jhotdraw.xml.DefaultDOMFactory;

import com.sun.org.apache.bcel.internal.classfile.Attribute;
import com.sun.org.apache.bcel.internal.classfile.Method;

import edu.uwm.cs361.classdiagram.ConnectionFigure;
import edu.uwm.cs361.classdiagram.ClassFigure;
import edu.uwm.cs361.classdiagram.InheritanceFigure;
import edu.uwm.cs361.classdiagram.data.UMLClass;


public class UMLFactory extends DefaultDOMFactory
{
	private final static Object[][]	classTagArray	= {
			{ DefaultDrawing.class, "UMLDiagram" },
			{ ClassFigure.class, "ClassFigure" },
			{ UMLClass.class, "class" },
			{ Method.class, "method" }, 
			{ Attribute.class, "attribute" },
			{ DependencyFigure.class, "depfigure" },
			{ InheritanceFigure.class, "inheritfigure" },
			{ ListFigure.class, "list" },
			{ TextFigure.class, "text" },
			{ GroupFigure.class, "g" },
			{ TextAreaFigure.class, "ta" },
			{ SeparatorLineFigure.class, "separator" },
			{ ConnectionFigure.class, "associationFigure" },
			{ ChopRectangleConnector.class, "rectConnector" },
			{ LocatorConnector.class, "locConnector" },
			{ RelativeLocator.class, "relativeLocator" },
			{ ArrowTip.class, "arrowTip" },					
	};

	/** Creates a new instance. */
	public UMLFactory()
	{
		for (Object[] o : classTagArray)
			{
				addStorableClass((String) o[1], (Class) o[0]);
			}
	}
}
