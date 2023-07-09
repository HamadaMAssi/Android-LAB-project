package edu.birzeit.android_lab_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminSignUpActivity extends AppCompatActivity {

    private ImageView back, imageViewPhoto;
    private Button signUpButton, cancelButton, instructorSignUpButton, studentSignUpButton;
    private EditText emailText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText passwordText;
    private EditText confirmPasswordText;
    private LinearLayout personalPhoto;
    private TextView textPhoto;

    private Uri imageUri;
    private static final int GALLERY_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signUpButton = (Button) findViewById(R.id.signUpButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        instructorSignUpButton = (Button) findViewById(R.id.instructorSignUpButton);
        studentSignUpButton = (Button) findViewById(R.id.studentSignUpButton);
        back = findViewById(R.id.back);

        emailText = (EditText) findViewById(R.id.emailText);
        firstNameText = (EditText) findViewById(R.id.firstNameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) findViewById(R.id.confirmPasswordText);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSignUpActivity.this,MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        instructorSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSignUpActivity.this,AdminSignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        studentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminSignUpActivity.this,TraineeSignUpActivity.class);
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
//                String photo = imageUri.toString();
                byte[] imageData = new byte[0];
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    imageData = getBytesFromInputStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataBaseHelper databasehelper = new DataBaseHelper(AdminSignUpActivity.this, "train", null, 1);
                Cursor Admin_Data = databasehelper.getAdminByEmail(email);
                Cursor Trainee_Data = databasehelper.getTraineeByEmail(email);
                Cursor Instructor_Data = databasehelper.getInstructorByEmail(email);
                if(!Admin_Data.moveToNext() && !Trainee_Data.moveToNext() && !Instructor_Data.moveToNext()){
                    if (password.equals(confirmPassword)){
                        if(validatePassword(password) && validateName(firstName) && validateName(lastName) && validateEmail(email) && validatePhoto(imageData)){
                            Admin admin = new Admin(email,firstName,lastName,password,imageData);
                            databasehelper.newAdmin(admin);
                            Intent intent = new Intent(AdminSignUpActivity.this,AdminMainActivity.class);
                            intent.putExtra("email", admin.getEmail_Address());
                            intent.putExtra("firstName", admin.getFirst_Name());
                            intent.putExtra("lastName", admin.getLast_Name());
                            intent.putExtra("password", admin.getPassword());
                            intent.putExtra("photo", admin.getPersonal_Photo());
                            AdminSignUpActivity.this.startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast toast =Toast.makeText(AdminSignUpActivity.this,
                                "Confirm password is incorrect",Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else {
                    Toast toast =Toast.makeText(AdminSignUpActivity.this,
                            "This email has been used by another user",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        personalPhoto = findViewById(R.id.personalPhoto);
        personalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, bytesRead);
        }
        return byteBuffer.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();
        textPhoto = findViewById(R.id.textPhoto);
        textPhoto.setText(imageUri.toString());
        imageViewPhoto = findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setImageURI(imageUri);
    }

    public boolean validatePassword(String password) {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasNumber = false;

        int passSize = password.length();
        if(passSize < 8){
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
                    "password must be at least 8 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (passSize > 15) {
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
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
                    Toast toast =Toast.makeText(AdminSignUpActivity.this,
                            "password must contain at least one number",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }else {
                Toast toast =Toast.makeText(AdminSignUpActivity.this,
                        "password must contain at least one uppercase letter",Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
            
        }else {
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
                    "password must contain at least one lowercase letter",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

    }

    public boolean validateName(String name) {

        int nameSize = name.length();
        if(nameSize < 3){
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
                    "first/last name must be at least 3 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (nameSize > 20) {
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
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
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
                    "email format is wrong",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    public boolean validatePhoto(byte [] p) {
        if(p == null || p.length == 0 ){
            Toast toast =Toast.makeText(AdminSignUpActivity.this,
                    "photo is empty",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else{
            return true;
        }
    }
}