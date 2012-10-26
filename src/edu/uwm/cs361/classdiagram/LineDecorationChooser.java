package edu.uwm.cs361.classdiagram;

import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

public class LineDecorationChooser extends JFrame{
	
	private static final long	serialVersionUID	= 553854446330611986L;

	public LineDecorationChooser(String startOrEnd)
	{
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(200, 200);
		setLayout(new GridLayout(0,2));
		addComponents(startOrEnd);
	}

	private void addComponents(String startOrEnd) {
		JRadioButton composition, aggregation, arrow;
		JLabel instructions = new JLabel("Select the " + startOrEnd + " decoration:");
		JButton ok = new JButton("Ok");
		JButton cancel = new JButton("Cancel");
		composition = new JRadioButton("Compostion");
		aggregation = new JRadioButton("Aggregation");
		arrow = new JRadioButton("Arrow-Tip");
		ButtonGroup group = new ButtonGroup();
		group.add(composition);
		group.add(aggregation);
		group.add(arrow);new JLabel(createImageIcon("/edu/uwm/cs361/images/tmpButton.png", "Composition"));
		add(instructions);
		add(new JLabel(""));
		add(composition);
		add(new JLabel(createImageIcon("/edu/uwm/cs361/images/tmpButton.png", "Composition")));
		add(aggregation);
		add(new JLabel(createImageIcon("/edu/uwm/cs361/images/tmpButton.png", "Composition")));
		add(arrow);
		add(new JLabel(createImageIcon("/edu/uwm/cs361/images/tmpButton.png", "Composition")));
		add(ok);
		add(cancel);
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
}