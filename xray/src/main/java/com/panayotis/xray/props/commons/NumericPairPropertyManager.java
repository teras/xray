/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import com.panayotis.xray.props.visuals.DoubleNumberPropertyVisuals;
import com.panayotis.xray.props.PropertyManager;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.swing.JComponent;

/**
 *
 * @author teras
 * @param <N>
 * @param <T>
 */
public abstract class NumericPairPropertyManager<N extends Number & Comparable<N>, T> extends PropertyManager<T> {

    private final DoubleNumberPropertyVisuals<N> visuals;
    private final Function<T, N[]> fromData;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public NumericPairPropertyManager(Object instance, String name, Method setter, Method getter, N min, N max, BiFunction<N, N, T> toData, Function<T, N[]> fromData) {
        super(instance, name, setter, getter);
        visuals = new DoubleNumberPropertyVisuals<>(min, max);
        visuals.setEnabled(!isReadOnly());
        visuals.addListener((x, y) -> setValue(toData.apply(x, y)));
        visuals.setLabelName(name);
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
            visuals.update(val[0], val[1]);
        }
    }

}
