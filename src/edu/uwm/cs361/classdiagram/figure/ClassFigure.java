package edu.uwm.cs361.classdiagram.figure;

import static edu.uwm.cs361.Util.dprint;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Action;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;

import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.ListFigure;
import org.jhotdraw.draw.RectangleFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.connector.ChopRectangleConnector;
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
import edu.uwm.cs361.classdiagram.data.Argument;
import edu.uwm.cs361.classdiagram.data.Attribute;
import edu.uwm.cs361.classdiagram.data.Method;
import edu.uwm.cs361.classdiagram.data.UMLAbstractClass;
import edu.uwm.cs361.classdiagram.data.UMLClass;
import edu.uwm.cs361.classdiagram.data.UMLInterface;
import edu.uwm.cs361.settings.Style;

@SuppressWarnings("serial")
public class ClassFigure extends GraphicalCompositeFigure {
	protected ListFigure nameList;
	protected TextFigure nameFig;
	protected ListFigure attrList = new ListFigure();
	protected ListFigure methodList = new ListFigure();

	protected SeparatorLineFigure sep1 = new SeparatorLineFigure();
	protected SeparatorLineFigure sep2 = new SeparatorLineFigure();
	private UMLClass data;

	private static class ColorSet {
		public Color fore_color = Color.black, back_color = Color.white, stroke_color = Color.black;
	}

	private static ColorSet CLASSCOLORS = new ColorSet();
	private static ColorSet ABSTRACTCOLORS = new ColorSet();
	private static ColorSet INTERFACECOLORS = new ColorSet();
	private ColorSet _colors;

	public ClassFigure() {
		this(new UMLClass());
	}

	public ClassFigure(UMLClass proto) {
		super(new RectangleFigure());

		data = (UMLClass) proto.clone();
		setLayouter(new VerticalLayouter());

		nameList = new ListFigure(); // container);
		nameFig = new TextFigure(proto.getName());

		setElements();

		Insets2D.Double insets = new Insets2D.Double(4, 8, 4, 8);

		attrList.set(LAYOUT_INSETS, insets);
		methodList.set(LAYOUT_INSETS, insets);
		nameList.set(LAYOUT_INSETS, insets);

		if (data.isAbstractClass()) {
			nameFig.set(FONT_ITALIC, true);
			nameFig.setAttributeEnabled(FONT_ITALIC, false);
		}
		nameFig.addFigureListener(new NameAdapter());
		nameList.add(nameFig);
	}

	private void setElements() {
		if (data instanceof UMLAbstractClass)
			_colors = ABSTRACTCOLORS;
		else if (data instanceof UMLInterface)
			_colors = INTERFACECOLORS;
		else
			_colors = CLASSCOLORS;

		setColors(_colors);

		removeAllChildren();
		add(nameList);
		if (!(data instanceof UMLInterface)) {
			add(sep1);
			add(attrList);
		}
		add(sep2);
		add(methodList);
	}

	private void setColors(ColorSet colors) {

		sep1.set(AttributeKeys.STROKE_COLOR, colors.stroke_color);
		sep2.set(AttributeKeys.STROKE_COLOR, colors.stroke_color);

		set(AttributeKeys.FILL_COLOR, colors.back_color);
		setAttributeEnabled(AttributeKeys.FILL_COLOR, false);

		// setAttributeEnabled ( AttributeKeys.STROKE_COLOR, true );
		set(AttributeKeys.STROKE_COLOR, colors.stroke_color);
		setAttributeEnabled(AttributeKeys.STROKE_COLOR, false);

		nameFig.set(AttributeKeys.FILL_COLOR, colors.back_color);
		setAttributeEnabled(AttributeKeys.TEXT_COLOR, true);
		nameFig.set(AttributeKeys.TEXT_COLOR, colors.fore_color);
		setAttributeEnabled(AttributeKeys.TEXT_COLOR, false);

		for (Figure fig : attrList.getChildren()) {
			fig.set(AttributeKeys.TEXT_COLOR, colors.fore_color);
		}

		for (Figure fig : methodList.getChildren()) {
			fig.set(AttributeKeys.TEXT_COLOR, colors.fore_color);
		}
	}

	/*
	static {
		readConfig();
	}
	*/

	public static void initColors() {
		Style style = UMLApplicationModel.getProgramStyle();
		if (style != null) {
			CLASSCOLORS.fore_color = style.getColor("Class.foreground-color",
					Color.black);
			CLASSCOLORS.back_color = style.getColor("Class.background-color",
					Color.white);
			CLASSCOLORS.stroke_color = style.getColor("Class.border-color",
					Color.black);

			ABSTRACTCOLORS.fore_color = style.getColor(
					"AbstractClass.foreground-color", CLASSCOLORS.fore_color);
			ABSTRACTCOLORS.back_color = style.getColor(
					"AbstractClass.background-color", CLASSCOLORS.back_color);
			ABSTRACTCOLORS.stroke_color = style.getColor(
					"AbstractClass.border-color", CLASSCOLORS.stroke_color);

			INTERFACECOLORS.fore_color = style.getColor(
					"Interface.foreground-color", CLASSCOLORS.fore_color);
			INTERFACECOLORS.back_color = style.getColor(
					"Interface.background-color", CLASSCOLORS.back_color);
			INTERFACECOLORS.stroke_color = style.getColor(
					"Interface.border-color", CLASSCOLORS.stroke_color);
		} else {
			CLASSCOLORS.fore_color = ABSTRACTCOLORS.fore_color = INTERFACECOLORS.fore_color = Color.black;
			CLASSCOLORS.back_color = ABSTRACTCOLORS.back_color = INTERFACECOLORS.back_color = Color.white;
			CLASSCOLORS.stroke_color = ABSTRACTCOLORS.stroke_color = INTERFACECOLORS.stroke_color = Color.black;
		}
	}

	@Override
	public Collection<Action> getActions(Point2D.Double p) {
		Collection<Action> col = new ArrayList<Action>();
		if (!(data instanceof UMLInterface))
			col.add(new AddAttributeAction(this));
		col.add(new AddMethodAction(this));
		if (!(data instanceof UMLInterface))
			col.add(new RemoveAttributeAction(this));
		col.add(new RemoveMethodAction(this));
		return col;
	}

	@Override
	public Collection<Handle> createHandles(int detailLevel) {
		List<Handle> handles = new LinkedList<Handle>();

		switch (detailLevel) {
		case -1:
			handles.add(new BoundsOutlineHandle(getPresentationFigure(), false,
					true));
			break;

		case 0:
			handles.add(new MoveHandle(this, RelativeLocator.northWest()));
			handles.add(new MoveHandle(this, RelativeLocator.northEast()));
			handles.add(new MoveHandle(this, RelativeLocator.southWest()));
			handles.add(new MoveHandle(this, RelativeLocator.southEast()));

			handles.add(new ConnectorHandle(new ChopRectangleConnector(this), new ConnectionFigure()));
			handles.add(new ConnectorHandle(new LocatorConnector(this,
					RelativeLocator.north()), new InheritanceFigure()));
			break;
		}
		return handles;
	}

	public boolean addAttribute(String str) {
		Attribute attr = Attribute.Create(str);
		boolean result = addAttribute(attr);

		if (result && getDrawing() != null ) {
			getDrawing().fireUndoableEditHappened(
					new AddAttributeEdit ( attr ) );
					//new AddAttributeAction.Edit(this, str));
		}

		return result;
	}

	private boolean addAttribute(Attribute attr) {
		if (attr == null)
			return false;
		TextFigure tmpFig;

		boolean added = data.addAttribute(attr);
		if (!added)
			return false;

		tmpFig = new TextFigure();
		tmpFig.setText(attr.toString());
		tmpFig.set(AttributeKeys.TEXT_COLOR, _colors.fore_color);
		if (attr.isStatic()) {
			tmpFig.set(FONT_UNDERLINE, true);
			tmpFig.setAttributeEnabled(FONT_UNDERLINE, false);
		}
		tmpFig.addFigureListener(new AttributeAdapter(tmpFig));

		willChange();
		attrList.add(tmpFig);
		changed();

		return added;
	}

	public void addMethod(String methtxt) {
		
		Method tmp = Method.Create(methtxt);
		if (addMethod(tmp) && getDrawing() != null ) {
			getDrawing().fireUndoableEditHappened(
					new AddMethodEdit ( tmp ) );
					
					//new AddMethodAction.Edit(this, methtxt));
		}
	}

	@Override
	public int getLayer() {
		return 0;
	}

	private boolean addMethod(Method meth) {
		if (meth == null) {
			UMLApplicationModel.error("error.Method.null", "Format Error");
			return false;
		}

		if (!data.addMethod(meth)) {
			UMLApplicationModel.error("id", "Not Overloading");
			return false;
		}

		TextFigure tmpFig = new TextFigure();
		tmpFig.setText(meth.toString());
		if (meth.isStatic())
			tmpFig.set(FONT_UNDERLINE, true);
		if (meth.isAbstract())
			tmpFig.set(FONT_ITALIC, true);
		tmpFig.set(AttributeKeys.TEXT_COLOR, _colors.fore_color);
		tmpFig.addFigureListener(new MethodAdapter(tmpFig));

		willChange();
		methodList.add(tmpFig);

		if (data.isAbstract() && !data.isAbstractClass()) {
			nameFig.set(FONT_ITALIC, true);
			_colors = ABSTRACTCOLORS;
			setColors(_colors);
		}
		changed();

		return true;
	}

	public void removeMethod(String methTxt) {
		if (methTxt == null)
			return;
		methTxt = methTxt.trim();
		if (Util.isEmpty(methTxt)) {
			UMLApplicationModel.error("edit.removeMethod.blankError",
					"Input Error");
			return;
		}

		boolean hasParams = (methTxt.contains("(") && methTxt.contains(")"));

		RemoveMethodEdit edit = new RemoveMethodEdit ( null );
		
		if (!methTxt.contains(" ") && !hasParams) // just the name is present
		{
			for (int i = 0; i < data.getMethods().size(); i++) {

				Method meth = data.getMethods().get(i);
				if (meth.getName().equals(methTxt)) {
					removeMethod(meth);
					edit.addEdit( new RemoveMethodEdit ( meth ) );
					i--;
				}
			}
		}

		if (hasParams)
		{
			//	return;

			String name = methTxt.substring(0, methTxt.indexOf("(")).trim();
			Argument[] list = Method.extractParams(methTxt);
			List<Argument> params = new LinkedList<Argument>();
			
			for (Argument param : list) {
				if (params == null)
					continue;
				params.add(param);
			}

			for (int i = 0; i < data.getMethods().size(); i++) {
				Method meth = data.getMethods().get(i);

				if (meth.getName().equals(name)
						&& Util.equals(params, meth.getParameters())) {
					removeMethod(meth);
					edit.addEdit( new RemoveMethodEdit ( meth ) );
				}
			}
		}
		
		if ( getDrawing() != null )
		{
			getDrawing().fireUndoableEditHappened( edit );
		}
	}

	public void removeAttribute(String attrTxt) {
		if (attrTxt == null)
			return;
		attrTxt = attrTxt.trim();
		if (Util.isEmpty(attrTxt))
			return;

		if (!attrTxt.contains(" ")) {
			for (Attribute attr : data.getAttributes()) {
				if (attr.getName().equals(attrTxt)) {
					attrTxt = attr.toString();
					break;
				}
			}
		}
		Attribute attr = Attribute.Create(attrTxt);
		if ( removeAttribute(attr) && getDrawing() != null )
		{
			getDrawing().fireUndoableEditHappened(
					new RemoveAttributeEdit ( attr ));
		}
	}

	private boolean removeAttribute(Attribute attr) {
		willChange();
		boolean ret = data.removeAttribute(attr);
		for (int i = 0; i < attrList.getChildCount(); i++) {
			Figure fig = attrList.getChild(i);
			if (fig instanceof TextFigure) {
				TextFigure tfig = (TextFigure) fig;

				if (attr.toString().equals(tfig.getText())) {
					attrList.remove(tfig);
					i--;
				}
			}
		}
		changed();
		return ret;
	}

	private void removeMethod(Method meth) {
		willChange();

		boolean abstractp = data.isAbstract();

		for (int i = 0; i < methodList.getChildCount(); i++) {
			Figure fig = methodList.getChild(i);
			if (fig instanceof TextFigure) {
				TextFigure tfig = (TextFigure) fig;

				if (meth.toString().equals(tfig.getText())) {
					methodList.remove(tfig);
					data.removeMethod(meth);
					i--;
				}
			}
		}
		if (abstractp && !data.isAbstract()) {
			Util.dprint("changing colors from abstract to concrete");
			_colors = CLASSCOLORS;
			setColors(_colors);
		}

		changed();
	}

	@Override
	public void read(DOMInput in) throws IOException {
		dprint("Reading ClassFigure");

		willChange();

		double x = in.getAttribute("x", 0d);
		double y = in.getAttribute("y", 0d);
		double w = in.getAttribute("w", 0d);
		double h = in.getAttribute("h", 0d);

		setBounds(new Point2D.Double(x, y), new Point2D.Double(x + w, y + h));

		in.openElement("class");

		String type = in.getAttribute("type", "class");
		data = (type.equals("class") ? new UMLClass() : (type
				.equals("abstract")) ? new UMLAbstractClass()
				: new UMLInterface());

		String name = in.getAttribute("name", "Opened");
		data.setName(name);
		nameFig.setText(name);

		changed();

		Util.dprint(name);
		int i = 0;
		int max = in.getElementCount("attribute");
		while (i < max) {
			in.openElement("attribute", i++);
			String attr_sig = "";
			attr_sig += in.getAttribute("access", "default") + " ";
			attr_sig += (in.getAttribute("static", false)) ? "static " : "";
			attr_sig += (in.getAttribute("final", false)) ? "final " : "";
			attr_sig += in.getAttribute("name", "attr_name") + " ";
			attr_sig += " : ";
			attr_sig += in.getAttribute("type", "void*") + " ";

			addAttribute(Attribute.Create(attr_sig));
			dprint(attr_sig);
			in.closeElement();
		}

		i = 0;
		max = in.getElementCount("method");
		while (i < max) {
			in.openElement("method", i++);
			String meth_sig = "";
			meth_sig += in.getAttribute("access", "default") + " ";
			meth_sig += (in.getAttribute("static", false)) ? "static " : "";
			meth_sig += (in.getAttribute("abstract", false)) ? "abstract " : "";
			meth_sig += in.getAttribute("type", "void*") + " ";
			meth_sig += in.getAttribute("name", "meth_name") + " ";

			LinkedList<String> params = new LinkedList<String>();
			int n = 0;
			int i_params = in.getElementCount("param");
			while (n < i_params) {
				in.openElement("param", n++);
				String tmp = in.getAttribute("name", "");
				tmp += " : " + in.getAttribute("type", "");
				if (!tmp.equals(""))
					params.add(tmp);
				in.closeElement();
			}

			meth_sig += "(" + Util.join(params, ", ") + ")";
			dprint(meth_sig);
			addMethod(meth_sig);
			in.closeElement();
		}

		willChange();
		readAttributes(in);
		setElements();
		changed();

		in.closeElement();
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

		for (Attribute attr : data.getAttributes()) {
			out.openElement("attribute");
			out.addAttribute("name", attr.getName());
			out.addAttribute("type", attr.getType());
			out.addAttribute("access", attr.getAccess().toString());
			out.addAttribute("final", (attr.isFinal()) ? "true" : "false");
			out.addAttribute("static", (attr.isStatic()) ? "true" : "false");
			out.closeElement();
		}
		for (Method meth : data.getMethods()) {
			out.openElement("method");
			out.addAttribute("name", meth.getName());
			out.addAttribute("type", meth.getType());
			out.addAttribute("access", meth.getAccess().toString());
			out.addAttribute("abstract", (meth.isAbstract()) ? "true" : "false");
			out.addAttribute("static", (meth.isStatic()) ? "true" : "false");

			for (Argument tmp : meth.getParameters()) {
				out.openElement("param");
				out.addAttribute("name", tmp.getName());
				out.addAttribute("type", tmp.getName());
				out.closeElement();
			}

			out.closeElement();
		}
		out.closeElement();
	}

	// XXX:FOR DEBUGING ONLY
	public String snapShot() {
		if ( !Util.debug() ) return null;
		
		String buffer = data.getDeclaration() + "{\n";
		for (Attribute attr : data.getAttributes()) {
			buffer += attr.getSignature() + "\n";
		}
		for (Method meth : data.getMethods()) {
			buffer += meth.getSignature() + "\n";
		}
		buffer += "}\n";

		return buffer;
	}

	@Override
	public String toString() {
		String buffer = "0x" + String.format("%x", hashCode()).toUpperCase();
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

	private class NameAdapter extends FigureAdapter {
		@Override
		public void attributeChanged(FigureEvent e) {
			willChange();
			if (e.getAttribute() == AttributeKeys.TEXT) {
				data.setName(e.getNewValue().toString());
				nameFig.setText(e.getNewValue().toString());
				nameFig.invalidate();
			}
			changed();
		}
	}

	private abstract class SimpleAdapter extends FigureAdapter {
		protected Figure fig;

		public SimpleAdapter(Figure fig) {
			this.fig = fig;
		}

		protected abstract boolean add(String n);

		protected abstract boolean rename(String old, String n);

		protected abstract boolean remove(String old);

		@Override
		public void attributeChanged(FigureEvent evt) {
			if (evt.getAttribute() == AttributeKeys.TEXT) {
				String n = (String) evt.getNewValue();
				String old = (String) evt.getOldValue();

				dprint("Class representation changed");

				boolean ret = false;

				if (((String) evt.getNewValue()).trim().equals("")) {
					ret = remove(old);
					dprint((ret) ? "removing element" : "Cannot remove element");
				}
				if (evt.getOldValue().equals("")) {
					ret = add(n);
					dprint((ret) ? "" : "Can not add new Attribute");
					dprint((ret) ? "" : "New: " + n + "\nOld: " + old);
				} else {
					ret = rename(old, n);
					dprint((ret) ? "" : "Can not rename");
				}

				if (ret) {
					fig.set(AttributeKeys.TEXT, Attribute.Create(n).toString());
				}
			}
		}

	}

	private class AttributeAdapter extends SimpleAdapter {
		public AttributeAdapter(Figure f) {
			super(f);
		}

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

	private class MethodAdapter extends SimpleAdapter {
		public MethodAdapter(Figure f) {
			super(f);
		}

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
	
	private class AddAttributeEdit extends AbstractUndoableEdit
	{
		private Attribute attr;
		
		public AddAttributeEdit ( Attribute attr ) { this.attr = attr; }
		
		@Override
		public void redo ( )
		{
			super.redo();
			ClassFigure.this.addAttribute ( attr );
		}
		
		@Override
		public void undo ( )
		{
			super.undo();
			ClassFigure.this.removeAttribute ( attr );
		}
	}
	
	private class AddMethodEdit extends AbstractUndoableEdit
	{
		private Method meth;
		public AddMethodEdit ( Method meth ) { this.meth = meth; }
		
		@Override
		public void redo ( )
		{
			super.redo();
			ClassFigure.this.addMethod ( meth );
		}
		
		@Override
		public void undo ( )
		{
			super.undo();
			ClassFigure.this.removeMethod ( meth );
		}
	}
	
	private class RemoveAttributeEdit extends AbstractUndoableEdit
	{
		private Attribute attr;
		public RemoveAttributeEdit ( Attribute attr ) { this.attr = attr; }
		
		@Override
		public void undo ( )
		{
			super.undo();
			ClassFigure.this.addAttribute ( attr );
		}
		
		@Override
		public void redo ( )
		{
			super.redo();
			ClassFigure.this.removeAttribute ( attr );
		}
	}
	
	private class RemoveMethodEdit extends AbstractUndoableEdit 
	{
		private Method meth;
		private LinkedList<Method> agg = new LinkedList<Method>();
		public RemoveMethodEdit ( Method meth ) { this.meth = meth; }
		
		@Override
		public void undo ( )
		{
			super.undo();
			if ( meth != null ) ClassFigure.this.addMethod ( meth );
			for ( Method tmp : agg )
			{
				ClassFigure.this.addMethod ( tmp );
			}
		}
		
		@Override
		public void redo ( )
		{
			super.redo();
			if ( meth != null ) ClassFigure.this.removeMethod ( meth );
			for ( Method tmp : agg )
			{
				ClassFigure.this.removeMethod ( tmp );
			}
		}
		
		@Override
		public boolean addEdit ( UndoableEdit edit )
		{
			if ( edit instanceof RemoveMethodEdit )
			{
				agg.add( ((RemoveMethodEdit)edit).meth );
				agg.addAll( ((RemoveMethodEdit)edit).agg );
				return true;
			}
			else return false;
		}
	}
}
