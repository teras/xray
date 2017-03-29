/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray;

import com.panayotis.xray.plugin.Logger;
import com.panayotis.xray.sys.ClassPathManager;
import com.panayotis.xray.sys.CommandArgs;
import javax.swing.SwingUtilities;

/**
 *
 * @author teras
 */
public class XRay {

    public static void main(String[] args) {
        XRayForm form = new XRayForm();
        CommandArgs cargs = new CommandArgs(args);
        if (cargs.getMainClass().isEmpty())
            Logger.init(true, form);
        else {
            Logger.init(false, form);
            ClassPathManager.initialize(cargs.getClasspath());
        }
        form.preInit();
        if (!cargs.getMainClass().isEmpty())
            ClassPathManager.execute(cargs.getMainClass());
        SwingUtilities.invokeLater(() -> form.postInit());
    }
}
