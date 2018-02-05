package view;

import model.Course;
import controller.Controller;
import model.Student;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

// todo:
// - Anzahl Gruppenmitglieder <-> Anzahl Gruppen
// - createTable überarbeiten...
public class JF_Groups extends javax.swing.JFrame {

    Course myClassRoom = new Course();        // Modell
    Controller ctrl = new Controller(myClassRoom);

    private javax.swing.JScrollPane jspGroups;
    private javax.swing.JTable tblGroups;

    public JF_Groups() {
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiLoadFile = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiEditCourse = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Number of groups:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(20, 40, 140, 16);

        txtNoMembersPerGroup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNoMembersPerGroup.setText("3");
        getContentPane().add(txtNoMembersPerGroup);
        txtNoMembersPerGroup.setBounds(160, 30, 39, 30);

        btnRandom.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnRandom.setText("Random!");
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });
        getContentPane().add(btnRandom);
        btnRandom.setBounds(210, 30, 128, 25);

        jMenu1.setText("File");

        jmiLoadFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jmiLoadFile.setText("Load file");
        jmiLoadFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiLoadFileActionPerformed(evt);
            }
        });
        jMenu1.add(jmiLoadFile);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jmiEditCourse.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jmiEditCourse.setText("Edit course");
        jmiEditCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiEditCourseActionPerformed(evt);
            }
        });
        jMenu2.add(jmiEditCourse);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

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

    private void jmiLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiLoadFileActionPerformed
        JFileChooser chooser = new JFileChooser("src/data");
        chooser.showDialog(null, "Gruppe laden");

        try {
            File studentFile = chooser.getSelectedFile();
            ctrl.readStudentsFromFile(studentFile);
            editCourse();
        } catch (IOException e) {
            // todo: error management missing...
        }

    }//GEN-LAST:event_jmiLoadFileActionPerformed

    private void jmiEditCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiEditCourseActionPerformed
        editCourse();        
    }//GEN-LAST:event_jmiEditCourseActionPerformed


    private void editCourse(){
        new JD_CourseEdit(this).setVisible(true);
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
            Logger.getLogger(JF_Groups.class.getName()).log(Level.WARNING, null, ex);
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
        jspGroups.setBounds(40, 110, tableWidth, tableHeight);
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
            java.util.logging.Logger.getLogger(JF_Groups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_Groups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_Groups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_Groups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JF_Groups myGroups = new JF_Groups();
                myGroups.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRandom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jmiEditCourse;
    private javax.swing.JMenuItem jmiLoadFile;
    private javax.swing.JTextField txtNoMembersPerGroup;
    // End of variables declaration//GEN-END:variables

}
