/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.visuals.DoubleNumberPropertyVisuals;
import com.panayotis.xray.props.PropertyManager;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RectanglePropertyManager extends PropertyManager<Rectangle> {

    private final DoubleNumberPropertyVisuals location;
    private final DoubleNumberPropertyVisuals size;
    private final JPanel visuals;
    private int x, y, width, height;

    public RectanglePropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
        visuals = new JPanel(new BorderLayout());

        location = new DoubleNumberPropertyVisuals();
        location.setEnabled(!isReadOnly());
        location.addListener((gx, gy) -> setValue(new Rectangle(this.x = gx.intValue(), this.y = gy.intValue(), width, height)), false);
        location.setLabelName("  Location");

        size = new DoubleNumberPropertyVisuals();
        size.setEnabled(!isReadOnly());
        size.addListener((gwidth, gheight) -> setValue(new Rectangle(x, y, this.width = gwidth.intValue(), this.height = gheight.intValue())), false);
        size.setLabelName("  Size");

        JLabel nameL = new JLabel(name);
        nameL.setEnabled(!isReadOnly());
        visuals.add(nameL, BorderLayout.NORTH);
        JPanel spinners = new JPanel(new GridLayout(2, 1));
        spinners.add(location);
        spinners.add(size);
        visuals.add(spinners, BorderLayout.CENTER);
    }

    @Override
    protected JComponent createView() {
        return visuals;
    }

    @Override
    public void updateView(Rectangle item) {
        if (item != null) {
            x = item.x;
            y = item.y;
            width = item.width;
            height = item.height;
            location.update(x, y);
            size.update(width, height);
        }
    }

    @Override
    public Rectangle defaultValue() {
        return new Rectangle(0, 0, 0, 0);
    }

}
