/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt;

import static com.panayotis.xray.props.PropertyRecognizer.getNameFromType;
import com.panayotis.xray.tree.PropertyTreeDecorator;
import java.awt.Component;
import java.awt.Container;
import com.panayotis.xray.tree.PropertyTreeModel;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JMenu;

/**
 *
 * @author teras
 */
class AWTModel implements PropertyTreeModel<Component> {

    static final Component ROOT = new AllWindows();

    @Override
    public Component getRoot() {
        return ROOT;
    }

    @Override
    public List<Component> getChildren(Component item) {
        if (item == ROOT) {
            List<Component> frames = new ArrayList<>();
            for (Window w : Window.getWindows())
                if (w instanceof com.panayotis.xray.XRayForm) {
                } else if (w.isVisible())
                    frames.add(w);
            return frames;
        } else if (item instanceof JMenu)
            return Arrays.asList(((JMenu) item).getMenuComponents());
        else if (item instanceof Container)
            return Arrays.asList(((Container) item).getComponents());
        return Collections.EMPTY_LIST;
    }

    @Override
    public PropertyTreeDecorator<Component> getTreeDecorator() {
        return new PropertyTreeDecorator<Component>() {
            @Override
            public String getName(Component item) {
                return getNameFromType(item.getClass(), "javax\\.swing\\.J", false);
            }
        };
    }

    @Override
    public boolean isRootVisible() {
        return false;
    }

}
