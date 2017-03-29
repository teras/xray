/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import com.panayotis.xray.plugin.XRayPlugin;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author teras
 */
public class PropertyPlugin implements XRayPlugin {

    private final String name;
    private final Object target;
    private final int order;
    private final JComponent comp;

    public PropertyPlugin(Object target) {
        this(target, new PropertyManagerFactory());
    }

    public PropertyPlugin(Object target, PropertyManagerFactory factory) {
        this(target, getNameFromClass(target.getClass()), 500, factory);
    }

    public PropertyPlugin(Object target, String name, int pluginOrder) {
        this(target, name, pluginOrder, new PropertyManagerFactory());
    }

    public PropertyPlugin(Object target, String name, int pluginOrder, PropertyManagerFactory factory) {
        this.target = target;
        this.name = name;
        this.order = pluginOrder;
        comp = target == null
            ? new JLabel("Null object")
            : factory.getView(target);
    }

    @Override
    public String getPluginName() {
        return name;
    }

    @Override
    public int getPluginOrder() {
        return order;
    }

    @Override
    public JComponent getPluginVisuals() {
        return comp;
    }

    public static String getNameFromClass(Object target) {
        if (target == null)
            return "-";
        String name = target.getClass().getName();
        int point = name.lastIndexOf('.');
        return point >= 0
            ? name.substring(point + 1)
            : name;
    }

}
