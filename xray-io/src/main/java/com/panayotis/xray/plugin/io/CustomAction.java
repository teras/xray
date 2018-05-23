/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.io;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author teras
 */
public interface CustomAction {

    default void setCustomAction(String action, int keyCode, int modifiers, Runnable todo) {
        if (action != null) {
            ((JComponent) this).getInputMap().put(KeyStroke.getKeyStroke(keyCode, modifiers), action);
            if (todo != null)
                ((JComponent) this).getActionMap().put(action, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        todo.run();
                    }
                });
        }
    }
}
