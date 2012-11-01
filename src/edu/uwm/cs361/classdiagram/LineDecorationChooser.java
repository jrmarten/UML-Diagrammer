package edu.uwm.cs361.classdiagram;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LineDecorationChooser extends JFrame implements ActionListener
{

	private static final long	serialVersionUID	= 553854446330611986L;

	public LineDecorationChooser(String startOrEnd)
	{
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(200, 200);
		setLayout(new BorderLayout(10, 10));
		addComponents(startOrEnd);
	}

	private void addComponents(String startOrEnd) {
		JLabel instructions, blank;
		JRadioButton composition, aggregation, arrow;
		JButton ok, cancel;

		instructions = new JLabel("Select the " + startOrEnd + " decoration:");
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
		add(blank, BorderLayout.LINE_START);
		add(items, BorderLayout.CENTER);
		add(blank, BorderLayout.LINE_END);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("ok") || ac.equals("cancel")) {
			if (ac.equals("ok")) System.out.println("You clicked ok!");
			if (ac.equals("cancel")) System.out.println("You clicked cancel.");
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