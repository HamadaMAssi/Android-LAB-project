package edu.birzeit.android_lab_project;

public class Trainee {
    private String Email_Address;
    private String First_Name;
    private String Last_Name;
    private String Password;
    private byte[] Personal_Photo;
    private String Mobile_Number;
    private String Address;

    public Trainee(String email_Address, String first_Name, String last_Name, String password, byte[] personal_Photo, String mobile_Number, String address) {
        Email_Address = email_Address;
        First_Name = first_Name;
        Last_Name = last_Name;
        Password = password;
        Personal_Photo = personal_Photo;
        Mobile_Number = mobile_Number;
        Address = address;
    }

    public String getEmail_Address() {
        return Email_Address;
    }

    public void setEmail_Address(String email_Address) {
        Email_Address = email_Address;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public byte[] getPersonal_Photo() {
        return Personal_Photo;
    }

    public void setPersonal_Photo(byte[] personal_Photo) {
        Personal_Photo = personal_Photo;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
