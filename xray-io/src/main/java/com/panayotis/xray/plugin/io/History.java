/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.io;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author teras
 */
public class History {

    private final List<String> history = new ArrayList<>();
    private int historyPointer = 0;

    {
        history.add("");
    }

    public void update(String current) {
        history.set(history.size() - 1, current);
        historyPointer = history.size() - 1;
    }

    public void send() {
        if (!history.get(history.size() - 1).isEmpty()) {
            historyPointer = history.size();
            history.add("");
        }
    }

    public String previous() {
        historyPointer--;
        if (historyPointer < 0) {
            historyPointer = 0;
            return null;
        }
        return history.get(historyPointer);
    }

    public String next() {
        historyPointer++;
        if (historyPointer >= history.size()) {
            historyPointer = history.size() - 1;
            return null;
        }
        return history.get(historyPointer);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("[ ");
        for (int i = 0; i < history.size(); i++)
            out.append('\'').append(history.get(i)).append(historyPointer == i ? "*" : "").append("' ");
        out.append("]");
        return out.toString();
    }

}
