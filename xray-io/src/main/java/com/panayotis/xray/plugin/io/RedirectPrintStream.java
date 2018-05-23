/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.io;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.function.Consumer;

/**
 *
 * @author teras
 */
public class RedirectPrintStream extends PrintStream {

    private final Consumer<String> cons;

    public RedirectPrintStream(OutputStream out, Consumer<String> cons) {
        super(out);
        this.cons = cons;
    }

    @Override
    public void print(Object obj) {
        cons.accept(obj == null ? "null" : obj.toString());
    }

    @Override
    public void print(String s) {
        cons.accept(s == null ? "null" : s);
    }

    @Override
    public void print(boolean b) {
        cons.accept(b ? "true" : "false");
    }

    @Override
    public void print(char c) {
        cons.accept(String.valueOf(c));
    }

    @Override
    public void print(char[] s) {
        cons.accept(new String(s));
    }

    @Override
    public void print(double d) {
        cons.accept(Double.toString(d));
    }

    @Override
    public void print(float f) {
        cons.accept(Float.toString(f));
    }

    @Override
    public void print(int i) {
        cons.accept(Integer.toString(i));
    }

    @Override
    public void print(long l) {
        cons.accept(Long.toString(l));
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        cons.accept(String.format(format, args));
        return this;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        cons.accept(String.format(l, format, args));
        return this;
    }

    @Override
    public void println() {
        cons.accept("\n");
    }

    @Override
    public void println(Object x) {
        print(x);
        println();
    }

    @Override
    public void println(String x) {
        print(x);
        println();
    }

    @Override
    public void println(boolean x) {
        print(x);
        println();
    }

    @Override
    public void println(char x) {
        print(x);
        println();
    }

    @Override
    public void println(char[] x) {
        print(x);
        println();
    }

    @Override
    public void println(double x) {
        print(x);
        println();
    }

    @Override
    public void println(float x) {
        print(x);
        println();
    }

    @Override
    public void println(int x) {
        print(x);
        println();
    }

    @Override
    public void println(long x) {
        print(x);
        println();
    }

}
