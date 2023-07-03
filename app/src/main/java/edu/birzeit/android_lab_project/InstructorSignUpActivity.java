package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructorSignUpActivity extends AppCompatActivity {

    private ImageView back;
    private Button signUpButton, cancelButton, adminSignUpButton, studentSignUpButton;
    private LinearLayout CoursesLayout;
    private EditText emailText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private EditText mobileNumberText;
    private EditText addressText;
    private EditText specializationText;
    private RadioGroup degreeRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        adminSignUpButton = (Button) findViewById(R.id.adminSignUpButton);
        studentSignUpButton = (Button) findViewById(R.id.studentSignUpButton);
        back = findViewById(R.id.back);
        CoursesLayout = (LinearLayout) findViewById(R.id.CoursesLayout);

        emailText = (EditText) findViewById(R.id.emailText);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPasswordText);
        mobileNumberText = (EditText) findViewById(R.id.mobileNumberText);
        addressText = (EditText) findViewById(R.id.addressText);
        specializationText = (EditText) findViewById(R.id.specializationText);
        degreeRadioGroup = (RadioGroup) findViewById(R.id.degreeRadioGroup);

        DataBaseHelper databasehelper = new DataBaseHelper(InstructorSignUpActivity.this, "train", null, 1);
        Cursor Courses_Data = databasehelper.getCourses();
        ArrayList<String> Courses = new ArrayList<String>();
        while (Courses_Data.moveToNext()){
            Courses.add(Courses_Data.getString(0));
        }
        CoursesLayout.removeAllViews();

        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        for (int i = 0; i<Courses.size(); i+=2){
            LinearLayout twoCourses = new LinearLayout(InstructorSignUpActivity.this);
            twoCourses.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            twoCourses.setLayoutParams(params);
            twoCourses.setVisibility(View.VISIBLE);
            if (Courses.size() > 1  && !Courses.get(i).isEmpty() && !Courses.get(i+1).isEmpty()){
                CheckBox Course1 = new CheckBox(InstructorSignUpActivity.this);
                Course1.setText(Courses.get(i));
                Course1.setChecked(false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1.0f;
                Course1.setLayoutParams(layoutParams);
                CheckBox Course2 = new CheckBox(InstructorSignUpActivity.this);
                Course2.setText(Courses.get(i+1));
                Course2.setChecked(false);
                Course2.setLayoutParams(layoutParams);
                twoCourses.addView(Course1,layoutParams);
                twoCourses.addView(Course2,layoutParams);
                checkBoxes.add(Course1);
                checkBoxes.add(Course2);
            } else {
                CheckBox Course1 = new CheckBox(InstructorSignUpActivity.this);
                Course1.setText(Courses.get(i));
                Course1.setChecked(false);
                Course1.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1.0f;
                Course1.setLayoutParams(layoutParams);
                twoCourses.addView(Course1);
                checkBoxes.add(Course1);
            }
            CoursesLayout.addView(twoCourses,params);

        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorSignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorSignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        adminSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorSignUpActivity.this,AdminSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        studentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorSignUpActivity.this,TraineeSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailText.getText().toString();
                String firstName = firstNameText.getText().toString();
                String lastName = lastNameText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();
                String mobileNumber = mobileNumberText.getText().toString();
                String address = addressText.getText().toString();
                String specialization = specializationText.getText().toString();
                DataBaseHelper databasehelper = new DataBaseHelper(InstructorSignUpActivity.this, "train", null, 1);
                Cursor Admin_Data = databasehelper.getAdminByEmail(email);
                Cursor Trainee_Data = databasehelper.getTraineeByEmail(email);
                Cursor Instructor_Data = databasehelper.getInstructorByEmail(email);
                if(!Admin_Data.moveToNext() && !Trainee_Data.moveToNext() && !Instructor_Data.moveToNext()){
                    if (password.equals(confirmPassword)){
                        if(validatePassword(password) && validateName(firstName) && validateName(lastName) && validateEmail(email)){
                            int selectedDegree = degreeRadioGroup.getCheckedRadioButtonId();
                            if(selectedDegree != -1){
                                RadioButton radioButton = (RadioButton) findViewById(selectedDegree);
                                String degree = radioButton.getText().toString();
                                //ArrayList<String> Courses = getInstCources(checkBoxes);
                                Instructor instructor = new Instructor(email,firstName,lastName,password,"no photo",mobileNumber,address,specialization,degree,Courses);
                                databasehelper.newInstructor(instructor);
                                for (int j=0; j<Courses.size(); j++){
                                    databasehelper.newInstCourses(Courses.get(j),email);
                                }
                                Intent intent = new Intent(InstructorSignUpActivity.this,InstructorMainActivity.class);
                                intent.putExtra("email", instructor.getEmail_Address());
                                intent.putExtra("firstName", instructor.getFirst_Name());
                                intent.putExtra("lastName", instructor.getLast_Name());
                                intent.putExtra("password", instructor.getPassword());
                                intent.putExtra("mobileNumber", instructor.getMobile_Number());
                                intent.putExtra("address", instructor.getAddress());
                                intent.putExtra("specialization", instructor.getSpecialization());
                                intent.putExtra("degree", instructor.getDegree());
                                InstructorSignUpActivity.this.startActivity(intent);
                                finish();
                            } else {
                                Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                                        "Please select your degree",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    } else {
                        Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                                "Confirm password is incorrect",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else {
                    Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                            "This email has been used by another user",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    public boolean validatePassword(String password) {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasNumber = false;

        int passSize = password.length();
        if(passSize < 8){
            Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                    "password must be at least 8 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (passSize > 15) {
            Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                    "password must be at most 15 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        for (char ch : password.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            }

        }
        if (hasLowerCase ) {
            if (hasUpperCase){
                if (hasNumber){
                    return true;
                }else {
                    Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                            "password must contain at least one number",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }else {
                Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                        "password must contain at least one uppercase letter",Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }

        }else {
            Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                    "password must contain at least one lowercase letter",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

    }

    public boolean validateName(String name) {

        int nameSize = name.length();
        if(nameSize < 3){
            Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                    "first/last name must be at least 3 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (nameSize > 20) {
            Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                    "first/last name must be at most 20 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        return true;

    }

    public boolean validateEmail(String email) {

        String pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(email);

        if(matcher.matches()){
            return true;
        } else{
            Toast toast =Toast.makeText(InstructorSignUpActivity.this,
                    "email format is wrong",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

    }

    public ArrayList<String> getInstCources(ArrayList<CheckBox> checkBoxes){
        ArrayList<String> Courses = new ArrayList<String>();
        for(int i = 0; i<checkBoxes.size(); i++){
            if (checkBoxes.get(i).isChecked()){
                Courses.add(checkBoxes.get(i).getText().toString());
            }
        }
        return Courses;
    }
}