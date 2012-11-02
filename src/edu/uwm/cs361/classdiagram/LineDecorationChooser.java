package edu.uwm.cs361.classdiagram;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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

public class LineDecorationChooser extends JFrame implements ActionListener
{

	private static final long	serialVersionUID	= 553854446330611986L;
	private AssociationFigure _data;
	private String _startOrEnd;
	JRadioButton composition, aggregation, arrow;
	
	@SuppressWarnings("deprecation")
	public LineDecorationChooser(AssociationFigure data, String startOrEnd)
	{
		super();
		_data = data;
		_startOrEnd = startOrEnd;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(300, 300);
		setLayout(new BorderLayout(0, 0));
		addComponents();
		
	}

	private void addComponents() {
		JLabel instructions, blank;
		JButton ok, cancel;

		instructions = new JLabel("Select the " + _startOrEnd + " decoration:");
		blank = new JLabel("");

		composition = new JRadioButton("Compostion");
		aggregation = new JRadioButton("Aggregation");
		arrow = new JRadioButton("Arrow-Tip");
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		ok.setActionCommand("ok");
		cancel.setActionCommand("cancel");
		ok.addActionListener(this);
		cancel.addActionListener(this);		

		ButtonGroup group = new ButtonGroup();
		group.add(composition);
		group.add(aggregation);
		group.add(arrow);

		JPanel items = new JPanel();
		items.setLayout(new GridLayout(0, 2));
		items.add(composition);
		items.add(new JLabel(createImageIcon("/edu/uwm/cs361/images/compositionDecoration.png",
						"Composition")));
		items.add(aggregation);
		items.add(new JLabel(createImageIcon("/edu/uwm/cs361/images/aggregationDecoration.png",
						"Aggregation")));
		items.add(arrow);
		items.add(new JLabel(createImageIcon("/edu/uwm/cs361/images/arrowTipDecoration.png",
						"Arrow")));
		items.add(ok, BorderLayout.PAGE_END);
		items.add(cancel, BorderLayout.PAGE_END);

		add(instructions, BorderLayout.PAGE_START);
//		add(blank, BorderLayout.LINE_START);
		add(items, BorderLayout.CENTER);
//		add(blank, BorderLayout.LINE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("ok") || ac.equals("cancel")) {
			if (ac.equals("ok")) {
				_data.willChange();
				LineDecoration tip = new ArrowTip();
				if (composition.isSelected()) tip = new ArrowTip(0.40, 15.0, 30.0);
				else if (aggregation.isSelected()) {
					tip = new ArrowTip(0.40, 15.0, 30.0, false, true, true);
				}
				else if (arrow.isSelected()) tip = new ArrowTip(0.35, 20, 18.4);
				AttributeKey<LineDecoration> key = AttributeKeys.START_DECORATION;
				if (_startOrEnd.equals("end")) key = AttributeKeys.END_DECORATION;
				_data.setAttributeEnabled(key, true);
				_data.set(key, tip);
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
