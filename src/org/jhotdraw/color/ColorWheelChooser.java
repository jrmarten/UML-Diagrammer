/*
 * @(#)ColorWheelChooser.java
 *
 * Copyright (c) 2008 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */

package org.jhotdraw.color;

import java.awt.*;
import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;
import javax.swing.plaf.*;

/**
 * A HSB color chooser, which displays a hue/saturation color wheel, and a
 * brightness slider.
 *
 * @author  Werner Randelshofer
 * @version $Id: ColorWheelChooser.java 717 2010-11-21 12:30:57Z rawcoder $
 */
public class ColorWheelChooser extends AbstractColorChooserPanel implements UIResource {
	private JColorWheel colorWheel;
	private ColorSliderModel ccModel = new DefaultColorSliderModel(HSBColorSpace.getInstance());

	private int updatingChooser;

	/**
	 * Creates a new instance.
	 */
	public ColorWheelChooser() {
		initComponents();

		int textSliderGap = UIManager.getInt("ColorChooser.textSliderGap");
		if (textSliderGap != 0) {
			BorderLayout layout = (BorderLayout) getLayout();
			layout.setHgap(textSliderGap);
		}

		colorWheel = new JColorWheel();
		add(colorWheel);

		ccModel.configureSlider(2, brightnessSlider);
		colorWheel.setModel(ccModel);

		ccModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				setColorToModel(ccModel.getColor());
			}
		});
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		brightnessSlider = new javax.swing.JSlider();

		setLayout(new java.awt.BorderLayout());

		brightnessSlider.setMajorTickSpacing(50);
		brightnessSlider.setOrientation(SwingConstants.VERTICAL);
		brightnessSlider.setPaintTicks(true);
		add(brightnessSlider, java.awt.BorderLayout.EAST);

	}//GEN-END:initComponents

	@Override
	protected void buildChooser() {
	}

	@Override
	public String getDisplayName() {
		return UIManager.getString("ColorChooser.colorWheel");
	}

	@Override
	public javax.swing.Icon getLargeDisplayIcon() {
		return UIManager.getIcon("ColorChooser.colorWheelIcon");
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return getLargeDisplayIcon();
	}

	@Override
	public void updateChooser() {
		updatingChooser++;
		ccModel.setColor(getColorFromModel());
		updatingChooser--;
	}
	public void setColorToModel(Color color) {
		if (updatingChooser == 0) {
			getColorSelectionModel().setSelectedColor(color);
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JSlider brightnessSlider;
	// End of variables declaration//GEN-END:variables

}
