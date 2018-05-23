/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.lang.reflect.Method;

public class BytePropertyManager extends GenericPropertyManager<Byte> {

    public BytePropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected Byte convertValue(Object value) {
        try {
            if (value != null)
                return Byte.valueOf(value.toString());
        } catch (NumberFormatException ex) {
        }
        return null;
    }

    @Override
    public Byte defaultValue() {
        return 0;
    }

}
