/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import java.lang.reflect.Method;
import static com.panayotis.xray.props.PropertyRecognizer.getDefaultCapitalizedName;

/**
 *
 * @author teras
 */
public class BeanPropertyRecognizer implements PropertyRecognizer {

    @Override
    public boolean isGetter(Method m) {
        String name = m.getName();
        return m.getParameterCount() == 0 && !m.getReturnType().equals(void.class)
            && Character.isUpperCase(name.charAt(3))
            && ((name.startsWith("is") && (m.getReturnType().equals(boolean.class) || m.getReturnType().equals(Boolean.class)))
            || (name.startsWith("get") && !(m.getReturnType().equals(boolean.class) || m.getReturnType().equals(Boolean.class))));
    }

    @Override
    public boolean isSetter(Method m) {
        String name = m.getName();
        return name.length() > 3 && name.startsWith("set") && Character.isUpperCase(name.charAt(3))
            && m.getReturnType().equals(void.class) && m.getParameterCount() == 1;
    }

    @Override
    public String getNameFromGetter(Method m) {
        return m.getReturnType().equals(boolean.class) || m.getReturnType().equals(Boolean.class)
            ? getDefaultCapitalizedName(m.getName().substring(2))
            : getDefaultCapitalizedName(m.getName().substring(3));
    }

    @Override
    public String getNameFromSetter(Method m) {
        return getDefaultCapitalizedName(m.getName().substring(3));
    }

}
