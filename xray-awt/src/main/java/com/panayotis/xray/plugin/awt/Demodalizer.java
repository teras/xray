/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.awt;

import static com.panayotis.xray.plugin.awt.AWTPlugin.getWindow;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.swing.Timer;

/**
 *
 * @author teras
 */
@SuppressWarnings({"StaticNonFinalUsedInInitialization", "UseSpecificCatch"})
public final class Demodalizer extends Timer {

    private static Method unblock;
    private static Method isBlocked;
    private static Method removeFilter;
    private static Field modalFilter;

    private boolean firstTime = true;

    static {
        try {
            unblock = Dialog.class.getDeclaredMethod("unblockWindow", Window.class);
            isBlocked = Window.class.getDeclaredMethod("isModalBlocked", (Class[]) null);
            modalFilter = Dialog.class.getDeclaredField("modalFilter");
            removeFilter = Class.forName("java.awt.EventDispatchThread").getDeclaredMethod("removeEventFilter", Class.forName("java.awt.EventFilter"));
            unblock.setAccessible(true);
            isBlocked.setAccessible(true);
            modalFilter.setAccessible(true);
            removeFilter.setAccessible(true);
        } catch (Exception ex) {
        }
    }

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public Demodalizer(Component reference) {
        super(1000, new ActionListener() {
            private Window self;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (self == null) {
                    self = getWindow(reference);
                    if (self == null)
                        return;
                }
                Window[] frames = Frame.getWindows();
                for (Window w : frames)
                    try {
                        if (w.isVisible() && w instanceof Dialog && ((Dialog) w).isModal() && (Boolean) isBlocked.invoke(self, (Object[]) null)) {
                            unblock.invoke(w, self);
                            removeFilter.invoke(Thread.currentThread(), modalFilter.get(w));
                        }
                    } catch (Exception ex) {
                    }
            }
        });
        setInitialDelay(0);
    }

    public static boolean isValid() {
        return removeFilter != null;
    }

    public synchronized void setEnabled(boolean enable) {
        if (enable == isRunning())
            return;
        if (enable)
            if (firstTime) {
                firstTime = false;
                start();
            } else
                restart();
        else
            stop();
    }
}
