package make_groups;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

// todo:
// - Anzahl Gruppenmitglieder <-> Anzahl Gruppen
// - createTable überarbeiten...
public class View extends javax.swing.JFrame {

    Model myClassRoom = new Model();        // Modell
    Controller ctrl = new Controller(myClassRoom);

    private javax.swing.JScrollPane jspGroups;
    private javax.swing.JTable tblGroups;

    public View() {
        initComponents();

        // JTable anlegen
        tblGroups = new javax.swing.JTable();
        jspGroups = new javax.swing.JScrollPane();

        jspGroups.setViewportView(tblGroups);
        getContentPane().add(jspGroups);

        this.setBounds(100, 100, 300, 200);
        this.setTitle("Gruppenaufteilung");
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNoMembersPerGroup = new javax.swing.JTextField();
        btnRandom = new javax.swing.JButton();
        btnLoadFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Number of groups:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(50, 50, 140, 16);

        txtNoMembersPerGroup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNoMembersPerGroup.setText("3");
        getContentPane().add(txtNoMembersPerGroup);
        txtNoMembersPerGroup.setBounds(190, 40, 39, 30);

        btnRandom.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnRandom.setText("Random!");
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });
        getContentPane().add(btnRandom);
        btnRandom.setBounds(40, 80, 128, 25);

        btnLoadFile.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnLoadFile.setText("Load Class");
        btnLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadFileActionPerformed(evt);
            }
        });
        getContentPane().add(btnLoadFile);
        btnLoadFile.setBounds(40, 10, 128, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents

 

    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed
        int noMembersPerGroup = Integer.parseInt(txtNoMembersPerGroup.getText()); // todo: error management missing...

        if (myClassRoom.getNoActiveStudents() > 0) {
            createGroupTable(txtNoMembersPerGroup.getText());
        } else {
            warningMessage("Bitte eine Gruppe laden.");
        }
    }//GEN-LAST:event_btnRandomActionPerformed

    private void btnLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadFileActionPerformed

        JFileChooser chooser = new JFileChooser("src/data");
        chooser.showDialog(null, "Gruppe laden");

        try {
            File studentFile = chooser.getSelectedFile();
            ctrl.readStudentsFromFile(studentFile);
            createSelectionTable();
        } catch (IOException e) {
            // todo: error management missing...
        }
    }//GEN-LAST:event_btnLoadFileActionPerformed

        private void createSelectionTable() {
        Object[][] displayData;
        String[] header;
        int noRows = 1;
        int table_width_factor = 1;
        int noStudents = myClassRoom.getNoStudents();

        table_width_factor = 2;
        header = new String[]{"Schüler", "anwesend"};
        displayData = new Object[noStudents][2];

        for (int row = 0; row < noStudents; row++) {
            displayData[row][0] = myClassRoom.getStudent(row).name;
            displayData[row][1] = myClassRoom.getStudent(row).active;
        }

        DefaultTableModel model = new DefaultTableModel(displayData, header);
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.UPDATE) {
                    if (model.getValueAt(tme.getFirstRow(), tme.getColumn()).toString().equals("false")) {
                        myClassRoom.getStudent(tme.getFirstRow()).active = false;   // --> inactive
                    }

                }
            }

        });

        tblGroups = new JTable(model) {
            private static final long serialVersionUID = 1L;

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return Boolean.class; // display checkboxes!!!
                }
            }
        };

        // quick and dirty...
        int tableWidth = noRows * 150;
        int tableHeight = 50 + noStudents * 20;
        jspGroups.setBounds(40, 110, tableWidth * table_width_factor, tableHeight);
        this.setBounds(this.getBounds().x, this.getBounds().y, 100 + tableWidth * table_width_factor, 200 + tableHeight);
        jspGroups.setViewportView(tblGroups);
        this.repaint();
    }

    private void createGroupTable(String text) {
        Object[][] displayData;
        String[] header;
        int table_width_factor = 1;
        int noGroups = 1;
        List<Student> activeStudents = myClassRoom.getActiveStudents();
        try {
            int parsedText = Integer.parseInt(text);
            noGroups = (parsedText > 0) ? parsedText : 1;
        } catch (NumberFormatException ex) {
            Logger.getLogger(View.class.getName()).log(Level.WARNING, null, ex);
        }
        int membersPerGroup = activeStudents.size() / noGroups;
        // compensation of unequal grouplength
        if (activeStudents.size() % noGroups > 0) {
            membersPerGroup++;
        }

        myClassRoom.shuffleStudents();
        displayData = new Object[membersPerGroup][noGroups];
        // initialize gStudents-array
        for (int group = 0; group < noGroups; group++) {
            for (int mCount = 0; mCount < membersPerGroup; mCount++) {
                displayData[mCount][group] = "";
            }
        }
        // fill gStudent-array with names
        for (int mCount = 0, i = 0; mCount < membersPerGroup && i < activeStudents.size(); mCount++) {
            for (int group = 0; group < noGroups && i < activeStudents.size(); group++) {
                displayData[mCount][group] = activeStudents.get(i++).name;
            }
        }

        // and create simple header numbering
        header = new String[noGroups];
        for (int i = 0; i < noGroups; i++) {
            header[i] = Integer.toString(i + 1);
        }

        tblGroups = new JTable();
        tblGroups.setModel(new javax.swing.table.DefaultTableModel(displayData, header));

        // quick and dirty...
        int tableWidth = noGroups * 150;
        int tableHeight = 50 + membersPerGroup * 20;
        jspGroups.setBounds(40, 110, tableWidth , tableHeight);
        this.setBounds(this.getBounds().x, this.getBounds().y, 100 + tableWidth * table_width_factor, 200 + tableHeight);
        jspGroups.setViewportView(tblGroups);
        this.repaint();
    }

    //Warnungen
    private void warningMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    
    public static void main(String args[]) throws IOException {
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
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                View myGroups = new View();
                myGroups.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadFile;
    private javax.swing.JButton btnRandom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtNoMembersPerGroup;
    // End of variables declaration//GEN-END:variables


}
