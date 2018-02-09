package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.Student;

public class JD_CourseEdit extends javax.swing.JDialog {

    Controller ctrl;
    private javax.swing.JScrollPane jspCourse;
    private javax.swing.JTable tblcourse;
    private DefaultTableModel model;
    private JButton btnCancel;
    private JButton btnSave;

    List<Student> change2active = new ArrayList<>();
    List<Student> change2inactive = new ArrayList<>();

    public JD_CourseEdit(JF_Groups owner) {
        super(owner, "Edit Course:", Dialog.ModalityType.DOCUMENT_MODAL); // MODAL!!!
        this.ctrl = owner.ctrl;

        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // only close window...
        this.getContentPane().setLayout(new BorderLayout());

        // JTable anlegen
        tblcourse = new javax.swing.JTable();
        jspCourse = new javax.swing.JScrollPane();

        createSelectionTable();
        jspCourse.setViewportView(tblcourse);

        this.setLocation(owner.getLocation().x + 100, owner.getLocation().y + 100);
        getContentPane().add(jspCourse, java.awt.BorderLayout.CENTER);

        // cancel / ok buttons
        btnCancel = new JButton("Cancel?!");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelDialog();
            }
        });

        btnSave = new JButton("Save!");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAndExitDialog();
            }

        });

        JPanel panel = new JPanel(); //Flow layout by default
        //If you want to anchor the buttons to the right you might try
        //panel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        panel.add(btnSave);
        panel.add(btnCancel);
        getContentPane().add(panel, java.awt.BorderLayout.SOUTH);

        pack();
    }

    private void saveAndExitDialog() {
        for (Student s : change2active) {
            s.active = true;
            ctrl.move2end(s);
        }
        for (Student s : change2inactive) {
            s.active = false;
        }
        this.dispose();
    }

    private void cancelDialog() {
        this.dispose();
    }

    private void tableChangedEventHandler(TableModelEvent tme) {
        Student changedStudent = null;
        boolean willBeActive;

        if (tme.getType() == TableModelEvent.UPDATE) {
            changedStudent = ctrl.getStudent(tme.getFirstRow());
            willBeActive = (boolean) model.getValueAt(tme.getFirstRow(), tme.getColumn());
            if (willBeActive) {
                change2active.add(changedStudent);
            } else {
                change2inactive.add(changedStudent);
            }
        }
    }

    private void createSelectionTable() {
        Object[][] displayData;
        String[] header;
        int noStudents = ctrl.getNoStudents();

        // prepare table heading
        header = new String[]{"Students:", "Active:"};
        displayData = new Object[noStudents][2];

        // prepare table data
        for (int row = 0; row < noStudents; row++) {
            displayData[row][0] = ctrl.getStudent(row).name;
            displayData[row][1] = ctrl.getStudent(row).active;
        }

        // create table model from header + data
        model = new DefaultTableModel(displayData, header);
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent tme) {
                tableChangedEventHandler(tme);
            }

        });

        // create table from model
        tblcourse = new JTable(model) {
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
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
