/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.lang.reflect.Method;

public class DoublePropertyManager extends GenericPropertyManager<Double> {

    public DoublePropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected Double convertValue(Object value) {
        try {
            if (value != null)
                return Double.valueOf(value.toString());
        } catch (NumberFormatException ex) {
        }
        return null;
    }

    @Override
    public Double defaultValue() {
        return 0d;
    }

}
