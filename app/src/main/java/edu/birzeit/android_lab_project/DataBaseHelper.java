package edu.birzeit.android_lab_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Course(ID SERIAL PRIMARY KEY,COURSE_TITLE TEXT UNIQUE, MAIN_TOPICS TEXT, PREREQUISITES TEXT,  PHOTO TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Admin(ID SERIAL PRIMARY KEY,Email_Address TEXT UNIQUE, First_Name TEXT, Last_Name TEXT,  Password TEXT, Personal_Photo TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Trainee(ID SERIAL PRIMARY KEY,Email_Address TEXT UNIQUE, First_Name TEXT, Last_Name TEXT,  Password TEXT, Personal_Photo TEXT, Mobile_Number TEXT, Address TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE Instructor(ID SERIAL PRIMARY KEY,Email_Address TEXT UNIQUE, First_Name TEXT, Last_Name TEXT,  Password TEXT, Personal_Photo TEXT, Mobile_Number TEXT, Address TEXT, Specialization TEXT, Degree TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE InstCourses(COURSE_TITLE SERIAL, Email_Address SERIAL, PRIMARY KEY (COURSE_TITLE,Email_Address))");
        sqLiteDatabase.execSQL("CREATE TABLE Registration(ID SERIAL PRIMARY KEY, COURSE_TITLE TEXT, Instructor_Name TEXT, Deadline TEXT, Start_Date TEXT, schedule TEXT, venue TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE TraineeReg(ID SERIAL, trainee_ID SERIAL, Registration_ID SERIAL, PRIMARY KEY (trainee_ID,Registration_ID))");

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

    public void newInstCourses(int COURSE_ID, int instructor_ID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_ID", COURSE_ID);
        contentValues.put("instructor_ID", instructor_ID);
        sqLiteDatabase.insert("InstCourses", null, contentValues);
    }

    public void newRegistration(Registration newRegistration) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COURSE_TITLE", newRegistration.getCOURSE_TITLE());
        contentValues.put("Instructor_Name", newRegistration.getInstructor_Name());
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

    public Cursor getTraineeByEmail(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Trainee WHERE Email_Address = '" + Email_Address + "';", null);
    }

    public Cursor getInstructorByEmail(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM Instructor WHERE Email_Address = '" + Email_Address + "';", null);
    }

    public Cursor getInstCourses(String Email_Address) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT COURSE_TITLE FROM InstCourses WHERE Email_Address = '" + Email_Address + "';", null);
    }

}
