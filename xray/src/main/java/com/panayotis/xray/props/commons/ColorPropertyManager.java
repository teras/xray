/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import com.panayotis.xray.props.PropertyManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Method;
import java.util.function.Function;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author teras
 * @param <C>
 */
public abstract class ColorPropertyManager<C> extends PropertyManager<C> {

    private final JPanel visuals;
    private final JPanel colorDisplay;
    private final Function<C, Color> fromData;
    private C ccolor;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public ColorPropertyManager(Object instance, String name, Method setter, Method getter, Function<Color, C> toData, Function<C, Color> fromData) {
        super(instance, name, setter, getter);
        this.fromData = fromData;

        visuals = new JPanel(new GridLayout(1, 2));
        JPanel cpanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(name);
        JButton popup = new JButton("Edit");
        colorDisplay = new JPanel();

        if (isReadOnly()) {
            label.setEnabled(false);
            popup.setEnabled(false);
        }

        popup.addActionListener(e -> {
            Color c = JColorChooser.showDialog(popup, name, fromData.apply(ccolor == null ? defaultValue() : ccolor));
            if (c != null)
                setValue(toData.apply(c));
        });

        cpanel.add(colorDisplay, BorderLayout.CENTER);
        cpanel.add(popup, BorderLayout.EAST);

        visuals.add(label);
        visuals.add(cpanel);
    }

    @Override
    protected JComponent createView() {
        return visuals;
    }

    @Override
    public void updateView(C value) {
        colorDisplay.setBackground(fromData.apply(ccolor = value));
    }
}
