/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.PropertyManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Method;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public class ColorPropertyManager extends PropertyManager<Color> {

    private final JPanel container;
    private final JPanel color;

    public ColorPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);

        color = new JPanel();
        JButton action = new JButton("Edit");
        action.addActionListener(a -> setValue(JColorChooser.showDialog(null, name, color.getBackground())));
        JPanel right = new JPanel(new BorderLayout());
        right.add(color, BorderLayout.CENTER);
        right.add(action, BorderLayout.EAST);

        container = new JPanel(new GridLayout(1, 2));
        container.add(new JLabel(name));
        container.add(right);
    }

    @Override
    protected JComponent createView() {
        return container;
    }

    @Override
    public void updateView(Color value) {
        if (value != null)
            color.setBackground(value);
    }

    @Override
    protected Color defaultValue() {
        return Color.black;
    }

}
