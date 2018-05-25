/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.commons.NumericPairPropertyManager;
import java.awt.Dimension;
import java.lang.reflect.Method;

public class DimensionPropertyManager extends NumericPairPropertyManager<Integer, Dimension> {

    public DimensionPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter, 0, Integer.MAX_VALUE, (w, h) -> new Dimension(w, h), dim -> new Integer[]{dim.width, dim.height});
    }

    @Override
    public Dimension defaultValue() {
        return new Dimension(0, 0);
    }

}
