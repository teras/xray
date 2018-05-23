/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.lang.reflect.Method;

public class StringPropertyManager extends GenericPropertyManager<String> {

    public StringPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected String convertValue(Object value) {
        return value == null ? null : value.toString();
    }

    @Override
    public String defaultValue() {
        return "";
    }

}
