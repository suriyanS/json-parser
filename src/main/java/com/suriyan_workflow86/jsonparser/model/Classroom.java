package com.suriyan_workflow86.jsonparser.model;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    public String className;
    public String description;
    public int numberOfStudents;
    public List<Group> groups;
    public List<Student> naughtyList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Student> getNaughtyList() {
        return naughtyList;
    }

    public void setNaughtyList(List<Student> naughtyList) {
        this.naughtyList = naughtyList;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "className='" + className + '\'' +
                ", description='" + description + '\'' +
                ", numberOfStudents=" + numberOfStudents +
                ", groups=" + groups +
                ", naughtyList=" + naughtyList +
                '}';
    }
}
