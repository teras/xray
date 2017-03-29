/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import com.panayotis.xray.props.commons.AwareTextField;
import com.panayotis.xray.props.PropertyManager;
import java.awt.GridLayout;
import java.lang.reflect.Method;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class DefaultPropertyManager<T> extends PropertyManager<T> {

    protected AwareTextField field;

    public DefaultPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected JComponent createView() {
        field = new AwareTextField();
        field.addActionListener(event -> updateObject(field.getText()));
        field.setEnabled(!isReadOnly());
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        p.add(new JLabel(getName()));
        p.add(field);
        return p;
    }

    protected void updateObject(String value) {
        T nativeValue = null;
        try {
            nativeValue = convertValue(value);
        } catch (Exception ex) {
        }
        if (nativeValue == null)
            nativeValue = defaultValue();
        setValue(nativeValue);
    }

    @Override
    public void updateView(T value) {
        field.setText(value == null ? "" : value.toString());
    }

    protected abstract T convertValue(Object value);
}
