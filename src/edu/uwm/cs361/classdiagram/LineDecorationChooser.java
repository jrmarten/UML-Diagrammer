package edu.uwm.cs361.classdiagram;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.decoration.LineDecoration;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.classdiagram.data.Connection;
import edu.uwm.cs361.classdiagram.data.ConnectionType;

public class LineDecorationChooser extends JFrame implements ActionListener
{

	private static final long	serialVersionUID	= 553854446330611986L;
	private ConnectionFigure _data;
	private boolean _isEnd;
	private JRadioButton _composition, _aggregation, _association, _none;
	private ArrowTip _compTip = new ArrowTip(0.40, 15.0, 30.0);
	private ArrowTip _aggTip = new ArrowTip(0.40, 15.0, 30.0, false, true, true);
	private ArrowTip _assTip = new ArrowTip(0.35, 20, 18.4);
	
	
	private final static int WIDTH = 300;
	private final static int HEIGHT = 300;
	private static int standard_x;
	private static int standard_y;
	
	static
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		
		standard_x = (d.width/2) - (WIDTH/2);
		standard_y = (d.height/2) - (HEIGHT/2);
	}
	
	public LineDecorationChooser(ConnectionFigure data, boolean isEnd)
	{
		super();
		_data = data;
		_isEnd = isEnd;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		setBounds( standard_x, standard_y,
				WIDTH, HEIGHT);
		//setSize(300, 300);
		setLayout(new BorderLayout(0, 0));
		addComponents();
	}

	private void addComponents() {
		JLabel instructions;
		JButton ok, cancel;

		instructions = new JLabel("Select the " + (_isEnd ? "end" : "start") + " decoration:");

		_composition = new JRadioButton("Compostion");
		_aggregation = new JRadioButton("Aggregation");
		_association = new JRadioButton("Association");
		_none = new JRadioButton("None");
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		ok.setActionCommand("ok");
		cancel.setActionCommand("cancel");
		ok.addActionListener(this);
		cancel.addActionListener(this);		

		ButtonGroup group = new ButtonGroup();
		group.add(_composition);
		group.add(_aggregation);
		group.add(_association);
		group.add(_none);

		JPanel items = new JPanel();
		items.setLayout(new GridLayout(0, 2));
		items.add(_composition);
		items.add(new JLabel(createImageIcon("/edu/uwm/cs361/images/compositionDecoration.png",
						"Composition")));
		items.add(_aggregation);
		items.add(new JLabel(createImageIcon("/edu/uwm/cs361/images/aggregationDecoration.png",
						"Aggregation")));
		items.add(_association);
		items.add(new JLabel(createImageIcon("/edu/uwm/cs361/images/arrowTipDecoration.png",
						"Arrow")));
		items.add(_none);
		items.add(new JLabel(""));
		items.add(ok);
		items.add(cancel);

		add(instructions, BorderLayout.PAGE_START);
		add(items, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("ok") || ac.equals("cancel")) {
			if (ac.equals("ok")) {
				_data.willChange();
				
				ConnectionType conType = ConnectionType.AGGREGATION;
				
				if (_composition.isSelected()) conType = ConnectionType.COMPOSITION;
				else if (_aggregation.isSelected()) conType = ConnectionType.AGGREGATION;
				else if (_association.isSelected()) conType = ConnectionType.ASSOCIATION;
				else conType = ConnectionType.DEPENDENCY;
				
				Connection con = _data.getData();
				
				con.setConnectionType( (_isEnd)? con.getEnd() : con.getStart(), conType);
				
				AttributeKey<LineDecoration> key = AttributeKeys.START_DECORATION;
				if (_isEnd) key = AttributeKeys.END_DECORATION;
				_data.setAttributeEnabled(key, true);
				_data.set(key, conType.getDecoration());
				_data.setAttributeEnabled(key, false);
				Util.dprint( "modifying association tip.") ;
				_data.changed();
			}
			if (ac.equals("cancel")) Util.dprint("You clicked cancel.");
			this.dispose();
		}
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null)
			{
				return new ImageIcon(imgURL, description);
			} else
				{
					System.err.println("Couldn't find file: " + path);
					return null;
				}
	}
}
