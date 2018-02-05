package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import model.Course;
import model.Student;

public class Controller {

    private Course myCourse;

    public Controller(Course myCourse) {
        this.myCourse = myCourse;
    }

    public void readStudentsFromFile(File studentFile) throws FileNotFoundException, IOException {
        String line, name;
        myCourse.clearStudents();

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
    
    

}
