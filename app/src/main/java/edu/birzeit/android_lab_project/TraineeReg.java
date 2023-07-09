package edu.birzeit.android_lab_project;

public class TraineeReg {
    private String Email_Address;
    private String Trainee_Name;
    private int Registration_ID;
    private String COURSE_TITLE;
    private String Reg_State;

    public TraineeReg(String email_Address, String trainee_Name, int registration_ID, String COURSE_TITLE, String reg_State) {
        Email_Address = email_Address;
        Trainee_Name = trainee_Name;
        Registration_ID = registration_ID;
        this.COURSE_TITLE = COURSE_TITLE;
        Reg_State = reg_State;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getTrainee_Name() {
        return Trainee_Name;
    }

    public void setTrainee_Name(String trainee_Name) {
        Trainee_Name = trainee_Name;
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

    public String getReg_State() {
        return Reg_State;
    }

    public void setReg_State(String reg_State) {
        Reg_State = reg_State;
    }
}
