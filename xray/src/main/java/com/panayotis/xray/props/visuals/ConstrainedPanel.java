/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.props.visuals;

import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public class ConstrainedPanel extends JPanel {

    public ConstrainedPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public ConstrainedPanel(LayoutManager layout) {
        super(layout);
    }

    public ConstrainedPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public ConstrainedPanel() {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, super.getPreferredSize().height);
    }
}
