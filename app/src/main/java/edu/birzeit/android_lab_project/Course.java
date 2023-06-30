package edu.birzeit.android_lab_project;

public class Course {
    private String COURSE_TITLE;
    private String MAIN_TOPICS;
    private String PREREQUISITES;
    private String PHOTO;

    public Course(String COURSE_TITLE, String MAIN_TOPICS, String PREREQUISITES, String PHOTO) {
        this.COURSE_TITLE = COURSE_TITLE;
        this.MAIN_TOPICS = MAIN_TOPICS;
        this.PREREQUISITES = PREREQUISITES;
        this.PHOTO = PHOTO;
    }

    public String getCOURSE_TITLE() {
        return COURSE_TITLE;
    }

    public void setCOURSE_TITLE(String COURSE_TITLE) {
        this.COURSE_TITLE = COURSE_TITLE;
    }

    public String getMAIN_TOPICS() {
        return MAIN_TOPICS;
    }

    public void setMAIN_TOPICS(String MAIN_TOPICS) {
        this.MAIN_TOPICS = MAIN_TOPICS;
    }

    public String getPREREQUISITES() {
        return PREREQUISITES;
    }

    public void setPREREQUISITES(String PREREQUISITES) {
        this.PREREQUISITES = PREREQUISITES;
    }

    public String getPHOTO() {
        return PHOTO;
    }

    public void setPHOTO(String PHOTO) {
        this.PHOTO = PHOTO;
    }
}
