/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.io;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

/**
 *
 * @author teras
 */
public class JAutoCompleteTextField extends JTextField implements CustomAction {

    private final History history = new History();

    public JAutoCompleteTextField() {
        init();
    }

    public JAutoCompleteTextField(String text) {
        super(text);
        init();
    }

    public JAutoCompleteTextField(int columns) {
        super(columns);
        init();
    }

    public JAutoCompleteTextField(String text, int columns) {
        super(text, columns);
        init();
    }

    public JAutoCompleteTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        init();
    }

    private void init() {
        setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.EMPTY_SET);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String prev;
                if (e.getModifiers() != KeyEvent.ACTION_EVENT_MASK)
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            prev = history.previous();
                            if (prev != null)
                                setText(prev);
                            break;
                        case KeyEvent.VK_DOWN:
                            prev = history.next();
                            if (prev != null)
                                setText(prev);
                            break;
                        case KeyEvent.VK_TAB:
                            System.out.println("Auto Complete");
                            break;
                        case KeyEvent.VK_ENTER:
                            if (!getText().isEmpty()) {
                                history.send();
                                setText("");
                            }
                            break;
                        default:
                            history.update(getText());
                    }
            }
        });
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "*none*");
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "*none*");
    }

}
