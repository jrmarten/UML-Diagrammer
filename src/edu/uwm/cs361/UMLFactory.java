package edu.uwm.cs361;

import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.draw.connector.LocatorConnector;
import org.jhotdraw.draw.connector.ChopRectangleConnector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.samples.pert.figures.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.xml.*;

import com.sun.org.apache.bcel.internal.classfile.Attribute;
import com.sun.org.apache.bcel.internal.classfile.Method;

import edu.uwm.cs361.classdiagram.ClassFigure;
import edu.uwm.cs361.classdiagram.data.UMLClass;

//TODO: Change this stuff to be UML related instead of Pert related...
public class UMLFactory extends DefaultDOMFactory
{
	private final static Object[][]	classTagArray	= {
			{ DefaultDrawing.class, "UMLDiagram" },
			{ ClassFigure.class, "class" },

			{ UMLClass.class, "Class" }, // for lack of a better tag currently
			{ Method.class, "method" }, { Attribute.class, "attribute" },

			{ DependencyFigure.class, "dep" }, { ListFigure.class, "list" },
			{ TextFigure.class, "text" }, { GroupFigure.class, "g" },
			{ TextAreaFigure.class, "ta" },
			{ SeparatorLineFigure.class, "separator" },

			{ ChopRectangleConnector.class, "rectConnector" },
			{ LocatorConnector.class, "locConnector" },
			{ RelativeLocator.class, "relativeLocator" },
			{ ArrowTip.class, "arrowTip" }						};

	/** Creates a new instance. */
	public UMLFactory()
	{
		for (Object[] o : classTagArray)
			{
				addStorableClass((String) o[1], (Class) o[0]);
			}
	}
}
