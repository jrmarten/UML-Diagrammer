package edu.uwm.cs361.classdiagram;

import static edu.uwm.cs361.Util.dprint;
import static org.jhotdraw.draw.AttributeKeys.FILL_COLOR;
import static org.jhotdraw.draw.AttributeKeys.FONT_BOLD;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.ListFigure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.connector.LocatorConnector;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.draw.handle.ConnectorHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.layouter.VerticalLayouter;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.geom.Insets2D;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

import edu.uwm.cs361.UMLApplicationModel;
import edu.uwm.cs361.Util;
import edu.uwm.cs361.action.AddAttributeAction;
import edu.uwm.cs361.action.AddMethodAction;
import edu.uwm.cs361.action.RemoveAttributeAction;
import edu.uwm.cs361.action.RemoveMethodAction;
import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.classdiagram.data.UMLAbstractClass;
import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.classdiagram.data.UMLInterface;

@SuppressWarnings("serial")
public class ClassFigure extends GraphicalCompositeFigure
{

	protected ListFigure			nameList		= new ListFigure();
	protected TextFigure			nameFig;
	protected ListFigure			attrList		= new ListFigure();
	protected ListFigure			methodList	= new ListFigure();
	protected RectangleFigure	container		= new RectangleFigure();

	private UMLClass					data;

	private class NameAdapter extends FigureAdapter
	{
		@Override
		public void attributeChanged(FigureEvent e) {
			willChange();
			if (e.getAttribute() == AttributeKeys.TEXT)
				{
					data.setName(e.getNewValue().toString());
					nameFig.setText(e.getNewValue().toString());
					nameFig.invalidate();
				}
			changed();
		}
	}

	private abstract class SimpleAdapter extends FigureAdapter
	{
		protected abstract boolean add(String n);

		protected abstract boolean rename(String old, String n);

		protected abstract boolean remove(String old);

		@Override
		public void attributeChanged(FigureEvent evt) {
			String n = (String) evt.getNewValue();
			String old = (String) evt.getOldValue();

			dprint("Class representation changed");

			if (((String) evt.getNewValue()).trim().equals(""))
				{
					dprint((remove(old)) ? "removing element" : "Cannot remove element");
				}
			if (evt.getOldValue().equals(""))
				{
					dprint("Source is "
							+ ((evt.getSource() instanceof ListFigure) ? "" : "not ")
							+ "a ListFigure");
					boolean added = add(n);
					dprint((added) ? "" : "Can not add new Attribute");
					dprint((added) ? "" : "New: " + n + "\nOld: " + old);
				} else
				{
					dprint((rename(old, n)) ? "" : "Can not rename");
				}
		}
	}

	private class AttributeAdapter extends SimpleAdapter
	{
		@Override
		protected boolean add(String n) {
			Attribute attr = Attribute.Create(n);
			return data.addAttribute(attr);
		}

		@Override
		protected boolean rename(String old, String n) {
			Attribute newAttr = Attribute.Create(n);
			Attribute oldAttr = Attribute.Create(old);
			return data.removeAttribute(oldAttr) && data.addAttribute(newAttr);
		}

		@Override
		protected boolean remove(String old) {
			Attribute attr = Attribute.Create(old);
			return data.removeAttribute(attr);
		}

	}

	private class MethodAdapter extends SimpleAdapter
	{
		@Override
		protected boolean add(String n) {
			Method meth = Method.Create(n);
			return data.addMethod(meth);
		}

		@Override
		protected boolean rename(String old, String n) {
			Method newMeth = Method.Create(n);
			Method oldMeth = Method.Create(old);
			return data.removeMethod(oldMeth) && data.addMethod(newMeth);
		}

		@Override
		protected boolean remove(String old) {
			Method meth = Method.Create(old);
			return data.removeMethod(meth);
		}

	}

	public ClassFigure()
	{
		this(new UMLClass());
	}

	public ClassFigure(UMLClass proto)
	{
		super(new RectangleFigure());

		data = (UMLClass) proto.clone();
		setLayouter(new VerticalLayouter());

		container.set(STROKE_COLOR, null);
		container.setAttributeEnabled(STROKE_COLOR, false);
		container.set(FILL_COLOR, null);
		container.setAttributeEnabled(FILL_COLOR, false);
		ListFigure nameList = new ListFigure(container);
		ListFigure attrList = new ListFigure();
		ListFigure methodList = new ListFigure();
		SeparatorLineFigure separator1 = new SeparatorLineFigure();
		SeparatorLineFigure separator2 = new SeparatorLineFigure();

		add(nameList);
		if (!(data instanceof UMLInterface))
			{
				add(separator1);
				add(attrList);
			}
		add(separator2);
		add(methodList);

		Insets2D.Double insets = new Insets2D.Double(4, 8, 4, 8);
		nameList.set(LAYOUT_INSETS, insets);
		attrList.set(LAYOUT_INSETS, insets);
		methodList.set(LAYOUT_INSETS, insets);

		nameFig = createTextFigure("Class");
		if (data.isAbstractClass())
			{
				nameFig.set(FONT_ITALIC, true);
				nameFig.setAttributeEnabled(FONT_ITALIC, false);
			}
		nameFig.addFigureListener(new NameAdapter());
		nameList.add(nameFig);
	}

	public void update() {
		removeAllChildren();

		if (data.isAbstract())
			nameFig.set(FONT_ITALIC, true);
		nameFig.addFigureListener(new NameAdapter());

		nameList.removeAllChildren();
		nameList.add(nameFig);
		add(nameList);

		SeparatorLineFigure sep1 = new SeparatorLineFigure();
		SeparatorLineFigure sep2 = new SeparatorLineFigure();

		if (!(data instanceof UMLInterface))
			{
				add(sep1);
				add(attrList);
			}
		add(sep2);
		add(methodList);

	}

	@Override
	public Collection<Action> getActions(Point2D.Double p) {
		Collection<Action> col = new ArrayList<Action>();
		if (!( data instanceof UMLInterface)) col.add(new AddAttributeAction(this));
		col.add(new AddMethodAction(this));
		if (!(data instanceof UMLInterface)) col.add(new RemoveAttributeAction(this));
		col.add(new RemoveMethodAction(this));
		return col;
	}

	private static TextFigure createTextFigure(String text) {
		TextFigure result = new TextFigure();
		result.setText(text);
		result.set(FONT_BOLD, true);
		result.setAttributeEnabled(FONT_BOLD, false);
		return result;
	}

	@Override
	public Collection<Handle> createHandles(int detailLevel) {
		List<Handle> handles = new LinkedList<Handle>();

		switch (detailLevel)
			{
				case -1:
					handles.add(new BoundsOutlineHandle(getPresentationFigure(), false,
							true));
				break;

				case 0:
					handles.add(new MoveHandle(this, RelativeLocator.northWest()));
					handles.add(new MoveHandle(this, RelativeLocator.northEast()));
					handles.add(new MoveHandle(this, RelativeLocator.southWest()));
					handles.add(new MoveHandle(this, RelativeLocator.southEast()));
					
					handles.add( new ConnectorHandle ( new LocatorConnector ( this, RelativeLocator.north()) ,
							new InheritanceFigure( ) ));
				break;
			}
		return handles;
	}

	public boolean addAttribute(String str) {
		Attribute attr = Attribute.Create(str);
		boolean result = addAttribute(attr);

		return result;
	}

	private boolean addAttribute(Attribute attr) {
		if (attr == null)
			return false;

		TextFigure tmpFig;

		boolean added = data.addAttribute(attr);
		if ( !added ) return false;

		tmpFig = new TextFigure();
		tmpFig.setText(attr.toString());
		if (attr.isStatic())
			{
				tmpFig.set(FONT_UNDERLINE, true);
				tmpFig.setAttributeEnabled(FONT_UNDERLINE, false);
			}
		tmpFig.addFigureListener(new AttributeAdapter());

		willChange();
		boolean added_fig = attrList.add(tmpFig);
		update();
		changed();

		dprint(tmpFig.getText());

		dprint((added_fig) ? "" : "TextFigure not added to attrList");
		dprint((added) ? "" : "ATTRIBUTE NOT ADDED TO DATA");
		return added;
	}

	public void addMethod(String methtxt) {
		Method tmp = Method.Create(methtxt);
		addMethod(tmp);
	}

	@Override
	public int getLayer() {
		return 0;
	}

	private void addMethod(Method meth) {
		if (meth == null)
			{
				UMLApplicationModel.error( "error.Method.null", "Format Error" ) ;
				return;
			}
		
		data.addMethod(meth);

		TextFigure tmpFig = new TextFigure();// createTextFigure(meth.toString());
		tmpFig.setText(meth.toString());
		if (meth.isStatic())
			tmpFig.set(FONT_UNDERLINE, true);
		if (meth.isAbstract())
			tmpFig.set(FONT_ITALIC, true);
		tmpFig.addFigureListener(new MethodAdapter());

		willChange();
		methodList.add(tmpFig);

		if (data.isAbstract() && !data.isAbstractClass())
			{
				nameFig.set(FONT_ITALIC, true);
			}
		update();
		changed();
	}

	public void removeMethod(String methTxt) {
		if ( methTxt == null ) return;
		methTxt = methTxt.trim();
		if ( Util.isEmpty ( methTxt ) )
			{
				UMLApplicationModel.error("edit.removeMethod.blankError", "Input Error");
				return;
			}
		
		boolean hasParams = ( methTxt.contains("(") && methTxt.contains(")") );
		
		if ( !methTxt.contains(" ") && !hasParams ) //just the name is present
			{
				Iterator<Method> it = data.getMethods().iterator();
				while ( it.hasNext() )
					{
						Method meth = it.next();
						if ( meth.getName().equals(methTxt) )
							{
								removeMethod ( meth );
								it.remove();
							}
					}
				return;
			}
		
		if ( !hasParams ) return;
		
		
		String name = methTxt.substring(0, methTxt.indexOf("(")).trim();
		String[] list = Method.extractParams( methTxt );
		List<String> params = new LinkedList<String> ( );
		
		for ( String param : list )
			{
				param = param.trim();
				if ( params.equals( "" ) ) continue;
				params.add ( param );
			}
		
		Iterator<Method> it = data.getMethods().iterator();
		while ( it.hasNext() )
			{
				Method meth = it.next();
				
				if ( meth.getName().equals( name ) 
						&& Util.equals( params, meth.getParameters()))
					{
						removeMethod ( meth );
						it.remove();
					}
					
			}
	}
	
	public void removeAttribute ( String attrTxt )
	{
		attrTxt = attrTxt.trim();
		if ( Util.isEmpty( attrTxt ) ) return;
		
		if ( !attrTxt.contains( " " ) )
			{
				for ( Attribute attr : data.getAttributes() )
					{
						if ( attr.getName().equals(attrTxt) )
							{
								removeAttribute ( attr );
								return;
							}	
					}
			}
		Attribute attr = Attribute.Create( attrTxt );
		removeAttribute( attr );
	}
	
	private void removeAttribute ( Attribute attr )
	{
		willChange();
		data.removeAttribute( attr );
		for ( int i = 0; i < attrList.getChildCount(); i++ )
			{
				Figure fig = attrList.getChild(i);
				if ( fig instanceof TextFigure )
					{
						TextFigure tfig = (TextFigure) fig;
						
						if ( attr.toString().equals( tfig.getText() ) )
							{
								attrList.remove( tfig );
								i--;
							}
					}
			}
		changed();
		
	}
	
	private void removeMethod ( Method meth )
	{
		willChange ( );
		
		for ( int i = 0; i < methodList.getChildCount(); i++ )
			{
				Figure fig = methodList.getChild( i );
				if ( fig instanceof TextFigure )
					{
						TextFigure tfig = (TextFigure) fig;
						
						if ( meth.toString().equals( tfig.getText() ) )
							{
								methodList.remove ( tfig );
								i--;
							}
					}
			}
		
		changed();
	}

	@Override
	public void read(DOMInput in) throws IOException {
		dprint ( "Reading ClassFigure" );
		
		double x = in.getAttribute("x", 0d);
		double y = in.getAttribute("y", 0d);
		double w = in.getAttribute("w", 0d);
		double h = in.getAttribute("h", 0d);
		
		setBounds(new Point2D.Double(x, y), new Point2D.Double(x + w, y + h));
		
		in.openElement("class");
		
		UMLClass proto;
		String type = in.getAttribute("type", "class");
		proto = (type.equals("class")? new UMLClass ( ) : (type.equals("abstract"))?
				new UMLAbstractClass ( ) : new UMLInterface ( ));
		data = (UMLClass) proto.clone();
		
		data.setName( in.getAttribute ( "name", "Class") );
		nameFig.set( AttributeKeys.TEXT, data.getName() );
		
		int i = 0;
		int max = in.getElementCount( "attribute" );
		while ( i < max )
			{
				in.openElement("attribute", i++);
				String attr_sig = "";
				attr_sig += in.getAttribute("access", "default") + " ";
				attr_sig += (in.getAttribute("static", false)) ? "static " : "";
				attr_sig += (in.getAttribute("final", false)) ? "final " : "";
				attr_sig += in.getAttribute("name", "attr_name") + " ";
				attr_sig += " : ";
				attr_sig += in.getAttribute("type", "void*") + " ";// XXX:
				
				addAttribute ( Attribute.Create( attr_sig ) );
				dprint ( attr_sig );
				in.closeElement();
			}
		
		i = 0;
		max = in.getElementCount( "method" );
		while ( i < max )
			{
				in.openElement("method", i++);
				String meth_sig = "";
				meth_sig += in.getAttribute("access", "default") + " ";
				meth_sig += (in.getAttribute("static", false)) ? "static " : "";
				meth_sig += (in.getAttribute("abstract", false)) ? "abstract " : "";
				meth_sig += in.getAttribute("type", "void*" ) + " ";
				meth_sig += in.getAttribute("name", "meth_name") + " ";
				
				LinkedList<String> params = new LinkedList<String> ( );
				int n = 0;
				int i_params = in.getElementCount("param");
				while ( n < i_params )
					{
						in.openElement("param", n++);
						String tmp = in.getAttribute("name", "");
						if ( !tmp.equals("") )
							params.add( tmp );
						in.closeElement();
					}
				
				meth_sig += "(" + Util.join( params, ", ") + ")";
				dprint ( meth_sig );
				addMethod (meth_sig );
				in.closeElement();
			}
		
		dprint ( toString() );
		
		readAttributes(in);
		
		in.closeElement();
		
		update();
		
		//in.closeElement();
	}

	@Override
	public void write(DOMOutput out) throws IOException {
		Rectangle2D.Double r = getBounds();
		out.addAttribute("x", r.x);
		out.addAttribute("y", r.y);
		writeAttributes(out);
		
		out.openElement("class");
		out.addAttribute("name", data.getName());
		out.addAttribute("type", data.getType());
		
		for (Attribute attr : data.getAttributes())
			{
				out.openElement("attribute");
				out.addAttribute("name", attr.getName());
				out.addAttribute("type", attr.getType());
				out.addAttribute("access", attr.getAccess().toString());
				out.addAttribute("final", (attr.isFinal()) ? "true" : "false");
				out.addAttribute("static", (attr.isStatic()) ? "true" : "false");
				out.closeElement();
			}
		for (Method meth : data.getMethods())
			{
				out.openElement("method");
				out.addAttribute("name", meth.getName());
				out.addAttribute("type", meth.getType());
				out.addAttribute("access", meth.getAccess().toString());
				out.addAttribute("abstract", (meth.isAbstract()) ? "true" : "false");
				out.addAttribute("static", (meth.isStatic()) ? "true" : "false");
				
				for ( String tmp : meth.getParameters() )
					{
						out.openElement( "param" );
						out.addAttribute("name", tmp);
						out.closeElement();
					}
				
				out.closeElement();
			}
		out.closeElement();
	}

	public void removeDependency(AssociationFigure fig) {
		Figure other;
		if (fig.getStartFigure() == this)
			other = fig.getEndFigure();
		else
			other = fig.getStartFigure();

		if (!(other instanceof ClassFigure))
			return;

		ClassFigure cfig = (ClassFigure) other;

		data.addDependency(cfig.getData());
	}

	public void addDependency(AssociationFigure fig) {
		Figure other;
		if (fig.getStartFigure() == this)
			other = fig.getEndFigure();
		else
			other = fig.getStartFigure();

		if (!(other instanceof ClassFigure))
			return;

		ClassFigure cfig = (ClassFigure) other;

		data.addDependency(cfig.getData());
	}

	// XXX:FOR DEBUGING ONLY
	public String snapShot() {

		String buffer = data.getDeclaration() + "{\n";
		for (Attribute attr : data.getAttributes())
			{
				buffer += attr.getSignature() + "\n";
			}
		for (Method meth : data.getMethods())
			{
				buffer += meth.getSignature() + "\n";
			}
		buffer += "}\n";

		return buffer;
	}

	public String toString() {
		String buffer =  "0x" + String.format("%x", hashCode ( ) ).toUpperCase();
		buffer += "\n" + snapShot();
		
		return buffer;
	}

	public UMLClass getData() {
		return data;
	}

	@Override
	public ClassFigure clone() {
		ClassFigure fig = new ClassFigure((UMLClass) data.clone());

		return fig;
	}
}
