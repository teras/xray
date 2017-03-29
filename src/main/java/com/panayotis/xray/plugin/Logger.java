/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin;

import com.panayotis.xray.XRayForm;
import java.io.PrintStream;
import javax.swing.JOptionPane;

/**
 *
 * @author teras
 */
public class Logger {

    private static final PrintStream out = System.out;
    private static final PrintStream err = System.err;
    private static XRayForm target;
    private static boolean commandBased;

    public static void init(boolean commandBased, XRayForm form) {
        Logger.target = form;
        Logger.commandBased = commandBased;
    }

    public static void stackTrace(Throwable th) {
        th.printStackTrace(err);
    }

    public static void error(Object error) {
        if (error != null)
            if (commandBased)
                JOptionPane.showMessageDialog(target, error, "XRay Error", JOptionPane.ERROR_MESSAGE);
            else
                err.println(error);
    }

    public static void info(Object info) {
        if (info != null)
            if (commandBased)
                JOptionPane.showMessageDialog(target, info, "XRay Info", JOptionPane.INFORMATION_MESSAGE);
            else
                out.println(info);
    }

    public static void debug(Object debug) {
        if (debug != null)
            out.println(debug);
    }

    public static void err(String txt) {
        err.print(txt);
    }

    public static void out(String txt) {
        out.print(txt);
    }
}
