/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin;

import java.lang.instrument.Instrumentation;
import javax.swing.JComponent;

/**
 *
 * @author teras
 */
public interface XRayPlugin {

    public void onInit(String agentArgs, Instrumentation inst);

    public String getPluginName();

    public JComponent getPluginVisuals();

    public int getPluginOrder();

    default void onFocus() {
    }
}
