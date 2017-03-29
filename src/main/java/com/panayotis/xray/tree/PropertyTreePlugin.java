/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.xray.tree;

import com.panayotis.xray.plugin.XRayPlugin;
import com.panayotis.xray.props.PropertyManagerFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public abstract class PropertyTreePlugin<T> extends javax.swing.JPanel implements XRayPlugin {

    private final PropertyManagerFactory factory;
    private final PropertyTreeModel<T> model;
    private final String name;
    private final int order;
    private T selected = null;
    private JScrollPane props = null;
    private JSplitPane both = null;
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            refresh(true);
        }
    };

    public PropertyTreePlugin(PropertyTreeModel<T> model, String name) {
        this(model, name, 500);
    }

    public PropertyTreePlugin(PropertyTreeModel<T> model, String name, PropertyManagerFactory factory) {
        this(model, name, 500, factory);
    }

    public PropertyTreePlugin(PropertyTreeModel<T> model, String name, int order) {
        this(model, name, order, new PropertyManagerFactory());
    }

    public PropertyTreePlugin(PropertyTreeModel<T> model, String name, int order, PropertyManagerFactory factory) {
        initComponents();
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setModel(null);
        tree.setUI(new BasicTreeUI() {
            @Override
            public int getRightChildIndent() {
                return 5;
            }

            @Override
            protected int getRowX(int row, int depth) {
                return super.getRowX(row, depth) * 6 / 11;
            }
        });
        this.factory = factory;
        this.name = name;
        this.order = order;
        this.model = model;

        TreeCellRenderer renderer = model.getTreeDecorator().getRenderer();
        if (renderer != null)
            tree.setCellRenderer(renderer);
    }

    @Override

    public String getPluginName() {
        return name;
    }

    @Override
    public JComponent getPluginVisuals() {
        return this;
    }

    @Override
    public int getPluginOrder() {
        return order;
    }

    @Override
    public void onFocus() {
        refresh(true);
    }

    public void addToolbarButton(AbstractButton button) {
        toolbar.add(button, toolbar.getComponentCount() - 4);
    }

    public T getSelectedItem() {
        return selected;
    }

    public synchronized void refresh(boolean shouldRefreshTree) {
        SwingUtilities.invokeLater(() -> {
            viewport.removeAll();
            if (treeView != null && treeView.getParent() != null)
                treeView.getParent().remove(treeView);
            if (props != null && props.getParent() != null)
                props.getParent().remove(props);
            if (both != null) {
                if (both.getParent() != null)
                    both.getParent().remove(both);
                both.removeAll();
            }
            JComponent cmp = treeB.isSelected()
                ? updateTree()
                : (propB.isSelected()
                    ? updateProps()
                    : updateBoth(shouldRefreshTree));
            viewport.add(cmp, BorderLayout.CENTER);
            viewport.revalidate();
            viewport.repaint();
        });
    }

    private JComponent updateTree() {
        tree.setModel(new DefaultTreeModel(new PropertyTreeNode(model)));
        int row = 0;
        TreePath path = null;
        while (row < tree.getRowCount()) {
            tree.expandRow(row);
            if (path == null) {
                path = tree.getPathForRow(row);
                if (((PropertyTreeNode<T>) path.getLastPathComponent()).getItem() != selected)
                    path = null;// still not found
            }
            row++;
        }
        if (path != null)
            tree.getSelectionModel().setSelectionPath(path);
        tree.setRootVisible(false);
        tree.setShowsRootHandles(true);
        return treeView;
    }

    private JComponent updateProps() {
        if (selected == null)
            return new JLabel("Please select an item first", JLabel.CENTER);
        Rectangle pos = props == null ? null : props.getViewport().getViewRect();
        props = new JScrollPane(factory.getView(selected));
        props.getVerticalScrollBar().setUnitIncrement(20);
        if (pos != null)
            props.getViewport().scrollRectToVisible(pos);
        return props;
    }

    private JComponent updateBoth(boolean shouldRefreshTree) {
        JComponent c1 = shouldRefreshTree ? updateTree() : treeView;
        JComponent c2 = updateProps();
        c1.setMinimumSize(new Dimension(20, 50));
        c2.setMinimumSize(new Dimension(100, 50));
        int divider = -1;
        if (both != null)
            divider = both.getDividerLocation();
        both = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, c1, c2);
        both.setDividerSize(7);
        both.setResizeWeight(getTreeResizeWeight());
        if (divider < 0)
            both.setDividerLocation(140);
        else
            both.setDividerLocation(divider);
        return both;
    }

    protected double getTreeResizeWeight() {
        return 0.6;
    }

    public PropertyManagerFactory getFactory() {
        return factory;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewtypeBG = new javax.swing.ButtonGroup();
        treeView = new javax.swing.JScrollPane();
        tree = new javax.swing.JTree();
        toolbar = new javax.swing.JToolBar();
        onceB = new javax.swing.JToggleButton();
        timedB = new javax.swing.JToggleButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 0), new java.awt.Dimension(8, 32767));
        treeB = new javax.swing.JToggleButton();
        propB = new javax.swing.JToggleButton();
        bothB = new javax.swing.JToggleButton();
        viewport = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();

        treeView.setMinimumSize(new java.awt.Dimension(100, 100));

        tree.setMinimumSize(new java.awt.Dimension(30, 30));
        tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeValueChanged(evt);
            }
        });
        treeView.setViewportView(tree);

        setLayout(new java.awt.BorderLayout());

        toolbar.setFloatable(false);
        toolbar.setRollover(true);

        onceB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload1.png"))); // NOI18N
        onceB.setFocusable(false);
        onceB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        onceB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        onceB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onceBActionPerformed(evt);
            }
        });
        toolbar.add(onceB);

        timedB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/reload.png"))); // NOI18N
        timedB.setFocusable(false);
        timedB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        timedB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        timedB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timedBActionPerformed(evt);
            }
        });
        toolbar.add(timedB);
        toolbar.add(filler2);
        toolbar.add(filler1);

        viewtypeBG.add(treeB);
        treeB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/views.png"))); // NOI18N
        treeB.setSelected(true);
        treeB.setFocusable(false);
        treeB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        treeB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        treeB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewStateChanged(evt);
            }
        });
        toolbar.add(treeB);

        viewtypeBG.add(propB);
        propB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/props.png"))); // NOI18N
        propB.setFocusable(false);
        propB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        propB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        propB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewStateChanged(evt);
            }
        });
        toolbar.add(propB);

        viewtypeBG.add(bothB);
        bothB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/both.png"))); // NOI18N
        bothB.setFocusable(false);
        bothB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bothB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bothB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewStateChanged(evt);
            }
        });
        toolbar.add(bothB);

        add(toolbar, java.awt.BorderLayout.NORTH);

        viewport.setLayout(new java.awt.BorderLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(jComboBox1);

        viewport.add(jPanel1, java.awt.BorderLayout.CENTER);

        add(viewport, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void onceBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onceBActionPerformed
        onceB.setEnabled(false);
        timedB.setEnabled(false);
        SwingUtilities.invokeLater(() -> {
            refresh(true);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    onceB.setEnabled(true);
                    timedB.setEnabled(true);
                    onceB.setSelected(false);
                }
            }, 100);
        });
    }//GEN-LAST:event_onceBActionPerformed

    private void treeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeValueChanged
        selected = ((PropertyTreeNode<T>) evt.getPath().getLastPathComponent()).getItem();
        if (bothB.isSelected())
            refresh(false);
    }//GEN-LAST:event_treeValueChanged

    private void viewStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_viewStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED)
            refresh(evt.getSource() == bothB);
    }//GEN-LAST:event_viewStateChanged

    private void timedBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timedBActionPerformed
        if (timedB.isSelected()) {
            onceB.setEnabled(false);
            if (task != null)
                new Timer().scheduleAtFixedRate(task, 0, 1000);
        } else if (task != null) {
            task.cancel();
            onceB.setEnabled(true);
            task = null;
        }
    }//GEN-LAST:event_timedBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton bothB;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton onceB;
    private javax.swing.JToggleButton propB;
    private javax.swing.JToggleButton timedB;
    private javax.swing.JToolBar toolbar;
    private javax.swing.JTree tree;
    private javax.swing.JToggleButton treeB;
    private javax.swing.JScrollPane treeView;
    private javax.swing.JPanel viewport;
    private javax.swing.ButtonGroup viewtypeBG;
    // End of variables declaration//GEN-END:variables

}
