package edu.birzeit.android_lab_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Course(ID SERIAL PRIMARY KEY, COURSE_TITLE TEXT UNIQUE, MAIN_TOPICS TEXT, PREREQUISITES TEXT,  PHOTO TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Admin(Email_Address TEXT PRIMARY KEY, First_Name TEXT, Last_Name TEXT,  Password TEXT, Personal_Photo TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Trainee(Email_Address TEXT PRIMARY KEY, First_Name TEXT, Last_Name TEXT,  Password TEXT, Personal_Photo TEXT, Mobile_Number TEXT, Address TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Instructor(Email_Address TEXT PRIMARY KEY, First_Name TEXT, Last_Name TEXT,  Password TEXT, Personal_Photo TEXT, Mobile_Number TEXT, Address TEXT, Specialization TEXT, Degree TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE InstCourses(COURSE_TITLE SERIAL, Email_Address SERIAL, PRIMARY KEY (COURSE_TITLE,Email_Address))");
        sqLiteDatabase.execSQL("CREATE TABLE Registration(ID SERIAL PRIMARY KEY, COURSE_TITLE TEXT, Email_Address TEXT, Deadline TEXT, Start_Date TEXT, schedule TEXT, venue TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE TraineeReg(Email_Address TEXT, Registration_ID SERIAL,Reg_State BOOLEAN, PRIMARY KEY (Email_Address,Registration_ID))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void newCourse(Course newCourse) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_TITLE", newCourse.getCOURSE_TITLE());
        contentValues.put("MAIN_TOPICS", newCourse.getMAIN_TOPICS());
        contentValues.put("PREREQUISITES", newCourse.getPREREQUISITES());
        contentValues.put("PHOTO", newCourse.getPHOTO());
        sqLiteDatabase.insert("Course", null, contentValues);
    }

    public void newAdmin(Admin newAdmin) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email_Address", newAdmin.getEmail_Address());
        contentValues.put("First_Name", newAdmin.getFirst_Name());
        contentValues.put("Last_Name", newAdmin.getLast_Name());
        contentValues.put("Password", newAdmin.getPassword());
        contentValues.put("Personal_Photo", newAdmin.getPersonal_Photo());
        sqLiteDatabase.insert("Admin", null, contentValues);
    }

    public void newTrainee(Trainee newTrainee) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email_Address", newTrainee.getEmail_Address());
        contentValues.put("First_Name", newTrainee.getFirst_Name());
        contentValues.put("Last_Name", newTrainee.getLast_Name());
        contentValues.put("Password", newTrainee.getPassword());
        contentValues.put("Personal_Photo", newTrainee.getPersonal_Photo());
        contentValues.put("Mobile_Number", newTrainee.getMobile_Number());
        contentValues.put("Address", newTrainee.getAddress());
        sqLiteDatabase.insert("Trainee", null, contentValues);
    }

    public void newInstructor(Instructor newInstructor) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email_Address", newInstructor.getEmail_Address());
        contentValues.put("First_Name", newInstructor.getFirst_Name());
        contentValues.put("Last_Name", newInstructor.getLast_Name());
        contentValues.put("Password", newInstructor.getPassword());
        contentValues.put("Personal_Photo", newInstructor.getPersonal_Photo());
        contentValues.put("Mobile_Number", newInstructor.getMobile_Number());
        contentValues.put("Address", newInstructor.getAddress());
        contentValues.put("Specialization", newInstructor.getSpecialization());
        contentValues.put("Degree", newInstructor.getDegree());
        sqLiteDatabase.insert("Instructor", null, contentValues);
    }

    public void newInstCourses(String COURSE_TITLE, String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_TITLE", COURSE_TITLE);
        contentValues.put("Email_Address", Email_Address);
        sqLiteDatabase.insert("InstCourses", null, contentValues);
    }

    public void newRegistration(Registration newRegistration) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_TITLE", newRegistration.getCOURSE_TITLE());
        contentValues.put("InstructorEmail", newRegistration.getInstructorEmail());
        contentValues.put("Deadline", newRegistration.getDeadline());
        contentValues.put("Start_Date", newRegistration.getStart_Date());
        contentValues.put("schedule", newRegistration.getSchedule());
        contentValues.put("venue", newRegistration.getVenue());
        sqLiteDatabase.insert("Registration", null, contentValues);
    }

    public void newTraineeReg(int trainee_ID, int Registration_ID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trainee_ID", trainee_ID);
        contentValues.put("Registration_ID", Registration_ID);
        sqLiteDatabase.insert("TraineeReg", null, contentValues);
    }

    public Cursor getCourses() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT COURSE_TITLE FROM Course" , null);
    }

    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Course", null);

        if (cursor.moveToFirst()) {
            do {
                String courseTitle = cursor.getString(0);
                String t = cursor.getString(1);
                String p = cursor.getString(2);
                String i = cursor.getString(3);
                Course course = new Course(courseTitle, t, p, i);
                courses.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return courses;
    }


    public Cursor getInstructors() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT Email_Address,First_Name,Last_Name FROM Instructor" , null);
    }
    public ArrayList<Instructor> getAllInstructors() {
        ArrayList<Instructor> instructors = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Instructor", null);

        if (cursor.moveToFirst()) {
            do {
                String email_Address = cursor.getString(0);
                String first_Name = cursor.getString(1);
                String last_Name = cursor.getString(2);
                String pass = cursor.getString(3);
                byte[] personal_Photo = cursor.getBlob(4);
                String mobile_Number = cursor.getString(5);
                String address = cursor.getString(6);
                String specialization = cursor.getString(7);
                String degree = cursor.getString(8);

                Instructor instructor = new Instructor(email_Address, first_Name, last_Name, pass, personal_Photo, mobile_Number, address, specialization, degree);
                instructors.add(instructor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return instructors;
    }

    public Cursor getTrainees() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Trainee" , null);
    }
    public ArrayList<Trainee> getAllTrainees() {
        ArrayList<Trainee> trainees = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Trainee", null);

        if (cursor.moveToFirst()) {
            do {
                String email_Address = cursor.getString(0);
                String first_Name = cursor.getString(1);
                String last_Name = cursor.getString(2);
                String pass = cursor.getString(3);
                byte[] personal_Photo = cursor.getBlob(4);
                String mobile_Number = cursor.getString(5);
                String address = cursor.getString(6);

                Trainee trainee = new Trainee(email_Address, first_Name, last_Name, pass, personal_Photo, mobile_Number, address);
                trainees.add(trainee);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return trainees;
    }

    public Cursor getCourseInfo(String COURSE_TITLE) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Course WHERE COURSE_TITLE = '" + COURSE_TITLE + "';", null);
    }

    public Void updateCourse(Course newCourse, String COURSE_TITLE) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_TITLE", newCourse.getCOURSE_TITLE());
        contentValues.put("MAIN_TOPICS", newCourse.getMAIN_TOPICS());
        contentValues.put("PREREQUISITES", newCourse.getPREREQUISITES());
        contentValues.put("PHOTO", newCourse.getPHOTO());
        String[] args = {COURSE_TITLE};
        sqLiteDatabase.update( "Course",contentValues,"COURSE_TITLE = ?", args);
        return null;
    }

    public Void deleteCourse(String COURSE_TITLE) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] args = {COURSE_TITLE};
        sqLiteDatabase.delete( "Course","COURSE_TITLE = ?", args);
        return null;
    }

    public Cursor getAdminByEmail(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Admin WHERE Email_Address = '" + Email_Address + "';", null);
    }

    public Admin getAdminObjectByEmail(String Email_Address) {
        String emailAddress= "";
        String firstName= "";
        String lastName= "";
        String password = "";
        byte[] imageData = new byte[0];
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Admin WHERE Email_Address = '" + Email_Address + "';", null);
        if (cursor.moveToFirst()) {
            emailAddress = cursor.getString(0);
            firstName = cursor.getString(1);
            lastName = cursor.getString(2);
            password = cursor.getString(3);
            imageData = cursor.getBlob(4);
        }
        cursor.close();
        return new Admin(emailAddress, firstName, lastName, password, imageData);
    }

    public Cursor getTraineeByEmail(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Trainee WHERE Email_Address = '" + Email_Address + "';", null);
    }

    public Trainee getTraineeObjectByEmail(String Email_Address) {
        String emailAddress= "";
        String firstName= "";
        String lastName= "";
        String password = "";
        byte[] imageData = new byte[0];
        String Mobile_Number = "";
        String Address="";

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Trainee WHERE Email_Address = '" + Email_Address + "';", null);
        if (cursor.moveToFirst()) {
            emailAddress = cursor.getString(0);
            firstName = cursor.getString(1);
            lastName = cursor.getString(2);
            password = cursor.getString(3);
            imageData = cursor.getBlob(4);
            Mobile_Number = cursor.getString(5);
            Address = cursor.getString(6);
        }
        cursor.close();
        return new Trainee(emailAddress, firstName, lastName, password, imageData, Mobile_Number, Address);
    }

    public Cursor getInstructorByEmail(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Instructor WHERE Email_Address = '" + Email_Address + "';", null);
    }

//    public Instructor getInstructorObjectByEmail(String Email_Address) {
//        String emailAddress= "";
//        String firstName= "";
//        String lastName= "";
//        String password = "";
//        byte[] imageData = new byte[0];
//        String Mobile_Number = "";
//        String Address="";
//        String Specialization="";
//        String Degree="";
//        ArrayList<String> Courses = new ArrayList<>();
//
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Instructor WHERE Email_Address = '" + Email_Address + "';", null);
//        if (cursor.moveToFirst()) {
//            emailAddress = cursor.getString(0);
//            firstName = cursor.getString(1);
//            lastName = cursor.getString(2);
//            password = cursor.getString(3);
//            imageData = cursor.getBlob(4);
//            Mobile_Number = cursor.getString(5);
//            Address = cursor.getString(6);
//            Specialization = cursor.getString(7);
//            Degree = cursor.getString(8);
//            String coursesString = cursor.getString(9); // Assuming the column index is 9
//
//            if (coursesString != null) {
//                String[] courseArray = coursesString.split(","); // Split the string by comma
//                Courses.addAll(Arrays.asList(courseArray));
//            }
//        }
//        cursor.close();
//        return new Instructor(emailAddress, firstName, lastName, password, imageData, Mobile_Number, Address, Specialization, Degree,Courses);
//    }

    public Cursor getInstCourses(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT COURSE_TITLE FROM InstCourses WHERE Email_Address = '" + Email_Address + "';", null);
    }

}
