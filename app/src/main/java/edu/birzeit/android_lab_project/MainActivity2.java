package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private boolean flag = false;
    private EditText EmailAddress;
    private EditText Password;
    private CheckBox checkBox;
    private Button SignInButton;

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
                    if(!flag) {
                        editor.putString("EMAIL", email);
                        editor.putString("PASS", password);
                        editor.putBoolean("FLAG", true);
                        editor.commit();
                    }
                }
                DataBaseHelper databasehelper = new DataBaseHelper(MainActivity2.this, "train", null, 1);
                Cursor Admin_Data = databasehelper.getAdminByEmail(email);
                Cursor Trainee_Data = databasehelper.getTraineeByEmail(email);
                Cursor Instructor_Data = databasehelper.getInstructorByEmail(email);
                Admin_Data.moveToNext();
                Trainee_Data.moveToNext();
                Instructor_Data.moveToNext();
                if(Admin_Data.getString(0) != null){
                    Admin admin = new Admin(Admin_Data.getString(0),Admin_Data.getString(1),Admin_Data.getString(2),Admin_Data.getString(3),Admin_Data.getString(4));
                } else if (Trainee_Data.getString(0) != null) {
                    Trainee trainee = new Trainee(Trainee_Data.getString(0),Trainee_Data.getString(1),Trainee_Data.getString(2),Trainee_Data.getString(3),Trainee_Data.getString(4),Trainee_Data.getString(5),Trainee_Data.getString(6));
                } else if (Instructor_Data.getString(0) != null) {
                    Cursor Instructor_Courses = databasehelper.getInstCourses(email);
                    ArrayList<String> Courses = new ArrayList<String>();
                    while (Instructor_Courses.moveToNext()) {
                        Courses.add(Instructor_Courses.getString(0));
                    }
                    Instructor instructor = new Instructor(Instructor_Data.getString(0),Instructor_Data.getString(1),Instructor_Data.getString(2),Instructor_Data.getString(3),Instructor_Data.getString(4),Instructor_Data.getString(5),Instructor_Data.getString(6),Instructor_Data.getString(7),Instructor_Data.getString(8),Courses);
                }

                /*Intent intent = new Intent(MainActivity2.this,Level1.class);
                intent.putExtra("playerName", playerName);
                EndActivity.this.startActivity(intent);
                finish();*/
            }
        });
    }
}