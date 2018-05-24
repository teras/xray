/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.PropertyManager;
import com.panayotis.xray.props.visuals.DoubleNumberPropertyVisuals;
import java.awt.Dimension;
import java.lang.reflect.Method;
import javax.swing.JComponent;

public class DimensionPropertyManager extends PropertyManager<Dimension> {

    private final DoubleNumberPropertyVisuals<Integer> visuals;

    public DimensionPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
        visuals = new DoubleNumberPropertyVisuals(0, Integer.MAX_VALUE);
        visuals.setEnabled(!isReadOnly());
        visuals.addListener((width, height) -> setValue(new Dimension(width.intValue(), height.intValue())));
        visuals.setLabelName(name);
    }

    @Override
    protected JComponent createView() {
        return visuals;
    }

    @Override
    public void updateView(Dimension item) {
        if (item != null)
            visuals.update(item.width, item.height);
    }

    @Override
    public Dimension defaultValue() {
        return new Dimension(0, 0);
    }

}
