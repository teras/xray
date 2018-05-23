/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import com.panayotis.xray.props.PropertyManager;
import java.awt.BorderLayout;
import java.lang.reflect.Method;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BooleanPropertyManager extends PropertyManager<Boolean> {

    private JCheckBox cb;

    public BooleanPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected JComponent createView() {
        if (cb == null)
            cb = new JCheckBox();
        cb.setEnabled(!isReadOnly());
        cb.addActionListener(event -> setValue(cb.isSelected()));
        cb.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel(getName()), BorderLayout.WEST);
        p.add(cb, BorderLayout.CENTER);
        return p;
    }

    @Override
    public void updateView(Boolean value) {
        cb.setSelected(value);
    }

    @Override
    public Boolean defaultValue() {
        return false;
    }

}
