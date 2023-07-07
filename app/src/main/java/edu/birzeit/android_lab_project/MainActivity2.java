package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private boolean flag = false;
    private EditText EmailAddress;
    private EditText Password;
    private CheckBox checkBox;
    private Button SignInButton;
    private Button SignUpButton;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();

        EmailAddress = (EditText) findViewById(R.id.EmailAddress);
        Password = (EditText) findViewById(R.id.Password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        flag = prefs.getBoolean("FLAG", false);

        if(flag){
            String name = prefs.getString("EMAIL", "");
            String password = prefs.getString("PASS", "");
            EmailAddress.setText(name);
            Password.setText(password);
            checkBox.setChecked(true);
        }

        SignInButton = (Button) findViewById(R.id.SignInButton);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = EmailAddress.getText().toString();
                String password = Password.getText().toString();

                if(checkBox.isChecked()){
                        editor.putString("EMAIL", email);
                        editor.putString("PASS", password);
                        editor.putBoolean("FLAG", true);
                        editor.commit();
                }
                DataBaseHelper databasehelper = new DataBaseHelper(MainActivity2.this, "train", null, 1);
                Cursor Admin_Data = databasehelper.getAdminByEmail(email);
                Cursor Trainee_Data = databasehelper.getTraineeByEmail(email);
                Cursor Instructor_Data = databasehelper.getInstructorByEmail(email);

                if(Admin_Data.moveToNext()){
                    System.out.println(password+"---"+Admin_Data.getString(3));
                    if (password.equals(Admin_Data.getString(3))){
                        Admin admin = new Admin(Admin_Data.getString(0),Admin_Data.getString(1),Admin_Data.getString(2),Admin_Data.getString(3),Admin_Data.getBlob(4));
                        Intent intent = new Intent(MainActivity2.this,AdminMainActivity.class);
                        intent.putExtra("email", admin.getEmail_Address());
                        intent.putExtra("firstName", admin.getFirst_Name());
                        intent.putExtra("lastName", admin.getLast_Name());
                        intent.putExtra("password", admin.getPassword());
                        intent.putExtra("photo", admin.getPersonal_Photo());
                        MainActivity2.this.startActivity(intent);
                        finish();
                    } else {
                        Toast toast =Toast.makeText(MainActivity2.this,
                                "Wrong Password \n Try Again!",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } else if (Trainee_Data.moveToNext()) {
                    if (password.equals(Trainee_Data.getString(3))){
                        Trainee trainee = new Trainee(Trainee_Data.getString(0),Trainee_Data.getString(1),Trainee_Data.getString(2),Trainee_Data.getString(3),Trainee_Data.getBlob(4),Trainee_Data.getString(5),Trainee_Data.getString(6));
                        Intent intent = new Intent(MainActivity2.this,TraineeMainActivity.class);
                        intent.putExtra("email", trainee.getEmail_Address());
                        intent.putExtra("firstName", trainee.getFirst_Name());
                        intent.putExtra("lastName", trainee.getLast_Name());
                        intent.putExtra("password", trainee.getPassword());
                        intent.putExtra("mobileNumber", trainee.getMobile_Number());
                        intent.putExtra("address", trainee.getAddress());
                        MainActivity2.this.startActivity(intent);
                        finish();
                    } else {
                        Toast toast =Toast.makeText(MainActivity2.this,
                                "Wrong Password \n Try Again!",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } else if (Instructor_Data.moveToNext()) {
                    if (password.equals(Instructor_Data.getString(3))){
                        Cursor Instructor_Courses = databasehelper.getInstCourses(email);
                        ArrayList<String> Courses = new ArrayList<String>();
                        while (Instructor_Courses.moveToNext()) {
                            Courses.add(Instructor_Courses.getString(0));
                        }
                        Instructor instructor = new Instructor(Instructor_Data.getString(0),Instructor_Data.getString(1),Instructor_Data.getString(2),Instructor_Data.getString(3),Instructor_Data.getBlob(4),Instructor_Data.getString(5),Instructor_Data.getString(6),Instructor_Data.getString(7),Instructor_Data.getString(8),Courses);
                        Intent intent = new Intent(MainActivity2.this,InstructorMainActivity.class);
                        intent.putExtra("email", instructor.getEmail_Address());
                        intent.putExtra("firstName", instructor.getFirst_Name());
                        intent.putExtra("lastName", instructor.getLast_Name());
                        intent.putExtra("password", instructor.getPassword());
                        intent.putExtra("mobileNumber", instructor.getMobile_Number());
                        intent.putExtra("address", instructor.getAddress());
                        intent.putExtra("specialization", instructor.getSpecialization());
                        intent.putExtra("degree", instructor.getDegree());
                        MainActivity2.this.startActivity(intent);
                        finish();
                    } else {
                        Toast toast =Toast.makeText(MainActivity2.this,
                                "Wrong Password \n Try Again!",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                } else {
                    Toast toast =Toast.makeText(MainActivity2.this,
                            "Wrong Email\n Try Again!",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        SignUpButton = (Button) findViewById(R.id.SignUpButton);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Inflate the popup window layout
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View viewPopupwindow = layoutInflater.inflate(R.layout.popup_window, null);

                // Find the buttons in popupwindow
                Button studentSignUpButtonPopup = viewPopupwindow.findViewById(R.id.studentSignUpButton);
                Button instructorSignUpButtonPopup = viewPopupwindow.findViewById(R.id.instructorSignUpButton);
                Button adminSignUpButtonPopup = viewPopupwindow.findViewById(R.id.adminSignUpButton);

                studentSignUpButtonPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity2.this,TraineeSignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                instructorSignUpButtonPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity2.this,InstructorSignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                adminSignUpButtonPopup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity2.this,AdminSignUpActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                // Create and show the popup window
                popupWindow = new PopupWindow(viewPopupwindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });
    }
}