/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.editor.model;

import com.alex.store.Store;
import com.editor.Main;
import com.editor.ToolSelection;
import com.editor.Utils;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.WindowConstants;

/**
 *
 * @author Travis
 */
public class MultiModelPacker extends javax.swing.JFrame {

    private static Store STORE;

    /**
     * Creates new form MultiModelPacker
     */
    public MultiModelPacker() {
        initComponents();
    }

    public MultiModelPacker(String cache) {
        try {
            STORE = new Store(cache);
        } catch (Exception e) {
            Main.log("MultiModelPacker", "Cannot find cache directory");
        }
        initComponents();
        setResizable(false);
        setTitle("MultiModelPacker");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        modelDirField = new javax.swing.JTextField();
        sameId = new javax.swing.JCheckBox();
        submit = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        modelDir = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Multiple Model Packer");

        jLabel2.setText("Models Directory");

        sameId.setText("Keep Same ID");
        sameId.setToolTipText("Keeps same ID as named");

        submit.setText("Submit");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        modelDir.setText("Choose Model Dir");
        modelDir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelDirActionPerformed(evt);
            }
        });
        jMenu1.add(modelDir);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modelDirField)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2))
                            .addComponent(sameId)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(submit)))
                .addGap(0, 76, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modelDirField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(sameId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(submit)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        String directory = modelDirField.getText();
        boolean keepID = sameId.isSelected();
        if (keepID) {
            String fileName = "";
            String modelFileID = "";
            File path = new File(directory);
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    System.out.println(files[i]);
                    fileName = files[i].getName();
                    modelFileID = files[i].getName().replace(".dat", "");
                    try {
                        Main.log("MultiModelPacker", "The model ID of " + fileName + " is: " + Utils.packCustomModel(STORE, Utils.getBytesFromFile(files[i]), Integer.parseInt(modelFileID)));
                    } catch (IOException ex) {
                        Main.log("MultiModelPacker", "There was an error packing the model.");
                    }

                }
            }
        } else {
            String fileName = "";
            File path = new File(directory);
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    fileName = files[i].getName();
                    try {
                        Main.log("MultiModelPacker", "The model ID of " + fileName + " is: " + Utils.packCustomModel(STORE, Utils.getBytesFromFile(files[i])));
                    } catch (IOException ex) {
                        Main.log("MultiModelPacker", "There was an error packing the model.");
                    }

                }
            }
        }
    }//GEN-LAST:event_submitActionPerformed

    private void modelDirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelDirActionPerformed
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (evt.getSource() == modelDir) {
            int returnVal = fc.showOpenDialog(MultiModelPacker.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                modelDirField.setText(file.getPath() + "/");
            }
        }
    }//GEN-LAST:event_modelDirActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MultiModelPacker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MultiModelPacker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MultiModelPacker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MultiModelPacker.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MultiModelPacker().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem exit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem modelDir;
    private javax.swing.JTextField modelDirField;
    private javax.swing.JCheckBox sameId;
    private javax.swing.JButton submit;
    // End of variables declaration//GEN-END:variables
}
