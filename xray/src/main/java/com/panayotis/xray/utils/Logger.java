/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.utils;

import java.io.PrintStream;

/**
 *
 * @author teras
 */
public class Logger {

    private static final PrintStream out = System.out;
    private static final PrintStream err = System.err;

    public static void init() {
    }

    public static void stackTrace(Throwable th) {
        th.printStackTrace(err);
    }

    public static void error(Object error) {
        if (error != null)
            err.println(error);
    }

    public static void info(Object info) {
        if (info != null)
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
