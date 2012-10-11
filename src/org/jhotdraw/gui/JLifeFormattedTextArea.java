/*
 * @(#)JLifeFormattedTextArea.java
 * 
 * Copyright (c) 2009-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 * 
 * You may not use, copy or modify this file, except in compliance with the
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.jhotdraw.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.text.Document;

/**
 * JLifeFormattedTextArea.
 *
 * @author Werner Randelshofer
 * @version $Id: JLifeFormattedTextArea.java 717 2010-11-21 12:30:57Z rawcoder $
 */
public class JLifeFormattedTextArea extends JTextArea {

	/** This adapter is used for adapting Formatters to the JTextArea. */
	private JLifeFormattedTextField formattedTextFieldAdapter;
	/**
	 * Forwards property change events from the formattedTextFieldAdapter to
	 * listeners of this object.
	 */
	private PropertyChangeListener handler;

	/** Creates new instance. */
	public JLifeFormattedTextArea() {
	}

	@Override
	public void setDocument(Document newValue) {
		super.setDocument(newValue);

		// We must check for null here, because setDocument is called in the
		// super class constructor.
		if (formattedTextFieldAdapter == null) {
			formattedTextFieldAdapter = new JLifeFormattedTextField();
			handler = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getSource() == formattedTextFieldAdapter &&//
							evt.getPropertyName() == "value") {
						firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
					}
				}
			};
			formattedTextFieldAdapter.addPropertyChangeListener(handler);
		}

		formattedTextFieldAdapter.setDocument(newValue);
	}

	public void setValue(Object value) {
		formattedTextFieldAdapter.setValue(value);
	}

	public Object getValue() {
		return formattedTextFieldAdapter.getValue();
	}

	public void setFormatterFactory(JFormattedTextField.AbstractFormatterFactory newValue) {
		formattedTextFieldAdapter.setFormatterFactory(newValue);
	}

	public JFormattedTextField.AbstractFormatterFactory getFormatterFactory() {
		return formattedTextFieldAdapter.getFormatterFactory();
	}

	/**

    /** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
	}// </editor-fold>//GEN-END:initComponents
	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
}
