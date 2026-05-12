package com.example.kyaustudentapp.models;

import com.google.firebase.firestore.PropertyName;

public class Course {
    @PropertyName("CourseCode")
    private String courseCode;
    @PropertyName("CourseName")
    private String courseName;
    @PropertyName("CourseTeacher")
    private String teacherName;
    @PropertyName("CreditHours")
    private double creditHours;
    @PropertyName("Schedule")
    private String schedule;
    @PropertyName("RoomNo")
    private Object roomNo;

    public Course() {}

    @PropertyName("CourseCode")
    public String getCourseCode() { return courseCode; }
    @PropertyName("CourseCode")
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    @PropertyName("CourseName")
    public String getCourseName() { return courseName; }
    @PropertyName("CourseName")
    public void setCourseName(String courseName) { this.courseName = courseName; }

    @PropertyName("CourseTeacher")
    public String getTeacherName() { return teacherName; }
    @PropertyName("CourseTeacher")
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    @PropertyName("CreditHours")
    public double getCreditHours() { return creditHours; }
    @PropertyName("CreditHours")
    public void setCreditHours(double creditHours) { this.creditHours = creditHours; }

    @PropertyName("Schedule")
    public String getSchedule() { return schedule; }
    @PropertyName("Schedule")
    public void setSchedule(String schedule) { this.schedule = schedule; }

    @PropertyName("RoomNo")
    public String getRoom() {
        return roomNo != null ? String.valueOf(roomNo) : "N/A";
    }
    @PropertyName("RoomNo")
    public void setRoomNo(Object roomNo) { this.roomNo = roomNo; }
}