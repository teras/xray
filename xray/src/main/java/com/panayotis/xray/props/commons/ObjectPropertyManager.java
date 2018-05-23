/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.awt.Color;
import java.lang.reflect.Method;
import java.util.function.Function;
import javax.swing.JComponent;

public class ObjectPropertyManager<T> extends GenericPropertyManager<T> {

    private final Function<Object, T> converter;

    public ObjectPropertyManager(Object instance, Function<Object, T> converter, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
        this.converter = converter;
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
    protected T convertValue(Object value) {
        if (converter != null)
            return converter.apply(value);
        try {
            return (T) value;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public T defaultValue() {
        return null;
    }

}
