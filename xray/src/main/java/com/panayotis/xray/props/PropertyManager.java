/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import java.lang.reflect.Method;
import javax.swing.JComponent;

public abstract class PropertyManager<T> {

    private final String name;
    private final Object instance;
    private final Method setter;
    private final Method getter;
    private JComponent view;
    private boolean setValueLock = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public PropertyManager(Object instance, String name, Method setter, Method getter) {
        this.instance = instance;
        this.name = name;
        this.setter = setter;
        this.getter = getter;
    }

    public final JComponent getView() {
        if (view == null) {
            view = createView();
            setValueLock = true;
            updateView(getValue());
            setValueLock = false;
        }
        return view;
    }

    @Override
    @SuppressWarnings("UseSpecificCatch")
    public final String toString() {
        try {
            T result = getValue();
            if (result != null)
                return result.toString();
        } catch (Exception ex) {
        }
        return null;
    }

    @SuppressWarnings("UseSpecificCatch")
    protected final boolean setValue(T newValue) {
        if (setValueLock)
            return true;
        setValueLock = true;
        if (setter != null)
            try {
                setter.invoke(instance, newValue);
                return true;
            } catch (Exception ex) {
            } finally {
                updateView(getValue());
                setValueLock = false;
            }
        return false;
    }

    @SuppressWarnings("UseSpecificCatch")
    protected final T getValue() {
        try {
            return (T) getter.invoke(instance, (Object[]) null);
        } catch (Exception ex) {
            return defaultValue();
        }
    }

    protected final boolean isReadOnly() {
        return setter == null;
    }

    protected final String getName() {
        return name;
    }

    protected abstract JComponent createView();

    public abstract void updateView(T value);

    protected abstract T defaultValue();
}
