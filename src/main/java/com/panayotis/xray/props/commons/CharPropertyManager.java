/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.commons;

import java.lang.reflect.Method;

public class CharPropertyManager extends DefaultPropertyManager<Character> {

    public CharPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter);
    }

    @Override
    protected Character convertValue(Object value) {
        String v = value == null ? " " : value.toString();
        return v.length() < 1 ? ' ' : v.charAt(0);
    }

    @Override
    public Character defaultValue() {
        return ' ';
    }

}
