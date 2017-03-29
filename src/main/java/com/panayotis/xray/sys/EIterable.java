/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.sys;

import java.util.Enumeration;
import java.util.Iterator;

/**
 *
 * @author teras
 */
public class EIterable<T> implements Iterable<T>, Iterator<T> {

    private final Enumeration<T> enumeration;

    public EIterable(Enumeration<T> enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public T next() {
        return enumeration.nextElement();
    }

}
