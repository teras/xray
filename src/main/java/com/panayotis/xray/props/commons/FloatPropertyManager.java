/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.lang.reflect.Method;

public class FloatPropertyManager extends DefaultPropertyManager<Float> {

    public FloatPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected Float convertValue(Object value) {
        try {
            if (value != null)
                return Float.valueOf(value.toString());
        } catch (NumberFormatException ex) {
        }
        return null;
    }

    @Override
    public Float defaultValue() {
        return 0f;
    }

}
