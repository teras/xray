/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props;

import java.lang.reflect.Method;

public interface PropertyGenerator<T> {

    public PropertyManager<T> construct(Object instance, String name, Method setter, Method getter);
}
