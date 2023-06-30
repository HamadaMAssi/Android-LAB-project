package edu.birzeit.android_lab_project;

import java.util.ArrayList;

public class Registration {
    private String COURSE_TITLE;
    private String Instructor_Name;
    private String Deadline;
    private String Start_Date;
    private String schedule;
    private String venue;

    private ArrayList<String> Students;

    public Registration(String COURSE_TITLE, String instructor_Name, String deadline, String start_Date, String schedule, String venue) {
        this.COURSE_TITLE = COURSE_TITLE;
        Instructor_Name = instructor_Name;
        Deadline = deadline;
        Start_Date = start_Date;
        this.schedule = schedule;
        this.venue = venue;
        Students = new ArrayList<String>();
    }

    public String getCOURSE_TITLE() {
        return COURSE_TITLE;
    }

    public void setCOURSE_TITLE(String COURSE_TITLE) {
        this.COURSE_TITLE = COURSE_TITLE;
    }

    public String getInstructor_Name() {
        return Instructor_Name;
    }

    public void setInstructor_Name(String instructor_Name) {
        Instructor_Name = instructor_Name;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public ArrayList<String> getStudents() {
        return Students;
    }

    public void setStudents(ArrayList<String> students) {
        Students = students;
    }
}
