/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.sys;

import com.panayotis.xray.plugin.Logger;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author teras
 */
public class ClassPathManager {

    private static URLClassLoader loader;

    public static void execute(String mainClass) {
        try {
            Class.forName(mainClass).getMethod("main", String[].class).invoke(null, (Object) new String[]{});
        } catch (ClassNotFoundException ex) {
            Logger.error("Unable to locate main class " + mainClass + "\n" + ex.toString());
            Logger.stackTrace(ex);
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.error("Unable to locate main method in class " + mainClass + "\n" + ex.toString());
            Logger.stackTrace(ex);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.error("Unable to call main method in class " + mainClass + "\n" + ex.toString());
            Logger.stackTrace(ex);
        }
    }

    public static void initialize(String... classpath) {
        initialize(files(classpath));
    }

    @SuppressWarnings("UseSpecificCatch")
    public static void initialize(File... classpath) {
        Method addURL;
        try {
            loader = (URLClassLoader) ClassPathManager.class.getClassLoader();
            addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURL.setAccessible(true);
        } catch (Exception ex) {
            Logger.error("JVM inconsistency: unable to touch URLClassLoader");
            return;
        }
        StringBuilder classPathProp = new StringBuilder();
        for (File f : classpath)
            try {
                addURL.invoke(loader, f.toURI().toURL());
                classPathProp.append(File.pathSeparatorChar).append(f.getAbsolutePath());
            } catch (Exception ex) {
            }
        System.setProperty("java.class.path", classPathProp.substring(1));
    }

    private static File[] files(String[] classpath) {
        Collection<File> result = new ArrayList<>();
        if (classpath != null)
            for (String file : classpath)
                result.add(new File(file));
        return result.toArray(new File[result.size()]);
    }
}
