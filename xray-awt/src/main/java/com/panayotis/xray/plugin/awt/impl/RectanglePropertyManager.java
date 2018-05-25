/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.commons.NumericQuadPropertyManager;
import java.awt.Rectangle;
import java.lang.reflect.Method;

public class RectanglePropertyManager extends NumericQuadPropertyManager<Integer, Rectangle> {


    public RectanglePropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter,
                Integer.MIN_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE, "Location", "Size",
                (x, y, width, height)->new Rectangle(x, y, width, height), 
                rect -> new Integer[]{rect.x, rect.y, rect.width, rect.height});
    }

    @Override
    public Rectangle defaultValue() {
        return new Rectangle(0, 0, 0, 0);
    }

}
