package make_groups;

import java.util.Random;

public class JF_MakeGroups extends javax.swing.JFrame {

    String[] students = {  // dummy name-list
	"Wechsler, Melanie",
	"Bar, Leon",
	"Drescher, Frank",
	"Lehrer, Ursula",
	"Schneider, Anke",
	"Duerr, Wolfgang",
	"Sommer, Kevin",
	"Hertz, Martin",
	"Mueller, Erik",
	"Shuster, René",
	"Richter, Bernd",
	"Vogler, Mandy",
	"Ziegler, Uwe",
	"Brauer, Ines",
	"Wagner, Torsten",
	"Richter, Lea" };

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
        this.setTitle(Integer.toString(students.length));
    }

    private void shuffle(String[] stra) {
        Random zzg = new Random();
        int foo, bar;
        String tmp;

        for (int i = 0; i < 2 * stra.length; i++) {
            foo = zzg.nextInt(stra.length);
            bar = zzg.nextInt(stra.length);
            tmp = stra[foo];
            stra[foo] = stra[bar];
            stra[bar] = tmp;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNoMembersPerGroup = new javax.swing.JTextField();
        btnRandom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Anzahl der Gruppenmitglieder:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 47, 197, 16);

        txtNoMembersPerGroup.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNoMembersPerGroup.setText("3");
        getContentPane().add(txtNoMembersPerGroup);
        txtNoMembersPerGroup.setBounds(250, 40, 39, 22);

        btnRandom.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnRandom.setText("Random!");
        btnRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRandomActionPerformed(evt);
            }
        });
        getContentPane().add(btnRandom);
        btnRandom.setBounds(40, 10, 128, 25);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRandomActionPerformed

        int noGroups = Integer.parseInt(txtNoMembersPerGroup.getText());
        int membersPerGroup = students.length / noGroups;
        // compensation of unequal grouplength
        if (students.length % noGroups > 0) {
            membersPerGroup++;
        }

        shuffle(students);

        String[][] membership = new String[membersPerGroup][noGroups];

        // initialize gStudents-array
        for (int group = 0; group < noGroups; group++) {
            for (int mCount = 0; mCount < membersPerGroup; mCount++) {
                membership[mCount][group] = "";
            }
        }

        // fill gStudent-array with names
        for (int mCount = 0, i = 0; mCount < membersPerGroup && i < students.length; mCount++) {
            for (int group = 0; group < noGroups && i < students.length; group++) {
                membership[mCount][group] = students[i++];
            }
        }

        // and create simple header numbering
        String[] header = new String[noGroups];
        for (int i = 0; i < noGroups; i++) {
            header[i] = Integer.toString(i + 1);
        }

        // fill JTable
        tblGroups.setModel(new javax.swing.table.DefaultTableModel(
                membership, header
        ));

        // quick and dirty...
        int tableWidth = noGroups * 150;
        int tableHeight = 50 + membersPerGroup * 20;
        jspGroups.setBounds(40, 86, tableWidth, tableHeight);
        this.setBounds(this.getBounds().x, this.getBounds().y, 100 + tableWidth, 200 + tableHeight);
       
    }//GEN-LAST:event_btnRandomActionPerformed

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
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JF_MakeGroups.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JF_MakeGroups().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRandom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtNoMembersPerGroup;
    // End of variables declaration//GEN-END:variables
}
