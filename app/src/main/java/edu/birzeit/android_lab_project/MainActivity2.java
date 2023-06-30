package edu.birzeit.android_lab_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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


            }
        });
    }
}