/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.visuals;

import java.awt.GridLayout;
import java.util.function.BiConsumer;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public class DoubleNumberPropertyVisuals extends JPanel {

    private final AwareSpinner num1;
    private final AwareSpinner num2;
    private final JLabel name;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public DoubleNumberPropertyVisuals() {
        name = new JLabel();
        num1 = new AwareSpinner(Integer.MIN_VALUE, Integer.MAX_VALUE);
        num2 = new AwareSpinner(Integer.MIN_VALUE, Integer.MAX_VALUE);

        JPanel numP = new JPanel(new GridLayout(1, 2));
        numP.add(num1);
        numP.add(num2);

        setLayout(new GridLayout(1, 2));
        add(name);
        add(numP);
    }

    public void setLabelName(String name) {
        this.name.setText(name);
    }

    public void update(Number v1, Number v2) {
        num1.setValue(v1);
        num2.setValue(v2);
    }

    @Override
    public void setEnabled(boolean enabled) {
        name.setEnabled(enabled);
        num1.setEnabled(enabled);
        num2.setEnabled(enabled);
    }

    public void addListener(BiConsumer<Number, Number> eventConsumer, boolean asDecimal) {
        num1.addChangeListener(e -> eventConsumer.accept(num1.getValue(), num2.getValue()));
        num2.addChangeListener(e -> eventConsumer.accept(num1.getValue(), num2.getValue()));
    }
}
