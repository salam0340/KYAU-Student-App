package com.example.kyaustudentapp.models;

import com.google.firebase.firestore.PropertyName;

public class Result {
    @PropertyName("Semester")
    private String semester;
    @PropertyName("gpa")
    private double gpa;
    @PropertyName("cgpa")
    private double cgpa;
    @PropertyName("CreditCompleted")
    private double creditCompleted;
    @PropertyName("grade")
    private String grade;

    public Result() {}

    @PropertyName("Semester")
    public String getSemester() { return semester; }
    @PropertyName("Semester")
    public void setSemester(String semester) { this.semester = semester; }
    @PropertyName("gpa")
    public double getGpa() { return gpa; }
    @PropertyName("gpa")
    public void setGpa(double gpa) { this.gpa = gpa; }
    @PropertyName("cgpa")
    public double getCgpa() { return cgpa; }
    @PropertyName("cgpa")
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
    @PropertyName("CreditCompleted")
    public double getCreditCompleted() { return (int) creditCompleted; }
    @PropertyName("CreditCompleted")
    public void setCreditCompleted(double creditCompleted) { this.creditCompleted = creditCompleted; }
    @PropertyName("grade")
    public String getGrade() { return grade; }
    @PropertyName("grade")
    public void setGrade(String grade) { this.grade = grade; }
}