package com.example.quizapp;

public class Result {
    private String name;
    private int marks;

    public Result(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public Integer getMarks() {
        return marks;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", marks='" + marks + '\'' +
                '}';

    }
}
