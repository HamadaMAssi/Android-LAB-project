package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

public class TraineeSignUpActivity extends AppCompatActivity {

    private ImageView back;
    private Button signUpButton, cancelButton, adminSignUpButton, instructorSignUpButton;

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
    }
}