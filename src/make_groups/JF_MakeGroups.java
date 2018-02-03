package make_groups;

import java.util.*;
import java.io.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

public class JF_MakeGroups extends javax.swing.JFrame {

    List<String> students = new ArrayList<String>();

    private javax.swing.JScrollPane jspGroups;
    private javax.swing.JTable tblGroups;

    public JF_MakeGroups() {
        initComponents();

        // JTable anlegen
        tblGroups = new javax.swing.JTable();
        jspGroups = new javax.swing.JScrollPane();

        jspGroups.setViewportView(tblGroups);
        getContentPane().add(jspGroups);

        this.setBounds(100, 100, 300, 200);
        this.setTitle("Gruppenaufteilung");
    }

    private void shuffle(List<String> strL) {
        Random zzg = new Random();
        int foo, bar;
        String tmp;

        for (int i = 0; i < 2 * strL.size(); i++) {
            foo = zzg.nextInt(strL.size());
            bar = zzg.nextInt(strL.size());
            tmp = strL.get(foo);
            strL.set(foo, strL.get(bar));
            strL.set(bar, tmp);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNoMembersPerGroup = new javax.swing.JTextField();
        btnRandom = new javax.swing.JButton();
        btnRandom1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Anzahl der Gruppenmitglieder");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 47, 192, 16);

        txtNoMembersPerGroup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNoMembersPerGroup.setText("3");
        getContentPane().add(txtNoMembersPerGroup);
        txtNoMembersPerGroup.setBounds(240, 40, 39, 30);

        btnRandom.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnRandom.setText("Random!");
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });
        getContentPane().add(btnRandom);
        btnRandom.setBounds(40, 80, 128, 25);

        btnRandom1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnRandom1.setText("Gruppe laden");
        btnRandom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandom1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnRandom1);
        btnRandom1.setBounds(40, 10, 128, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed
        if (students.size() > 0) {
            createTable(txtNoMembersPerGroup.getText(), false);
        } else {
            warningMessage("Bitte eine Gruppe laden.");
        }
    }//GEN-LAST:event_btnRandomActionPerformed

    private void btnRandom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandom1ActionPerformed
        //Textfile mit Namensliste auswählen
        JFileChooser chooser = new JFileChooser();
        // basedir des filechoosers einstellen
        chooser.setCurrentDirectory(new File("src/data"));
        // Dialog zum Oeffnen von Dateien anzeigen
        chooser.showDialog(null, "Gruppe laden");

        try {
            File myGroup_File = chooser.getSelectedFile();
            if (myGroup_File == null) {
                warningMessage("Keine Datei ausgewählt.");
            } else if (myGroup_File.getName().contains(".txt")) {
                this.readStudents(myGroup_File);
            } else {
                warningMessage("Bitte wählen Sie eine .txt Datei aus.");
            }
        } catch (IOException ex) {
            Logger.getLogger(JF_MakeGroups.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(JF_MakeGroups.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }//GEN-LAST:event_btnRandom1ActionPerformed

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
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JF_MakeGroups myGroups = new JF_MakeGroups();
                myGroups.setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRandom;
    private javax.swing.JButton btnRandom1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtNoMembersPerGroup;
    // End of variables declaration//GEN-END:variables

    private void createTable(String text, boolean first_load) {
        Object[][] membership;
        String[] header;
        int table_width_factor = 2;
        int noGroups = 1;
        try {
            int parsedText = Integer.parseInt(text);
            noGroups = (parsedText > 0) ? parsedText : 1;
        } catch (NumberFormatException ex) {
            Logger.getLogger(JF_MakeGroups.class.getName()).log(Level.WARNING, null, ex);
        }
        int membersPerGroup = students.size() / noGroups;
        // compensation of unequal grouplength
        if (students.size() % noGroups > 0) {
            membersPerGroup++;
        }

        //Nur bei ersten Laden der Schülergruppe anwesend oder nicht mit einbauen
        if (first_load) {
            table_width_factor = 2;
            header = new String[]{"Schüler", "anwesend"};
            membership = new Object[membersPerGroup][2];
            // initialize gStudents-array
            for (int mCount = 0, i = 0; mCount < membersPerGroup && i < students.size(); mCount++) {
                membership[mCount][0] = students.get(i++);
                membership[mCount][1] = true;
            }

            DefaultTableModel model = new DefaultTableModel(membership, header);
            //Anwesend Klick beobachten
            model.addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent tme) {
                    if (tme.getType() == TableModelEvent.UPDATE) {
                        //anwesend abgewählt
                        if (model.getValueAt(tme.getFirstRow(), tme.getColumn()).toString().equals("false")) {
                            int result = JOptionPane.showConfirmDialog(null, "Möchten Sie den Schüler " + students.get(tme.getFirstRow()) + " aus der Liste entfernen?", "Bestätigen", JOptionPane.YES_NO_CANCEL_OPTION);
                            if (result == JOptionPane.YES_OPTION) {
                                students.remove(tme.getFirstRow());
                                createTable("1", true);
                            }
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
                            return Boolean.class;
                    }
                }

            };

        } else {
            shuffle(students);
            membership = new Object[membersPerGroup][noGroups];
            // initialize gStudents-array
            for (int group = 0; group < noGroups; group++) {
                for (int mCount = 0; mCount < membersPerGroup; mCount++) {
                    membership[mCount][group] = "";
                }
            }
            // fill gStudent-array with names
            for (int mCount = 0, i = 0; mCount < membersPerGroup && i < students.size(); mCount++) {
                for (int group = 0; group < noGroups && i < students.size(); group++) {
                    membership[mCount][group] = students.get(i++);
                }
            }

            // and create simple header numbering
            header = new String[noGroups];
            for (int i = 0; i < noGroups; i++) {
                header[i] = Integer.toString(i + 1);
            }

            tblGroups = new JTable();
            tblGroups.setModel(new javax.swing.table.DefaultTableModel(
                    membership, header
            ));
        }

        // quick and dirty...
        int tableWidth = noGroups * 150;
        int tableHeight = 50 + membersPerGroup * 20;
        jspGroups.setBounds(40, 110, tableWidth * table_width_factor, tableHeight);
        this.setBounds(this.getBounds().x, this.getBounds().y, 100 + tableWidth, 200 + tableHeight);
        jspGroups.setViewportView(tblGroups);
        this.repaint();
    }

    //Warnungen
    private void warningMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void readStudents(File group_file) throws FileNotFoundException, IOException {
        students.clear();

        FileReader fr = new FileReader(group_file);
        BufferedReader br = new BufferedReader(fr);
        String zeile_1, zeile = br.readLine();

        while (zeile != null) {
            if (zeile.contains("\"")) {
                zeile_1 = zeile.substring(zeile.indexOf("\"") + 1, zeile.lastIndexOf("\""));
                students.add(zeile_1);
            }
            zeile = br.readLine();
        }

        if (students.size() < 1) {
            warningMessage("Die Textdatei enthielt keine Schüler. Bitte laden Sie eine andere Datei.");
        } else {
            createTable("1", true);
        }
        br.close();

    }
}
