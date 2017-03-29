/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.PropertyManager;
import java.awt.BorderLayout;
import java.lang.reflect.Method;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public class IconPropertyManager extends PropertyManager<Icon> {

    private final JLabel image;
    private final JPanel panel;

    public IconPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
        panel = new JPanel(new BorderLayout());
        image = new JLabel();
        image.setHorizontalAlignment(JLabel.CENTER);
        panel.add(new JLabel(name), BorderLayout.NORTH);
        panel.add(image, BorderLayout.CENTER);
    }

    @Override
    protected JComponent createView() {
        return panel;
    }

    @Override
    public void updateView(Icon value) {
        image.setIcon(value);
    }

    @Override
    protected Icon defaultValue() {
        return null;
    }

}
