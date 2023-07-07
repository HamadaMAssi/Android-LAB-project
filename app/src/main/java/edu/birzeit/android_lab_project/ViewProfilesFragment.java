package edu.birzeit.android_lab_project;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewProfilesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewProfilesFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private Spinner categorySpinner;
    private RecyclerView myRecyclerView;
    public ViewProfilesFragment() {
        // Required empty public constructor
    }

    public static ViewProfilesFragment newInstance(String param1, String param2) {
        ViewProfilesFragment fragment = new ViewProfilesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_profiles, container, false);

        // Categories
        String[] options = {"Trainees", "Instructors", "All"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_dropdown_item, options);

        categorySpinner = view.findViewById(R.id.spinner);
        categorySpinner.setAdapter(categoryAdapter);

        myRecyclerView = view.findViewById(R.id.myRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCategory = options[position];
                DataBaseHelper databaseHelper = new DataBaseHelper(requireActivity(), "train", null, 1);

                if (selectedCategory.equals("Trainees")) {
                    List<Trainee> itemList = databaseHelper.getAllTrainees();
                    if (itemList != null) {
                        Toast.makeText(requireActivity(), "Get Data successfully", Toast.LENGTH_SHORT).show();
                        CaptionedImagesAdapter_trainee adapter = new CaptionedImagesAdapter_trainee(requireActivity(), itemList);
                        myRecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireActivity(), "No Trainees found", Toast.LENGTH_SHORT).show();
                    }
                } else if (selectedCategory.equals("Instructors")) {
                    List<Instructor> itemList = databaseHelper.getAllInstructors();
                    if (itemList != null) {
                        Toast.makeText(requireActivity(), "Get Data successfully", Toast.LENGTH_SHORT).show();
                        CaptionedImagesAdapter_instructor adapter = new CaptionedImagesAdapter_instructor(requireActivity(), itemList);
                        myRecyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(requireActivity(), "No Trainees found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case when nothing is selected
            }
        });

        return view;
    }


}