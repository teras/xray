/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author teras
 */
public class AwareTextField extends JPanel {

    private final JTextField field;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public AwareTextField() {
        super(new BorderLayout());
        field = new JTextField();
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                setOpaque(e.getKeyCode() != KeyEvent.VK_ENTER);
            }
        });
        add(field, BorderLayout.CENTER);
        setOpaque(false);
        setBackground(AwareDefaults.DIRTY);
    }

    public String getText() {
        return field.getText();
    }

    public void setText(String text) {
        field.setText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        field.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    public void addActionListener(ActionListener listener) {
        field.addActionListener(listener);
    }

}
