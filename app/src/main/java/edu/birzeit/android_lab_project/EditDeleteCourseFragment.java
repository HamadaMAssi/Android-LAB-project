package edu.birzeit.android_lab_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditDeleteCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDeleteCourseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView myRecyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditDeleteCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditDeleteCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditDeleteCourseFragment newInstance(String param1, String param2) {
        EditDeleteCourseFragment fragment = new EditDeleteCourseFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_delete_course, container, false);
        DataBaseHelper databaseHelper = new DataBaseHelper(requireActivity(), "train", null, 1);
        myRecyclerView = view.findViewById(R.id.myRecyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Course> itemList = databaseHelper.getAllCourses();
        if (itemList != null) {
            Toast.makeText(requireActivity(), "Get Data successfully", Toast.LENGTH_SHORT).show();
            CaptionedImagesAdapter_course adapter = new CaptionedImagesAdapter_course(requireActivity(), itemList);
            myRecyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(requireActivity(), "No Course found", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}