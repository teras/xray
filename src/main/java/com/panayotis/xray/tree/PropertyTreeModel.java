/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.tree;

import java.util.List;

public interface PropertyTreeModel<T> {

    public abstract T getRoot();

    public List<T> getChildren(T item);

    default boolean isRootVisible() {
        return true;
    }

    default boolean isLeaf(T item) {
        return getChildren(item).isEmpty();
    }

    default PropertyTreeDecorator<T> getTreeDecorator() {
        return new PropertyTreeDecorator<T>() {
        };
    }

}
