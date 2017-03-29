/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin;

import com.panayotis.xray.sys.EIterable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 *
 * @author teras
 */
public class PluginManager {

    private static final String PLUGIN_LOC = "META-INF/xray-plugins/";
    private static Collection<XRayPlugin> plugins;

    @SuppressWarnings("UseSpecificCatch")
    public static Collection<XRayPlugin> plugins() {
        if (plugins == null) {
            plugins = new TreeSet<>((o1, o2) -> o1.getPluginOrder() - o2.getPluginOrder());
            for (URL url : ((URLClassLoader) PluginManager.class.getClassLoader()).getURLs())
                try {
                    readPlugins(new File(Paths.get(url.toURI()).toString()), plugins);
                } catch (Exception ex) {
                }
        }
        return plugins;
    }

    private static void readPlugins(File path, Collection<XRayPlugin> plugins) throws IOException {
        if (path.isDirectory()) {
            path = new File(path, PLUGIN_LOC);
            if (!path.exists() || !path.isDirectory())
                return;
            for (File possible : path.listFiles())
                if (possible.isFile())
                    loadPlugin(possible.getName(), plugins);
        } else if (path.isFile()) {
            JarFile jar = new JarFile(path);
            for (JarEntry entry : new EIterable<>(jar.entries()))
                if (entry.getName().startsWith(PLUGIN_LOC))
                    loadPlugin(entry.getName().substring(PLUGIN_LOC.length()), plugins);
        }
    }

    @SuppressWarnings("UseSpecificCatch")
    private static void loadPlugin(String name, Collection<XRayPlugin> plugins) {
        if (name == null || name.isEmpty())
            return;
        try {
            plugins.add((XRayPlugin) Class.forName(name).newInstance());
        } catch (Exception ex) {
            Logger.error("Unable to instantiate plugin '" + name + "'");
            Logger.stackTrace(ex);
        }
    }
}
