/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt;

import com.panayotis.xray.plugin.awt.impl.ColorPropertyManager;
import com.panayotis.xray.plugin.awt.impl.DimensionPropertyManager;
import com.panayotis.xray.plugin.awt.impl.IconPropertyManager;
import com.panayotis.xray.plugin.awt.impl.ImagePropertyManager;
import com.panayotis.xray.plugin.awt.impl.PointPropertyManager;
import com.panayotis.xray.plugin.awt.impl.RectanglePropertyManager;
import com.panayotis.xray.props.PropertyManagerFactory;
import com.panayotis.xray.tree.PropertyTreePlugin;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.lang.instrument.Instrumentation;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.Timer;

/**
 *
 * @author teras
 */
public class AWTPlugin extends PropertyTreePlugin<Component> {

    private final FlashComponent flash = new FlashComponent();
    private final JToggleButton flashButton;
    private final Demodalizer demodalizer = new Demodalizer(this);

    public AWTPlugin() {
        super(new AWTModel(), "AWT", new PropertyManagerFactory());
        PropertyManagerFactory factory = getFactory();
        factory.register(Dimension.class, DimensionPropertyManager.class);
        factory.register(Point.class, PointPropertyManager.class);
        factory.register(Rectangle.class, RectanglePropertyManager.class);
        factory.register(Image.class, ImagePropertyManager.class);
        factory.register(Icon.class, IconPropertyManager.class);
        factory.register(Color.class, ColorPropertyManager.class);

        flashButton = new JToggleButton(new ImageIcon(getClass().getResource("/icons/flash.png")));
        flashButton.addActionListener(e -> flashComponent());
        flashButton.setFocusable(false);
        addToolbarButton(flashButton);

        if (Demodalizer.isValid()) {
            JToggleButton demodButton = new JToggleButton(new ImageIcon(getClass().getResource("/icons/lock.png")));
            demodButton.addActionListener(e -> demodalizer.setEnabled(((JToggleButton) e.getSource()).isSelected()));
            demodButton.setFocusable(false);
            demodButton.setSelectedIcon(new ImageIcon(getClass().getResource("/icons/unlock.png")));
            addToolbarButton(demodButton);
        }
    }

    private void flashComponent() {
        flashButton.setEnabled(false);
        if (flash.running) {
            reenableFlashButtonLater();
            return;
        }
        Component selected = getSelectedItem();
        Window window = getWindow(selected);
        if (window == null) {
            reenableFlashButtonLater();
            return;
        }
        Point loc;
        try {
            loc = selected.getLocationOnScreen();
        } catch (Exception ex) {
            reenableFlashButtonLater();
            return;
        }
        flash.running = true;
        flash.w = selected.getWidth();
        flash.h = selected.getHeight();
        Component oldPane;
        Timer t;
        if (window instanceof JFrame) {
            JFrame frame = (JFrame) window;
            flash.x = loc.x - frame.getContentPane().getLocationOnScreen().x;
            flash.y = loc.y - frame.getContentPane().getLocationOnScreen().y;
            oldPane = frame.getGlassPane();
            frame.setGlassPane(flash);
            t = new Timer(500, e -> {
                frame.setGlassPane(oldPane);
                reenableFlashButtonNow();
            });
        } else if (window instanceof JDialog) {
            JDialog dialog = (JDialog) window;
            flash.x = loc.x - dialog.getContentPane().getLocationOnScreen().x;
            flash.y = loc.y - dialog.getContentPane().getLocationOnScreen().y;
            oldPane = dialog.getGlassPane();
            dialog.setGlassPane(flash);
            t = new Timer(500, e -> {
                dialog.setGlassPane(oldPane);
                reenableFlashButtonNow();
            });
        } else {
            reenableFlashButtonLater();
            return;
        }
        flash.setVisible(true);
        t.setRepeats(false);
        t.start();
    }

    private void reenableFlashButtonLater() {
        Timer t = new Timer(100, e -> reenableFlashButtonNow());
        t.setRepeats(false);
        t.start();
    }

    private void reenableFlashButtonNow() {
        flashButton.setSelected(false);
        flashButton.setEnabled(true);
        flash.running = false;
    }

    @Override
    public void onInit(String agentArgs, Instrumentation inst) {
    }

    private class FlashComponent extends JComponent {

        private final Color color = new Color(1, 0, 0, 0.5f);
        int x, y, w, h;
        boolean running;

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(color);
            g.fillRect(x, y, w, h);
        }
    }

    static Window getWindow(Component c) {
        while (c != null && !(c instanceof Window))
            c = c.getParent();
        return (Window) c;
    }
}
