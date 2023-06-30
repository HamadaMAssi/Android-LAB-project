package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
                    if(!flag) {
                        editor.putString("EMAIL", email);
                        editor.putString("PASS", password);
                        editor.putBoolean("FLAG", true);
                        editor.commit();
                    }
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