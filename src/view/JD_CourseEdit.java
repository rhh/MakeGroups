package view;

import controller.Controller;
import java.awt.BorderLayout;
import java.awt.Dialog;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class JD_CourseEdit extends javax.swing.JDialog {

    Controller ctrl;
    private javax.swing.JScrollPane jspCourse;
    private javax.swing.JTable tblcourse;

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

        getContentPane().add(jspCourse, java.awt.BorderLayout.CENTER);

        pack();
    }

    private void createSelectionTable() {
        Object[][] displayData;
        String[] header;
        int noStudents = ctrl.getNoStudents();

        header = new String[]{"Students:", "Active:"};
        displayData = new Object[noStudents][2];

        for (int row = 0; row < noStudents; row++) {
            displayData[row][0] = ctrl.getStudent(row).name;
            displayData[row][1] = ctrl.getStudent(row).active;
        }

        DefaultTableModel model = new DefaultTableModel(displayData, header);
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent tme) {
                if (tme.getType() == TableModelEvent.UPDATE) {
                    if ((boolean) model.getValueAt(tme.getFirstRow(), tme.getColumn())) {
                        ctrl.getStudent(tme.getFirstRow()).active = true;
                    } else {
                        ctrl.getStudent(tme.getFirstRow()).active = false;
                    }

                }
            }

        });

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
