package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import model.Course;
import model.Student;
import view.JF_Groups;

public class Controller {

    private Course myCourse;

    public Controller(Course myCourse) {
        this.myCourse = myCourse;
    }

    public void readStudentsFromFile(File studentFile) throws FileNotFoundException, IOException {
        String line, name;
        
        myCourse.clearStudents();

        String fileName = studentFile.getName();
        int fnExtensionStart = fileName.lastIndexOf(".");
        myCourse.setCourseID(fileName.substring(0, fnExtensionStart));
        
        Scanner sc = new Scanner(studentFile);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.contains("\"") && line.indexOf("\"") != line.lastIndexOf("\"")) {  // "..."
                name = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));                
                myCourse.addStudent(name, true);     // default: active
            }
        }
    }

    public int getNoStudents() {
        return myCourse.getNoStudents();
    }
    public int getNoActiveStudents() {
        return myCourse.getNoActiveStudents();
    }

    public Student getStudent(int row) {
        return myCourse.getStudent(row);
    }
    
    public List<Student> getActiveStudents() {
        return myCourse.getActiveStudents();
    }
    
    public void shuffleStudents() {
        myCourse.shuffleStudents();
    }

    public void move2end(Student s){
        myCourse.move2end(s);
    }

    public String getCourseID() {
        return myCourse.getCourseID();
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

}
