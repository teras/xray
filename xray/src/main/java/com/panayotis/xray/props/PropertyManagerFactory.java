/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import com.panayotis.xray.props.visuals.ConstrainedPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
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
    private final Map<Class, Map<String, PropMethod>> registry = new HashMap<>();

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

        Map<String, PropMethod> methods = retrieveList(instance);
        for (String prop : methods.keySet()) {
            PropMethod method = methods.get(prop);
            PropertyManager pman = resolv.resolve(instance, recogn.getNameFromGetter(method.getter), method.setter, method.getter);
            JPanel constraint = new ConstrainedPanel(new BorderLayout());
            constraint.add(pman.getView(), BorderLayout.CENTER);
            panel.add(constraint);
        }
        return panel;
    }

    private Map<String, PropMethod> retrieveList(Object instance) {
        Class cls = instance.getClass();
        Map<String, PropMethod> objmap = registry.get(cls);
        if (objmap == null) {
            registry.put(cls, objmap = new TreeMap<>());
            for (Method m : cls.getMethods())
                if (recogn.isSetter(m))
                    getValue(objmap, recogn.getNameFromSetter(m), () -> new PropMethod()).setter = m;
                else if (recogn.isGetter(m))
                    getValue(objmap, recogn.getNameFromGetter(m), () -> new PropMethod()).getter = m;

            // Remove setter-only properties (should be invalid!)
            Iterator<String> props = objmap.keySet().iterator();
            while (props.hasNext()) {
                String prop = props.next();
                PropMethod method = objmap.get(prop);
                if (method.getter == null)
                    props.remove();
            }
        }
        return objmap;
    }

    static class PropMethod {

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
