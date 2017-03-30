/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.plugin.io;

import bsh.Interpreter;
import com.panayotis.xray.plugin.Logger;
import com.panayotis.xray.plugin.XRayPlugin;
import com.panayotis.xray.sys.RedirectPrintStream;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author teras
 */
public class IOPlugin extends javax.swing.JPanel implements XRayPlugin {

    private final Interpreter inter = new Interpreter();
    private final SimpleAttributeSet outStyle = new SimpleAttributeSet();
    private final SimpleAttributeSet errorStyle = new SimpleAttributeSet();
    private final SimpleAttributeSet evalInputStyle = new SimpleAttributeSet();
    private final SimpleAttributeSet evalOutputStyle = new SimpleAttributeSet();
    private final SimpleAttributeSet evalErrorStyle = new SimpleAttributeSet();

    /**
     * Creates new form IOPlugin
     */
    public IOPlugin() {
        initComponents();
        StyleConstants.setForeground(outStyle, Color.black);
        StyleConstants.setForeground(errorStyle, Color.red);
        StyleConstants.setItalic(evalInputStyle, true);
        StyleConstants.setForeground(evalInputStyle, Color.lightGray);
        StyleConstants.setForeground(evalOutputStyle, Color.blue);
        StyleConstants.setForeground(evalErrorStyle, Color.magenta);

        System.setOut(new RedirectPrintStream(System.out, t -> showOut(t, outStyle)));
        System.setErr(new RedirectPrintStream(System.out, t -> showError(t, errorStyle)));

        outT.setEditorKit(new LetterBasedKit());
        errorT.setEditorKit(new LetterBasedKit());
        bothT.setEditorKit(new LetterBasedKit());

        eval("import crossmobile.ios.uikit.*; import crossmobile.ios.coregraphics.*;", true);

        viewportUpdate();
    }

    @Override
    public String getPluginName() {
        return "I/O";
    }

    @Override
    public JComponent getPluginVisuals() {
        return this;
    }

    @Override
    public int getPluginOrder() {
        return 1000;
    }

    @Override
    public void onFocus() {
        input.requestFocusInWindow();
    }

    private void eval(String code, boolean silently) {
        if (code.trim().isEmpty())
            return;
        if (!silently)
            showOut("# " + code + "\n", evalInputStyle);
        try {
            Object result = inter.eval(code);
            if (!silently)
                showOut(result == null ? " - no result -\n" : result.toString().trim() + "\n", evalOutputStyle);
        } catch (Throwable th) {
            showError(th.toString().trim() + "\n", evalErrorStyle);
        }
    }

    private void viewportUpdate() {
        if (outS.getParent() != null)
            outS.getParent().remove(outS);
        if (errorS.getParent() != null)
            errorS.getParent().remove(errorS);
        if (bothS.getParent() != null)
            bothS.getParent().remove(bothS);
        JComponent v = output.isSelected()
                ? outS
                : (error.isSelected()
                ? errorS
                : bothS);
        viewport.add(v, BorderLayout.CENTER);
        viewport.revalidate();
        viewport.repaint();
    }

    private void showOut(String txt, SimpleAttributeSet style) {
        try {
            outT.getStyledDocument().insertString(outT.getStyledDocument().getLength(), txt, style);
            bothT.getStyledDocument().insertString(bothT.getStyledDocument().getLength(), txt, style);
            outT.setCaretPosition(outT.getDocument().getLength());
            bothT.setCaretPosition(bothT.getDocument().getLength());
            Logger.out(txt);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    private void showError(String txt, SimpleAttributeSet style) {
        try {
            errorT.getStyledDocument().insertString(errorT.getStyledDocument().getLength(), txt, style);
            bothT.getStyledDocument().insertString(bothT.getStyledDocument().getLength(), txt, style);
            errorT.setCaretPosition(errorT.getDocument().getLength());
            bothT.setCaretPosition(bothT.getDocument().getLength());
            Logger.err(txt);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        outputErrorGroup = new javax.swing.ButtonGroup();
        bothS = new javax.swing.JScrollPane();
        bothT = new javax.swing.JTextPane();
        errorS = new javax.swing.JScrollPane();
        errorT = new javax.swing.JTextPane();
        outS = new javax.swing.JScrollPane();
        outT = new javax.swing.JTextPane();
        jToolBar1 = new javax.swing.JToolBar();
        clean = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        output = new javax.swing.JToggleButton();
        error = new javax.swing.JToggleButton();
        both = new javax.swing.JToggleButton();
        commandP = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        input = new JAutoCompleteTextField();
        viewport = new javax.swing.JPanel();

        bothT.setEditable(false);
        bothT.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        bothS.setViewportView(bothT);

        errorT.setEditable(false);
        errorT.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        errorS.setViewportView(errorT);

        outT.setEditable(false);
        outT.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        outS.setViewportView(outT);

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        clean.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sweep.png"))); // NOI18N
        clean.setFocusable(false);
        clean.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clean.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cleanActionPerformed(evt);
            }
        });
        jToolBar1.add(clean);
        jToolBar1.add(filler1);

        outputErrorGroup.add(output);
        output.setText("O");
        output.setFocusable(false);
        output.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        output.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        output.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        output.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumptToViewport(evt);
            }
        });
        jToolBar1.add(output);

        outputErrorGroup.add(error);
        error.setText("E");
        error.setFocusable(false);
        error.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        error.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        error.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        error.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumptToViewport(evt);
            }
        });
        jToolBar1.add(error);

        outputErrorGroup.add(both);
        both.setSelected(true);
        both.setText("O/E");
        both.setFocusable(false);
        both.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        both.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        both.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        both.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumptToViewport(evt);
            }
        });
        jToolBar1.add(both);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        commandP.setLayout(new javax.swing.BoxLayout(commandP, javax.swing.BoxLayout.X_AXIS));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/gearsmall.png"))); // NOI18N
        commandP.add(jLabel1);

        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });
        commandP.add(input);

        add(commandP, java.awt.BorderLayout.SOUTH);

        viewport.setLayout(new java.awt.BorderLayout());
        add(viewport, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        eval(input.getText(), false);
    }//GEN-LAST:event_inputActionPerformed

    private void jumptToViewport(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumptToViewport
        viewportUpdate();
    }//GEN-LAST:event_jumptToViewport

    private void cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cleanActionPerformed
        outT.setText("");
        errorT.setText("");
        bothT.setText("");
    }//GEN-LAST:event_cleanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton both;
    private javax.swing.JScrollPane bothS;
    private javax.swing.JTextPane bothT;
    private javax.swing.JButton clean;
    private javax.swing.JPanel commandP;
    private javax.swing.JToggleButton error;
    private javax.swing.JScrollPane errorS;
    private javax.swing.JTextPane errorT;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane outS;
    private javax.swing.JTextPane outT;
    private javax.swing.JToggleButton output;
    private javax.swing.ButtonGroup outputErrorGroup;
    private javax.swing.JPanel viewport;
    // End of variables declaration//GEN-END:variables

}
