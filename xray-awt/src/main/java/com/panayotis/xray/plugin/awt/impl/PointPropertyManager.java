/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.PropertyManager;
import com.panayotis.xray.props.visuals.DoubleNumberPropertyVisuals;
import java.awt.Point;
import java.lang.reflect.Method;
import javax.swing.JComponent;

public class PointPropertyManager extends PropertyManager<Point> {

    private final DoubleNumberPropertyVisuals<Integer> visuals;

    public PointPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
        visuals = new DoubleNumberPropertyVisuals<>(Integer.MIN_VALUE, Integer.MAX_VALUE);
        visuals.setEnabled(!isReadOnly());
        visuals.addListener((x, y) -> setValue(new Point(x.intValue(), y.intValue())));
        visuals.setLabelName(name);
    }

    @Override
    protected JComponent createView() {
        return visuals;
    }

    @Override
    public void updateView(Point item) {
        if (item != null)
            visuals.update(item.x, item.y);
    }

    @Override
    public Point defaultValue() {
        return new Point(0, 0);
    }

}
