package model;

import java.util.Objects;

public class Student implements Comparable<Student> {
    
    public String name;
    public boolean active;

    public Student(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    @Override
    public int compareTo(Student s) {
        return this.name.compareToIgnoreCase(s.name);
    }

}
