/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import java.awt.BorderLayout;
import java.awt.Font;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public class PropertyManagerFactory {

    private final PropertyResolver resolv;
    private final PropertyRecognizer recogn;

    public PropertyManagerFactory() {
        this(null, null);
    }

    public PropertyManagerFactory(PropertyRecognizer recogn) {
        this(null, recogn);
    }

    public PropertyManagerFactory(PropertyResolver resolv) {
        this(resolv, null);
    }

    public PropertyManagerFactory(PropertyResolver resolv, PropertyRecognizer recogn) {
        if (resolv == null)
            resolv = new PropertyResolver();
        if (recogn == null)
            recogn = new BeanPropertyRecognizer();
        this.resolv = resolv;
        this.recogn = recogn;
    }

    public <T> void register(Class<T> type, PropertyGenerator<T> generator) {
        resolv.register(type, generator);
    }

    public JComponent getView(Object instance) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel titlePanel = new ConstrainedPanel(new BorderLayout());
        JLabel title = new JLabel(instance.getClass().getName());
        JLabel hash = new JLabel("@" + Integer.toHexString(instance.hashCode()));
        hash.setHorizontalAlignment(JLabel.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD));
        titlePanel.add(title, BorderLayout.WEST);
        titlePanel.add(hash, BorderLayout.CENTER);
        panel.add(titlePanel);

        for (PropertyManager p : retrieveList(instance)) {
            JPanel constraint = new ConstrainedPanel(new BorderLayout());
            constraint.add(p.getView(), BorderLayout.CENTER);
            panel.add(constraint);
        }
        return panel;
    }

    private List<PropertyManager> retrieveList(Object instance) {
        List<PropertyManager> props = new ArrayList<>();
        Map<String, PropMethods> properties = new TreeMap<>();
        Class cls = instance.getClass();
        for (Method m : cls.getMethods())
            if (recogn.isSetter(m))
                getValue(properties, recogn.getNameFromSetter(m), () -> new PropMethods()).setter = m;
            else if (recogn.isGetter(m))
                getValue(properties, recogn.getNameFromGetter(m), () -> new PropMethods()).getter = m;
        for (PropMethods methods : properties.values())
            if (methods.getter != null)
                props.add(resolv.resolve(instance, recogn.getNameFromGetter(methods.getter), methods.setter, methods.getter));
        return props;
    }

    static class PropMethods {

        private Method getter;
        private Method setter;

    }

    static <K, V> V getValue(Map<K, V> map, K key, Supplier<V> keyConstructor) {
        V val = map.get(key);
        if (val == null) {
            val = keyConstructor.get();
            map.put(key, val);
        }
        return val;
    }
}
