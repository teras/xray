/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import com.panayotis.xray.props.PropertyManager;
import com.panayotis.xray.props.visuals.DoubleNumberPropertyVisuals;
import com.panayotis.xray.utils.QuadFunction;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.lang.reflect.Method;
import java.util.function.Function;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author teras
 * @param <N>
 * @param <T>
 */
public abstract class NumericQuadPropertyManager<N extends Number & Comparable<N>, T> extends PropertyManager<T> {

    private final DoubleNumberPropertyVisuals<N> top;
    private final DoubleNumberPropertyVisuals<N> bottom;
    private final JPanel visuals;
    private final Function<T, N[]> fromData;
    private N topA, topB, bottomC, bottomD;

    public NumericQuadPropertyManager(Object instance, String name, Method setter, Method getter,
            N minTop, N maxTop, N minBottom, N maxBottom,
            String topLabel, String bottomLabel,
            QuadFunction<N, N, N, N, T> toData,
            Function<T, N[]> fromData) {
        super(instance, name, setter, getter);
        topA = minTop;
        topB = maxTop;
        bottomC = minBottom;
        bottomD = maxBottom;

        visuals = new JPanel(new BorderLayout());

        JLabel nameL = new JLabel(name);
        top = new DoubleNumberPropertyVisuals<>(minTop, maxTop);
        bottom = new DoubleNumberPropertyVisuals<>(minBottom, maxBottom);
        if (isReadOnly()) {
            nameL.setEnabled(false);
            top.setEnabled(false);
            bottom.setEnabled(false);
        }
        top.addListener((a, b) -> setValue(toData.apply(topA = a, topB = b, bottomC, bottomD)));
        bottom.addListener((c, d) -> setValue(toData.apply(topA, topB, bottomC = c, bottomD = d)));
        if (topLabel != null)
            top.setLabelName("  " + topLabel);
        if (bottomLabel != null)
            bottom.setLabelName("  " + bottomLabel);

        visuals.add(nameL, BorderLayout.NORTH);
        JPanel spinners = new JPanel(new GridLayout(2, 1));
        spinners.add(top);
        spinners.add(bottom);
        visuals.add(spinners, BorderLayout.CENTER);
        this.fromData = fromData;
    }

    @Override
    protected JComponent createView() {
        return visuals;
    }

    @Override
    public void updateView(T item) {
        if (item != null) {
            N[] val = fromData.apply(item);
            top.update(topA = val[0], topB = val[1]);
            bottom.update(bottomC = val[2], bottomD = val[3]);
        }
    }

}
