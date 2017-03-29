/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.awt.Color;
import java.lang.reflect.Method;
import javax.swing.JComponent;

public class ObjectPropertyManager extends DefaultPropertyManager<Integer> {

    public ObjectPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected JComponent createView() {
        JComponent view = super.createView();
        this.field.setOpaque(!isReadOnly());
        this.field.setBackground(Color.yellow);
        this.field.setEnabled(false);
        return view;
    }

    @Override
    protected Integer convertValue(Object value) {
        try {
            if (value != null)
                return Integer.getInteger(value.toString());
        } catch (NumberFormatException ex) {
        }
        return null;
    }

    @Override
    public Integer defaultValue() {
        return 0;
    }

}
