/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import com.panayotis.xray.props.PropertyManager;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.lang.reflect.Method;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EnumPropertyManager extends PropertyManager<Object> {

    private JComboBox cb;
    private Object[] constants;

    public EnumPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
        constants = getter.getReturnType().getEnumConstants();

    }

    @Override
    protected JComponent createView() {
        if (cb == null) {
            cb = new JComboBox(constants);
            cb.addItemListener(a -> updateValue(a));
        }
        cb.setEnabled(!isReadOnly());
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(1, 2));
        p.add(new JLabel(getName()));
        p.add(cb);
        return p;
    }

    @Override
    public void updateView(Object value) {
        cb.setSelectedItem(value);
    }

    private void updateValue(ItemEvent a) {
        if (a.getStateChange() != ItemEvent.SELECTED)
            return;
        setValue(cb.getSelectedItem());
    }

    @Override
    public Object defaultValue() {
        return null;
    }

}
