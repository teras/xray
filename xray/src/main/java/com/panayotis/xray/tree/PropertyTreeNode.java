/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.tree;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;

/**
 *
 * @author teras
 */
@SuppressWarnings({"UseSpecificCatch", "StaticNonFinalUsedInInitialization"})
public class PropertyTreeNode<T> implements TreeNode {

    private final T thisItem;
    private final PropertyTreeNode<T> parent;
    private final PropertyTreeModel<T> model;
    private List<? extends T> children;

    public PropertyTreeNode(PropertyTreeModel<T> model) {
        this(model, model.getRoot(), null);
    }

    private PropertyTreeNode(PropertyTreeModel<T> model, T thisItem, PropertyTreeNode<T> parentItem) {
        this.model = model;
        this.thisItem = thisItem;
        this.parent = parentItem;
    }

    @Override
    public int getIndex(TreeNode node) {
        if (thisItem == null)
            return -1;
        int idx = -1;
        for (T child : lazyChildren()) {
            idx++;
            if (child == node)
                return idx;
        }
        return -1;
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public boolean isLeaf() {
        if (thisItem == null)
            return true;
        return model.isLeaf(thisItem);
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return new PropertyTreeNode<>(model, lazyChildren().get(childIndex), this);
    }

    @Override
    public int getChildCount() {
        if (thisItem == null)
            return -1;
        return lazyChildren().size();
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public Enumeration children() {
        return new Enumeration() {
            int upTo = getChildCount();
            int idx = 0;

            @Override
            public boolean hasMoreElements() {
                return idx < upTo;
            }

            @Override
            public Object nextElement() {
                return getChildAt(idx++);
            }
        };
    }

    public T getItem() {
        return thisItem;
    }

    private List<? extends T> lazyChildren() {
        if (children == null) {
            children = model.getChildren(thisItem);
            if (children == null)
                children = Collections.EMPTY_LIST;
        }
        return children;
    }

}
