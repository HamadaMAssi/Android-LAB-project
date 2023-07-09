package edu.birzeit.android_lab_project;

import java.util.ArrayList;

public class Registration {

    private int Registration_ID;
    private String COURSE_TITLE;
    private String InstructorEmail;
    private String Deadline;
    private String Start_Date;
    private String schedule;
    private String venue;

    private ArrayList<String> Students;

    public Registration(int Registration_ID, String COURSE_TITLE, String instructorEmail, String deadline, String start_Date, String schedule, String venue) {
        this.COURSE_TITLE = COURSE_TITLE;
        InstructorEmail = instructorEmail;
        Deadline = deadline;
        Start_Date = start_Date;
        this.schedule = schedule;
        this.venue = venue;
        Students = new ArrayList<String>();
    }

    public int getRegistration_ID() {
        return Registration_ID;
    }

    public void setRegistration_ID(int registration_ID) {
        Registration_ID = registration_ID;
    }

    public String getCOURSE_TITLE() {
        return COURSE_TITLE;
    }

    public void setCOURSE_TITLE(String COURSE_TITLE) {
        this.COURSE_TITLE = COURSE_TITLE;
    }

    public String getInstructorEmail() {
        return InstructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        InstructorEmail = instructorEmail;
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
