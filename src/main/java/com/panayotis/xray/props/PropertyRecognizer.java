/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import java.lang.reflect.Method;

/**
 *
 * @author teras
 */
public interface PropertyRecognizer {

    public boolean isGetter(Method m);

    public boolean isSetter(Method m);

    public String getNameFromGetter(Method m);

    public String getNameFromSetter(Method m);

    public static String getNameFromType(Class cls, String regexToRemove, boolean firstRemovePackage) {
        String name = cls.getName();
        if (firstRemovePackage)
            name = removePackageName(name);
        if (regexToRemove != null && !regexToRemove.isEmpty())
            name = name.replaceAll(regexToRemove, "");
        if (!firstRemovePackage)
            name = removePackageName(name);
        return getDefaultCapitalizedName(name);
    }

    public static String removePackageName(String name) {
        int point = name.lastIndexOf('.');
        return point >= 0 ? name.substring(point + 1) : name;
    }

    public static String getDefaultCapitalizedName(String name) {
        if (name == null)
            return null;
        if (name.length() > 1)
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        else
            name = name.toUpperCase();
        String[] parts = name.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])");
        StringBuilder out = new StringBuilder();
        for (String part : parts)
            out.append(' ').append(part);
        return out.length() <= 1 ? out.toString() : out.substring(1);
    }
}
