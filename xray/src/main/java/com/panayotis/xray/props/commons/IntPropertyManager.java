/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.lang.reflect.Method;

public class IntPropertyManager extends GenericPropertyManager<Integer> {

    public IntPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected Integer convertValue(Object value) {
        try {
            if (value != null)
                return Integer.valueOf(value.toString());
        } catch (NumberFormatException ex) {
        }
        return null;
    }

    @Override
    public Integer defaultValue() {
        return 0;
    }

}
