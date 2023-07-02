package edu.birzeit.android_lab_project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateNewCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateNewCourseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView back;
    private Button addCourseButton, cancelButton;
    private EditText courseTitleText;
    private EditText courseMainTopicText;
    private EditText prerequisitesText;


    public CreateNewCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateNewCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateNewCourseFragment newInstance(String param1, String param2) {
        CreateNewCourseFragment fragment = new CreateNewCourseFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_course, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addCourseButton = (Button) getActivity().findViewById(R.id.addCourseButton);
        cancelButton = (Button) getActivity().findViewById(R.id.cancelButton);
//        back = getActivity().findViewById(R.id.back);

        courseTitleText = (EditText) getActivity().findViewById(R.id.courseTitleText);
        courseMainTopicText = (EditText) getActivity().findViewById(R.id.courseMainTopicText);
        prerequisitesText = (EditText) getActivity().findViewById(R.id.prerequisitesText);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
            }
        });

//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
//            }
//        });


        final int[] i = {0};
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = courseTitleText.getText().toString();
                String courseMainTopic = courseMainTopicText.getText().toString();
                String prerequisites = prerequisitesText.getText().toString();
                DataBaseHelper databasehelper = new DataBaseHelper(requireActivity(), "train", null, 1);
                Cursor Course_Data = databasehelper.getCourseInfo(courseTitle);
                if(!Course_Data.moveToNext()){
                    Course course = new Course(courseTitle,courseMainTopic,prerequisites,"no photo");
                    databasehelper.newCourse(course);
                    Toast toast =Toast.makeText(requireActivity(),
                            "Course Added successfully",Toast.LENGTH_SHORT);
                    toast.show();
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
                }
            }
        });
    }
}