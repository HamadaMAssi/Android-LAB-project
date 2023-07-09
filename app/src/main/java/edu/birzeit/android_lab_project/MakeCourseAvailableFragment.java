package edu.birzeit.android_lab_project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeCourseAvailableFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeCourseAvailableFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner coursesSpinner;
    private Spinner instructorsSpinner;
    private Spinner daySpinner;
    private Spinner monthSpinner;
    private Spinner yearSpinner;
    private Spinner startDaySpinner;
    private Spinner startMonthSpinner;
    private Spinner startYearSpinner;
    private ArrayList<CheckBox> scheduleCheckBox;
    private Spinner hourSpinner;
    private Spinner minSpinner;
    private EditText venueText;
    private Button addRegistrationButton;
    private Button cancelButton;

    public MakeCourseAvailableFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeCourseAvailableFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeCourseAvailableFragment newInstance(String param1, String param2) {
        MakeCourseAvailableFragment fragment = new MakeCourseAvailableFragment();
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
        return inflater.inflate(R.layout.fragment_make_course_available, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coursesSpinner = (Spinner) getActivity().findViewById(R.id.coursesSpinner);
        instructorsSpinner = (Spinner) getActivity().findViewById(R.id.instructorsSpinner);
        daySpinner = (Spinner) getActivity().findViewById(R.id.daySpinner);
        monthSpinner = (Spinner) getActivity().findViewById(R.id.monthSpinner);
        yearSpinner = (Spinner) getActivity().findViewById(R.id.yearSpinner);
        startDaySpinner = (Spinner) getActivity().findViewById(R.id.startDaySpinner);
        startMonthSpinner = (Spinner) getActivity().findViewById(R.id.startMonthSpinner);
        startYearSpinner = (Spinner) getActivity().findViewById(R.id.startYearSpinner);
        hourSpinner = (Spinner) getActivity().findViewById(R.id.hourSpinner);
        minSpinner = (Spinner) getActivity().findViewById(R.id.minSpinner);
        scheduleCheckBox = new ArrayList<CheckBox>();
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.saCheckBox));
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.suCheckBox));
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.moCheckBox));
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.tuCheckBox));
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.weCheckBox));
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.thCheckBox));
        scheduleCheckBox.add((CheckBox) getActivity().findViewById(R.id.frCheckBox));

        addRegistrationButton = (Button) getActivity().findViewById(R.id.addRegistrationButton);
        cancelButton = (Button) getActivity().findViewById(R.id.cancelButton);
        venueText = (EditText) getActivity().findViewById(R.id.venueText);


        DataBaseHelper databasehelper = new DataBaseHelper(requireActivity(), "train", null, 1);
        Cursor Courses_Data = databasehelper.getCourses();
        ArrayList<String> courses = new ArrayList<String>();
        while (Courses_Data.moveToNext()){
            courses.add(Courses_Data.getString(0));
        }
        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, courses);
        coursesSpinner.setAdapter(coursesAdapter);
        coursesSpinner.setSelection(0);

        Cursor Instructors_Data = databasehelper.getInstructors();
        ArrayList<String> instructorsEmail = new ArrayList<String>();
        ArrayList<String> instructorsName = new ArrayList<String>();
        int count = 1;
        while (Instructors_Data.moveToNext()){
            instructorsEmail.add(Instructors_Data.getString(0));
            String Name = count+ " " + Instructors_Data.getString(1) +" "+ Instructors_Data.getString(2);
            instructorsName.add(Name);
            count++;
        }
        ArrayAdapter<String> instructorsAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, instructorsName);
        instructorsSpinner.setAdapter(instructorsAdapter);
        instructorsSpinner.setSelection(0);

        String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, days);
        daySpinner.setAdapter(daysAdapter);
        startDaySpinner.setAdapter(daysAdapter);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        daySpinner.setSelection(day);
        day += 7;
        if (day > 30){
            day -= 31;
        }
        startDaySpinner.setSelection(day);

        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        ArrayAdapter<String> monthsAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, months);
        monthSpinner.setAdapter(monthsAdapter);
        startMonthSpinner.setAdapter(monthsAdapter);
        int month = calendar.get(Calendar.MONTH);
        monthSpinner.setSelection(month);
        startMonthSpinner.setSelection(month);

        String[] years = {"2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032"};
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, years);
        yearSpinner.setAdapter(yearsAdapter);
        startYearSpinner.setAdapter(yearsAdapter);
        int year = calendar.get(Calendar.YEAR) - 2022;
        yearSpinner.setSelection(year);
        startYearSpinner.setSelection(year);

        String[] hours = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        ArrayAdapter<String> hoursAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, hours);
        hourSpinner.setAdapter(hoursAdapter);
        hourSpinner.setSelection(9);

        String[] min = new String[60];
        for (int i=0; i<60; i++){
            if (i<10){
                min[i] = "0"+i;
            }else{
                min[i] = Integer.toString(i);
            }
        }
        ArrayAdapter<String> minAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_dropdown_item, min);
        minSpinner.setAdapter(minAdapter);
        minSpinner.setSelection(0);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
            }
        });


        addRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseTitle = coursesSpinner.getSelectedItem().toString();
                String instructorName = instructorsSpinner.getSelectedItem().toString();
                int instructorNum = Integer.parseInt(instructorName.replaceAll("[^0-9]", ""));
                String instructorEmail = instructorsEmail.get(instructorNum-1);
                String Deadline = daySpinner.getSelectedItem().toString() + "/" + monthSpinner.getSelectedItem().toString() + "/" + yearSpinner.getSelectedItem().toString();
                String StartDate = startDaySpinner.getSelectedItem().toString() + "/" + startMonthSpinner.getSelectedItem().toString() + "/" + startYearSpinner.getSelectedItem().toString();
                String schedule = "";
                for (int i=0; i<scheduleCheckBox.size(); i++){
                    if (scheduleCheckBox.get(i).isChecked()){
                        schedule += scheduleCheckBox.get(i).getText().toString()+", ";
                    }
                }
                if (schedule.equals("")){
                    Toast toast =Toast.makeText(requireActivity(),
                            "please select at least 1 day for schedule!!",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    schedule += "(" + hourSpinner.getSelectedItem().toString() + ":" + minSpinner.getSelectedItem().toString() + ")";
                    String venue = venueText.getText().toString();
                    if(venue.isEmpty()){
                        Toast toast =Toast.makeText(requireActivity(),
                                "please fill venue field!!",Toast.LENGTH_SHORT);
                        toast.show();
                    }else{
                        Registration registration = new Registration(0,courseTitle,instructorEmail,Deadline,StartDate,schedule,venue);
                        databasehelper.newRegistration(registration);
                        Toast toast =Toast.makeText(requireActivity(),
                                "Registration Added successfully",Toast.LENGTH_SHORT);
                        toast.show();
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
                    }
                }
            }
        });
    }
}