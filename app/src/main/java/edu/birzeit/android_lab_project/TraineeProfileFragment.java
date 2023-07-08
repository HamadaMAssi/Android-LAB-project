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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TraineeProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TraineeProfileFragment extends Fragment {

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TraineeProfileFragment() {
        // Required empty public constructor
    }

    public static TraineeProfileFragment newInstance(String param1, String param2) {
        TraineeProfileFragment fragment = new TraineeProfileFragment();
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
        View v = inflater.inflate(R.layout.fragment_trainee_profile, container, false);
        emailText = (EditText) v.findViewById(R.id.emailText);
        firstNameText = (EditText) v.findViewById(R.id.firstNameText);
        lastNameText = (EditText) v.findViewById(R.id.lastNameText);
        passwordText = (EditText) v.findViewById(R.id.passwordText);
        confirmPasswordText = (EditText) v.findViewById(R.id.confirmPasswordText);
        mobileNumberText = (EditText) v.findViewById(R.id.mobileNumberText);
        addressText = (EditText) v.findViewById(R.id.addressText);


        imageViewPhoto = (ImageView) v.findViewById(R.id.imageViewPhoto);
        databasehelper = new DataBaseHelper(requireContext(), "train", null, 1);

        Bundle args = getArguments();
        String email = args.getString("email");
        Trainee user = databasehelper.getTraineeObjectByEmail(email);
        Bitmap bitmap = BitmapFactory.decodeByteArray(user.getPersonal_Photo(), 0, user.getPersonal_Photo().length);

        emailText.setText(user.getEmail_Address());
        firstNameText.setText(user.getFirst_Name());
        lastNameText.setText(user.getLast_Name());
        passwordText.setText(user.getPassword());
        confirmPasswordText.setText(user.getPassword());
        imageViewPhoto.setImageBitmap(bitmap);
        mobileNumberText.setText(user.getMobile_Number());
        addressText.setText(user.getAddress());

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
                Cursor Trainee_Data = databasehelper.getTraineeByEmail(email);
                if (Trainee_Data.moveToNext()) {
                    if (password.equals(confirmPassword)) {
                        if (validatePassword(password) && validateName(firstName) && validateName(lastName) && validateEmail(email) && validatePhoto(imageData)) {

                                Trainee trainee = new Trainee(email,firstName,lastName,password,imageData,mobileNumber,address);
                                databasehelper.updateTrainee(trainee);
                                Intent intent = new Intent(requireContext(),TraineeMainActivity.class);
                                intent.putExtra("email", trainee.getEmail_Address());
                                intent.putExtra("firstName", trainee.getFirst_Name());
                                intent.putExtra("lastName", trainee.getLast_Name());
                                intent.putExtra("password", trainee.getPassword());
                                intent.putExtra("mobileNumber", trainee.getMobile_Number());
                                intent.putExtra("address", trainee.getAddress());
                                requireContext().startActivity(intent);
                                requireActivity().finish();

                        }
                    } else {
                        Toast toast = Toast.makeText(requireContext(), "Confirm password is incorrect", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(requireContext(), "Trainee data not found", Toast.LENGTH_SHORT);
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