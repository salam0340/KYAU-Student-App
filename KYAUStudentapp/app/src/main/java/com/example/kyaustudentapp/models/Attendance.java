package com.example.kyaustudentapp.models;

import com.google.firebase.firestore.PropertyName;

public class Attendance {
    @PropertyName("CourseName")
    public String courseName;
    @PropertyName("CourseCode")
    public String courseCode;
    @PropertyName("TotalClasses")
    private int totalClasses;
    @PropertyName("AttendedClasses")
    private int attendedClasses;
    @PropertyName("Percentage")
    private double percentage;

    public Attendance() {}

    @PropertyName("CourseCode")
    public String getCourseCode() { return courseCode; }
    @PropertyName("CourseCode")
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }
    @PropertyName("CourseName")
    public String getCourseName() { return courseName; }
    @PropertyName("CourseName")
    public void setCourseName(String courseName) { this.courseName = courseName; }
    @PropertyName("TotalClasses")
    public int getTotalClasses() { return totalClasses; }
    @PropertyName("TotalClasses")
    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }
    @PropertyName("AttendedClasses")
    public int getAttendedClasses() { return attendedClasses; }
    @PropertyName("AttendedClasses")
    public void setAttendedClasses(int attendedClasses) { this.attendedClasses = attendedClasses; }
    @PropertyName("Percentage")
    public double getPercentage() { return percentage; }
    @PropertyName("Percentage")
    public void setPercentage(double percentage) { this.percentage = percentage; }
}