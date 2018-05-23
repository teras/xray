/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.utils;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumToIterable {

    public static <T> Iterable<T> toIterable(Enumeration<T> enumeration) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        return enumeration.hasMoreElements();
                    }

                    @Override
                    public T next() {
                        return enumeration.nextElement();
                    }
                };
            }
        };
    }
}
