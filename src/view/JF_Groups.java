package view;

import model.Course;
import controller.Controller;
import model.Student;
import java.util.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;

// todo:
// - Anzahl Gruppenmitglieder <-> Anzahl Gruppen
// - createTable überarbeiten...
public class JF_Groups extends javax.swing.JFrame {

    
    Controller ctrl = new Controller(new Course());     // everthing starts here

    private javax.swing.JScrollPane jspGroups;
    private javax.swing.JTable tblGroups;

    public JF_Groups() {
        initComponents();

        // JTable anlegen
        tblGroups = new javax.swing.JTable();
        jspGroups = new javax.swing.JScrollPane();

        jspGroups.setViewportView(tblGroups);
        getContentPane().add(jspGroups);

        this.setBounds(100, 100, 450, 200);
        this.setTitle("Gruppenaufteilung");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNoMembersPerGroup = new javax.swing.JTextField();
        jlNoGroups = new javax.swing.JLabel();
        btnSplit = new javax.swing.JButton();
        cbShuffle = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiLoadFile = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiEditCourse = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNoMembersPerGroup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNoMembersPerGroup.setText("3");

        jlNoGroups.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jlNoGroups.setText("Number of groups:");

        btnSplit.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnSplit.setText("Split!");
        btnSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSplitActionPerformed(evt);
            }
        });

        cbShuffle.setText("Shuffle?");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNoGroups)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNoMembersPerGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(cbShuffle)
                .addGap(18, 18, 18)
                .addComponent(btnSplit, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNoGroups)
                    .addComponent(txtNoMembersPerGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbShuffle)
                    .addComponent(btnSplit))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jmiLoadFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiLoadFileActionPerformed
        JFileChooser chooser = new JFileChooser("src/data");
        chooser.showDialog(null, "Gruppe laden");

        try {
            File studentFile = chooser.getSelectedFile();
            ctrl.readStudentsFromFile(studentFile);
            this.setTitle(ctrl.getCourseID());
            editCourse();
        } catch (IOException e) {
            // todo: error management missing...
        }

    }//GEN-LAST:event_jmiLoadFileActionPerformed

    private void jmiEditCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiEditCourseActionPerformed
        editCourse();
    }//GEN-LAST:event_jmiEditCourseActionPerformed

    private void btnSplitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSplitActionPerformed
        int noGroups=0;
        try {
            noGroups = Integer.parseInt(txtNoMembersPerGroup.getText()); // todo: error management missing...
        } catch (NumberFormatException ex) {
            Logger.getLogger(JF_Groups.class.getName()).log(Level.WARNING, null, ex);
        }

        if(cbShuffle.isSelected()){
            ctrl.shuffleStudents();
        }
        
        if (ctrl.getNoActiveStudents() > 0) {
            createGroupTable(noGroups);
        } else {
            warningMessage("Bitte eine Gruppe laden!");
        }
    }//GEN-LAST:event_btnSplitActionPerformed

    private void editCourse() {
        new JD_CourseEdit(this).setVisible(true);
    }

    private void createGroupTable(int noGroups) {
        Object[][] displayData;
        String[] header;
        int table_width_factor = 1;

        List<Student> activeStudents = ctrl.getActiveStudents();
        int membersPerGroup = activeStudents.size() / noGroups;
        // compensation of unequal grouplength
        if (activeStudents.size() % noGroups > 0) {
            membersPerGroup++;
        }

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

    // *** main() removed, to make view exchangable...

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSplit;
    private javax.swing.JCheckBox cbShuffle;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel jlNoGroups;
    private javax.swing.JMenuItem jmiEditCourse;
    private javax.swing.JMenuItem jmiLoadFile;
    private javax.swing.JTextField txtNoMembersPerGroup;
    // End of variables declaration//GEN-END:variables

}
