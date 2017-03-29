/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.sys;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import static java.lang.reflect.Modifier.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author teras
 */
public class MainResolver {

    // DO NOT USE - IT MIGHT BREAK EXECUTION DUE TO EARLY STATIC INITIALIZATION!
    // Probably we should use something like asm, but this is already used in an
    // old version for bsh and thus it breaks
    public static Method retrieveMain(String[] cp) {
        Collection<Method> possible = new HashSet<>();
        for (String pathname : cp) {
            File path = new File(pathname);
            if (path.isFile())
                try {
                    JarFile jar = new JarFile(path);
                    for (JarEntry entry : new EIterable<>(jar.entries())) {
                        String name = entry.getName();
                        if (name.toLowerCase().endsWith(".class"))
                            checkClass(name.substring(0, name.length() - 6).replace('/', '.'), possible);
                    }
                } catch (IOException ex) {
                }
            else if (path.isDirectory())
                iterateFiles(path, "", possible);
        }
        System.out.println(possible);
        switch (possible.size()) {
            case 1:
                return possible.iterator().next();
            case 0:
                return null;
            default:
                return null;
        }
    }

    private static void iterateFiles(File root, String packg, Collection<Method> possible) {
        File[] files = root.listFiles();
        if (files == null || files.length == 0)
            return;
        for (File f : files) {
            String name = f.getName();
            if (f.isDirectory())
                iterateFiles(f, packg.isEmpty() ? name : packg + "." + name, possible);
            else if (f.isFile())
                if (name.toLowerCase().endsWith(".class"))
                    checkClass(packg + (packg.isEmpty() ? "" : ".") + name.substring(0, name.length() - 6).replace('/', '.').replace('\\', '.'), possible);
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private static void checkClass(String name, Collection<Method> mainFound) {
        try {
            Method m = Class.forName(name).getMethod("main", String[].class);
            int modifiers = m.getModifiers();
            if (isPublic(modifiers) && isStatic(modifiers) && m.getReturnType().equals(void.class))
                mainFound.add(m);
        } catch (Throwable ex) {
        }
    }

    public static void getMainFromJar(String filename, CommandArgs args) {
        Attributes attrs = null;
        try {
            attrs = new JarFile(filename).getManifest().getMainAttributes();
        } catch (IOException ex) {
            System.err.println("Unable to locate file " + filename);
            return;
        }
        for (Iterator it = attrs.keySet().iterator(); it.hasNext();) {
            Attributes.Name attname = (Attributes.Name) it.next();
            String name = attname.toString().toLowerCase().trim();
            if (name.equals("main-class"))
                args.setMain(attrs.getValue(attname));
            else if (name.equals("class-path"))
                args.appendClasspath(attrs.getValue(attname).trim().replaceAll("( )+", " ").replace(' ', File.pathSeparatorChar));
        }
    }

}
