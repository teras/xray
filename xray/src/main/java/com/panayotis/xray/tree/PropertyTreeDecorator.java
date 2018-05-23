/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.tree;

import static com.panayotis.xray.props.PropertyRecognizer.getNameFromType;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public interface PropertyTreeDecorator<T> {

    default String getName(T item) {
        return getNameFromType(item.getClass(), null, true);
    }

    default Icon getIcon(T item) {
        return null;
    }

    default TreeCellRenderer getRenderer() {
        return new DefaultTreeCellRenderer() {

            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component result = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                T item = ((PropertyTreeNode<T>) value).getItem();

                Icon icon = item != null ? PropertyTreeDecorator.this.getIcon(item) : null;
                setIconTextGap(1);
                setIcon(icon == null ? leafIcon : icon);
                setText(PropertyTreeDecorator.this.getName(item));
                return result;
            }
        };
    }
}
