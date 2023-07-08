package edu.birzeit.android_lab_project;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InstructorProfileFragment extends Fragment {

    private ImageView imageViewPhoto;
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
    private ImageView personalPhoto;
    private Uri imageUri;
    private static final int GALLERY_REQUEST_CODE = 123;
    private DataBaseHelper databasehelper;

    private Button SaveModificationsButton;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public InstructorProfileFragment() {
        // Required empty public constructor
    }


    public static InstructorProfileFragment newInstance(String param1, String param2) {
        InstructorProfileFragment fragment = new InstructorProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructor_profile, container, false);

        CoursesLayout = (LinearLayout) v.findViewById(R.id.CoursesLayout);

        emailText = (EditText) v.findViewById(R.id.emailText);
        firstNameText = (EditText) v.findViewById(R.id.firstNameText);
        lastNameText = (EditText) v.findViewById(R.id.lastNameText);
        passwordText = (EditText) v.findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) v.findViewById(R.id.confirmPasswordText);
        mobileNumberText = (EditText) v.findViewById(R.id.mobileNumberText);
        addressText = (EditText) v.findViewById(R.id.addressText);
        specializationText = (EditText) v.findViewById(R.id.specializationText);
        degreeRadioGroup = (RadioGroup) v.findViewById(R.id.degreeRadioGroup);

        imageViewPhoto = (ImageView) v.findViewById(R.id.imageViewPhoto);

        databasehelper = new DataBaseHelper(requireContext(), "train", null, 1);

        Bundle args = getArguments();
        String email = args.getString("email");
        Instructor user = databasehelper.getInstructorObjectByEmail(email);
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPersonal_Photo(), 0, user.getPersonal_Photo().length);

        emailText.setText(user.getEmail_Address());
        firstNameText.setText(user.getFirst_Name());
        lastNameText.setText(user.getLast_Name());
        passwordText.setText(user.getPassword());
        confirmPasswordText.setText(user.getPassword());
        imageViewPhoto.setImageBitmap(bitmap);
        mobileNumberText.setText(user.getMobile_Number());
        addressText.setText(user.getAddress());
        specializationText.setText(user.getSpecialization());

        String degree = user.getDegree();
        int radioButtonId = -1;

        switch (degree) {
            case "BSc":
                radioButtonId = R.id.degreeBSc;
                break;
            case "MSc":
                radioButtonId = R.id.degreeMSc;
                break;
            case "PhD":
                radioButtonId = R.id.degreePhD;
                break;
        }

        if (radioButtonId != -1) {
            RadioButton radioButton = degreeRadioGroup.findViewById(radioButtonId);
            radioButton.setChecked(true);
        }

        Cursor Courses_Data = databasehelper.getCourses();
        ArrayList<String> Courses = new ArrayList<String>();
        while (Courses_Data.moveToNext()){
            Courses.add(Courses_Data.getString(0));
        }
        CoursesLayout.removeAllViews();

        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        for (int i = 0; i<Courses.size(); i+=2){
            LinearLayout twoCourses = new LinearLayout(requireContext());
            twoCourses.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            twoCourses.setLayoutParams(params);
            twoCourses.setVisibility(View.VISIBLE);
            if (Courses.size() > 1  && !Courses.get(i).isEmpty() && !Courses.get(i+1).isEmpty()){
                CheckBox Course1 = new CheckBox(requireContext());
                Course1.setText(Courses.get(i));
                Course1.setChecked(false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1.0f;
                Course1.setLayoutParams(layoutParams);
                CheckBox Course2 = new CheckBox(requireContext());
                Course2.setText(Courses.get(i+1));
                Course2.setChecked(false);
                Course2.setLayoutParams(layoutParams);
                twoCourses.addView(Course1,layoutParams);
                twoCourses.addView(Course2,layoutParams);
                checkBoxes.add(Course1);
                checkBoxes.add(Course2);
            } else {
                CheckBox Course1 = new CheckBox(requireContext());
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

        personalPhoto = v.findViewById(R.id.personalPhoto);
        personalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        SaveModificationsButton = v.findViewById(R.id.SaveModificationsButton);
        SaveModificationsButton.setOnClickListener(new View.OnClickListener() {
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

                byte[] imageData = new byte[0];
                if(imageUri == null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPersonal_Photo(), 0, user.getPersonal_Photo().length);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    imageData = outputStream.toByteArray();
                }else{
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
                        imageData = getBytesFromInputStream(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                databasehelper = new DataBaseHelper(requireContext(), "train", null, 1);
                Cursor Instructor_Data = databasehelper.getInstructorByEmail(email);
                if (Instructor_Data.moveToNext()) {
                    if (password.equals(confirmPassword)) {
                        if (validatePassword(password) && validateName(firstName) && validateName(lastName) && validateEmail(email) && validatePhoto(imageData)) {

                            int selectedDegree = degreeRadioGroup.getCheckedRadioButtonId();
                            if(selectedDegree != -1){
                                RadioButton radioButton = (RadioButton) v.findViewById(selectedDegree);
                                String degree = radioButton.getText().toString();
                                ArrayList<String> Courses = getInstCources(checkBoxes);
                                Instructor instructor = new Instructor(email,firstName,lastName,password,imageData,mobileNumber,address,specialization,degree, Courses);
                                databasehelper.updateInstructor(instructor);
                                for (int j=0; j<Courses.size(); j++){
                                    databasehelper.newInstCourses(Courses.get(j),email);
                                }
                                Intent intent = new Intent(requireContext(),InstructorMainActivity.class);
                                intent.putExtra("email", instructor.getEmail_Address());
                                intent.putExtra("firstName", instructor.getFirst_Name());
                                intent.putExtra("lastName", instructor.getLast_Name());
                                intent.putExtra("password", instructor.getPassword());
                                intent.putExtra("mobileNumber", instructor.getMobile_Number());
                                intent.putExtra("address", instructor.getAddress());
                                intent.putExtra("specialization", instructor.getSpecialization());
                                intent.putExtra("degree", instructor.getDegree());
                                requireContext().startActivity(intent);
                                requireActivity().finish();
                            } else {
                                Toast toast =Toast.makeText(requireContext(),
                                        "Please select your degree",Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    } else {
                        Toast toast = Toast.makeText(requireContext(), "Confirm password is incorrect", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(requireContext(), "Instructor data not found", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return v;
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageUri = data.getData();
        imageViewPhoto.setImageURI(imageUri);
    }

    public boolean validatePassword(String password) {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasNumber = false;

        int passSize = password.length();
        if(passSize < 8){
            Toast toast =Toast.makeText(requireContext(),
                    "password must be at least 8 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (passSize > 15) {
            Toast toast =Toast.makeText(requireContext(),
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
                    Toast toast =Toast.makeText(requireContext(),
                            "password must contain at least one number",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }else {
                Toast toast =Toast.makeText(requireContext(),
                        "password must contain at least one uppercase letter",Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }

        }else {
            Toast toast =Toast.makeText(requireContext(),
                    "password must contain at least one lowercase letter",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

    }

    public boolean validateName(String name) {

        int nameSize = name.length();
        if(nameSize < 3){
            Toast toast =Toast.makeText(requireContext(),
                    "first/last name must be at least 3 character",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else if (nameSize > 20) {
            Toast toast =Toast.makeText(requireContext(),
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
            Toast toast =Toast.makeText(requireContext(),
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
    public boolean validatePhoto(byte [] p) {
        if(p == null || p.length == 0 ){
            Toast toast =Toast.makeText(requireContext(),
                    "photo is empty",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        } else{
            return true;
        }
    }

}