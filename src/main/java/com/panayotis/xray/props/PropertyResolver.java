/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import static com.panayotis.xray.props.PropertyManagerFactory.getValue;
import com.panayotis.xray.props.commons.BooleanPropertyManager;
import com.panayotis.xray.props.commons.BytePropertyManager;
import com.panayotis.xray.props.commons.CharPropertyManager;
import com.panayotis.xray.props.commons.DoublePropertyManager;
import com.panayotis.xray.props.commons.EnumPropertyManager;
import com.panayotis.xray.props.commons.FloatPropertyManager;
import com.panayotis.xray.props.commons.IntPropertyManager;
import com.panayotis.xray.props.commons.LongPropertyManager;
import com.panayotis.xray.props.commons.ObjectPropertyManager;
import com.panayotis.xray.props.commons.ShortPropertyManager;
import com.panayotis.xray.props.commons.StringPropertyManager;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author teras
 */
public class PropertyResolver {

    private final Map<Class, PropertyGenerator> mapping = new HashMap<>();

    public PropertyResolver() {
    }

    public <T> void register(Class<T> type, PropertyGenerator<T> manager) {
        getValue(mapping, type, () -> manager);
    }

    PropertyManager resolve(Object instance, String name, Method setter, Method getter) {
        Class<?> type = getter.getReturnType();
        PropertyManager prop = null;
        PropertyGenerator generator = mapping.get(type);
        if (generator != null)
            prop = generator.construct(instance, name, setter, getter);
        return prop == null
            ? resolveBasedOnNativeType(instance, type, name, setter, getter)
            : prop;
    }

    private PropertyManager resolveBasedOnNativeType(Object instance, Class type, String name, Method setter, Method getter) {
        switch (type.getName()) {
            case "byte":
                return new BytePropertyManager(instance, name, setter, getter);
            case "short":
                return new ShortPropertyManager(instance, name, setter, getter);
            case "int":
                return new IntPropertyManager(instance, name, setter, getter);
            case "long":
                return new LongPropertyManager(instance, name, setter, getter);
            case "float":
                return new FloatPropertyManager(instance, name, setter, getter);
            case "double":
                return new DoublePropertyManager(instance, name, setter, getter);
            case "boolean":
                return new BooleanPropertyManager(instance, name, setter, getter);
            case "char":
                return new CharPropertyManager(instance, name, setter, getter);
            case "java.lang.String":
                return new StringPropertyManager(instance, name, setter, getter);
            default:
                if (type.isEnum())
                    return new EnumPropertyManager(instance, name, setter, getter);
                else
                    return new ObjectPropertyManager(instance, name, setter, getter);
        }
    }

}
