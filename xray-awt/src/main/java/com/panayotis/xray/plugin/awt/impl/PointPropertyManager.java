/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt.impl;

import com.panayotis.xray.props.commons.NumericPairPropertyManager;
import java.awt.Point;
import java.lang.reflect.Method;

public class PointPropertyManager extends NumericPairPropertyManager<Integer, Point> {

    public PointPropertyManager(Object instance, String name, Method setter, Method getter) {
        super(instance, name, setter, getter, Integer.MIN_VALUE, Integer.MAX_VALUE, (x, y) -> new Point(x, y), point -> new Integer[]{point.x, point.y});
    }

    @Override
    public Point defaultValue() {
        return new Point(0, 0);
    }

}
