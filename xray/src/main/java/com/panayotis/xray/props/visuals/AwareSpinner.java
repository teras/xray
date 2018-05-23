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
 */
public class AwareSpinner extends JPanel {

    private final JSpinner spinner;
    private final SpinnerNumberModel model;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AwareSpinner(int min, int max) {
        super(new BorderLayout());
        spinner = new JSpinner(model = new SpinnerNumberModel(0, min, max, 1));
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

    public int getValue() {
        return model.getNumber().intValue();
    }

    public void setValue(Number n) {
        model.setValue(n.intValue());
    }

    @Override
    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    public void addChangeListener(ChangeListener listener) {
        spinner.addChangeListener(listener);
    }

}
