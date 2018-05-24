/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.visuals;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 *
 * @author teras
 * @param <N>
 */
public class AwareSpinner<N extends Number & Comparable<N>> extends JPanel {

    private final JSpinner spinner;
    private final SpinnerNumberModel model;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AwareSpinner(N min, N max) {
        super(new BorderLayout());
        spinner = new JSpinner(model = new SpinnerNumberModel(num(min, 0), min, max, num(min, 1)));
        spinner.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setOpaque(e.getKeyCode() != KeyEvent.VK_ENTER);
            }
        });
        add(spinner, BorderLayout.CENTER);
        setOpaque(false);
        setBackground(AwareDefaults.DIRTY);
    }

    public N getValue() {
        return (N) model.getNumber();
    }

    public void setValue(Number n) {
        model.setValue(n);
    }

    @Override
    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    public void addChangeListener(ChangeListener listener) {
        spinner.addChangeListener(listener);
    }

    private Number num(N number, int basenum) {
        if (number instanceof Byte)
            return (byte) basenum;
        else if (number instanceof Short)
            return (short) basenum;
        else if (number instanceof Integer)
            return basenum;
        else if (number instanceof Long)
            return (long) basenum;
        else if (number instanceof Float)
            return (float) basenum;
        else if (number instanceof Double)
            return (double) basenum;
        else
            return null;
    }
}
