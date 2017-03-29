/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.sys;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author teras
 */
public class CommandArgs {

    private String classpath;
    private String[] classpathArr;
    private String jar;
    private final List<String> givenArgs = new ArrayList<>();
    private String mainClass = "";

    public CommandArgs(String[] args) {
        if (args == null || args.length < 0)
            return;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-help"))
                showHelp();
            else if (mainClass.isEmpty() && (arg.equals("-classpath") | arg.equals("-cp"))) {
                if ((i + 1) >= args.length)
                    parseError("Class path requires an argument");
                if (classpath != null)
                    parseError("Class path already defined");
                i++;
                classpath = args[i];
            } else if (mainClass.isEmpty() && (arg.equals("-jar"))) {
                if ((i + 1) >= args.length)
                    parseError("JAR file requires an argument");
                if (jar != null)
                    parseError("JAR file already defined");
                i++;
                jar = args[i];
                MainResolver.getMainFromJar(jar, this);
                if (mainClass.isEmpty())
                    parseError("Unable to retrieve main class from JAR file " + jar);
            } else if (!mainClass.isEmpty())
                givenArgs.add(arg);
            else {
                if (classpath == null && jar == null)
                    parseError("Please provide either class path or jar file before defining other arguments");
                mainClass = arg;
            }
        }
        if (classpath != null && mainClass.isEmpty())
            parseError("Missing definition of main class, when defining class path");
    }

    void setMain(String main) {
        this.mainClass = main == null ? "" : main;
    }

    void appendClasspath(String classpath) {
        this.classpath = this.classpath == null || this.classpath.isEmpty() ? classpath : this.classpath + ":" + classpath;
    }

    private void parseError(String error) {
        System.err.println();
        System.err.println(error);
        System.err.println();
        showHelp();
    }

    private void showHelp() {
        System.err.println("Usage: java JAVA_OPTIONS -jar xray.jar -cp|-classpath CLASSPATH Main.Class ARGS...\n"
            + "       java JAVA_OPTIONS -jar xray.jar [-cp|-classpath CLASSPATH] -jar APP.jar ARGS...\n"
            + "       java -help\n"
            + "Where\n"
            + "  JAVA_OPTIONS : the required by the application Java options\n"
            + "  CLASSPATH    : the classpath of the application to examine\n"
            + "  Main.Class   : the main class of the application to execute\n"
            + "  APP.jar      : the application JAR\n"
            + "  ARGS...      : The possible application arguments\n");
        System.exit(-1);
    }

    public String getMainClass() {
        return mainClass;
    }

    public String[] getClasspath() {
        if (classpathArr == null)
            if (jar == null)
                classpathArr = classpath.split(File.pathSeparator);
            else {
                File base = new File(jar).getParentFile().getAbsoluteFile();
                List<String> cpArray = new ArrayList<>();
                if (classpath != null && !classpath.isEmpty())
                    for (String pathname : classpath.split(File.pathSeparator))
                        cpArray.add(new File(base, pathname).getAbsolutePath());
                cpArray.add(jar);
                classpathArr = cpArray.toArray(new String[cpArray.size()]);
            }
        return classpathArr;
    }

    public String getJar() {
        return jar;
    }

    public String[] getArguments() {
        return givenArgs.toArray(new String[givenArgs.size()]);
    }
}
