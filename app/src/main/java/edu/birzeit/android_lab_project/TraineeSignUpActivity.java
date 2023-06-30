package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TraineeSignUpActivity extends AppCompatActivity {

    private ImageView back;
    private Button signUpButton, cancelButton, adminSignUpButton, instructorSignUpButton;
    private EditText emailText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private EditText mobileNumberText;
    private EditText addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        signUpButton = (Button) findViewById(R.id.signUpButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        adminSignUpButton = (Button) findViewById(R.id.adminSignUpButton);
        instructorSignUpButton = (Button) findViewById(R.id.instructorSignUpButton);
        back = findViewById(R.id.back);

        emailText = (EditText) findViewById(R.id.emailText);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPasswordText);
        mobileNumberText = (EditText) findViewById(R.id.mobileNumberText);
        addressText = (EditText) findViewById(R.id.addressText);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraineeSignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraineeSignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        adminSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraineeSignUpActivity.this,AdminSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        instructorSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraineeSignUpActivity.this,TraineeSignUpActivity.class);
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
                DataBaseHelper databasehelper = new DataBaseHelper(TraineeSignUpActivity.this, "train", null, 1);
                Cursor Admin_Data = databasehelper.getAdminByEmail(email);
                Cursor Trainee_Data = databasehelper.getTraineeByEmail(email);
                Cursor Instructor_Data = databasehelper.getInstructorByEmail(email);
                if(!Admin_Data.moveToNext() && !Trainee_Data.moveToNext() && !Instructor_Data.moveToNext()){
                    if (password.equals(confirmPassword)){
                        if(validatePassword(password) && validateName(firstName) && validateName(lastName) && validateEmail(email)){
                            Trainee trainee = new Trainee(email,firstName,lastName,password,"no photo",mobileNumber,address);
                            databasehelper.newTrainee(trainee);
                            Intent intent = new Intent(TraineeSignUpActivity.this,TraineeMainActivity.class);
                            intent.putExtra("email", trainee.getEmail_Address());
                            intent.putExtra("firstName", trainee.getFirst_Name());
                            intent.putExtra("lastName", trainee.getLast_Name());
                            intent.putExtra("password", trainee.getPassword());
                            intent.putExtra("mobileNumber", trainee.getMobile_Number());
                            intent.putExtra("address", trainee.getAddress());
                            TraineeSignUpActivity.this.startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                                "Confirm password is incorrect",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else {
                    Toast toast =Toast.makeText(TraineeSignUpActivity.this,
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
            Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                    "password must be at least 8 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (passSize > 15) {
            Toast toast =Toast.makeText(TraineeSignUpActivity.this,
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
                    Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                            "password must contain at least one number",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }else {
                Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                        "password must contain at least one uppercase letter",Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }

        }else {
            Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                    "password must contain at least one lowercase letter",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

    }

    public boolean validateName(String name) {

        int nameSize = name.length();
        if(nameSize < 3){
            Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                    "first/last name must be at least 3 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (nameSize > 20) {
            Toast toast =Toast.makeText(TraineeSignUpActivity.this,
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
            Toast toast =Toast.makeText(TraineeSignUpActivity.this,
                    "email format is wrong",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

    }
}