package com.suriyan_workflow86.jsonparser.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int group;
    private List<Student> students;

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Group{" +
                "group=" + group +
                ", students=" + students +
                '}';
    }
}
