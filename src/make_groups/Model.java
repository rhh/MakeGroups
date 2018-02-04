package make_groups;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {

    List<Student> students = new ArrayList<>();

    public void clearStudents() {
        students.clear();
    }

    public void addStudent(String name, boolean active) {
        students.add(new Student(name, active));
    }

    public List<Student> getActiveStudents() {
        List<Student> activeStudents = new ArrayList<>();

        for (Student s : students) {
            if (s.active) {
                activeStudents.add(s);
            }
        }
        return activeStudents;
    }

    public int getNoStudents() {
        return students.size();
    }

    public int getNoActiveStudents() {
        int count = 0;
        for (Student s : students) {
            if (s.active) {
                count++;
            }
        }
        return count;
    }

    public Student getStudent(int idx) {
        return students.get(idx);
    }

    public void shuffleStudents() {
        Random zzg = new Random();
        int foo, bar;
        Student tmp;

        for (int i = 0; i < 2 * students.size(); i++) {
            foo = zzg.nextInt(students.size());
            bar = zzg.nextInt(students.size());
            tmp = students.get(foo);
            students.set(foo, students.get(bar));
            students.set(bar, tmp);
        }
    }

}
