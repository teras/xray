/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray;

import com.panayotis.xray.plugin.PluginManager;
import com.panayotis.xray.plugin.XRayPlugin;
import com.panayotis.xray.utils.Logger;
import com.panayotis.xray.utils.Profiling;
import java.lang.instrument.Instrumentation;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class XRay {

    public static void main(String[] args) {
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        Logger.init();
        for (XRayPlugin plugin : PluginManager.plugins())
            plugin.onInit(agentArgs, inst);
        SwingUtilities.invokeLater(() -> postInit());

    }

    private static void postInit() {
        Profiling.setFeedback(System.out::println);
        XRayWindow form = new XRayWindow();
        form.setVisible(true);
    }

}
