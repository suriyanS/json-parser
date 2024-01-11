package com.suriyan_workflow86.jsonparser.model;

public class Student {
    private String name;
    private boolean needSupport;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeedSupport() {
        return needSupport;
    }

    public void setNeedSupport(boolean needSupport) {
        this.needSupport = needSupport;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", needSupport=" + needSupport +
                '}';
    }
}
