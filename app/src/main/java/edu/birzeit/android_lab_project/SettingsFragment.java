package edu.birzeit.android_lab_project;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }


    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        editor = prefs.edit();

        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Spinner spinnerLanguage = view.findViewById(R.id.spinnerLanguage);
        Spinner spinnerNotifications = view.findViewById(R.id.spinnerNotifications);
        Spinner spinnerAppearance = view.findViewById(R.id.spinnerAppearance);
        TextView PrivacyPolicy = view.findViewById(R.id.PrivacyPolicy);
        TextView TermsandConditions = view.findViewById(R.id.TermsandConditions);
        Button apply = view.findViewById(R.id.apply);
        Button cancel = view.findViewById(R.id.cancel);

        String [] datalist1={"English","العربية"};
        String [] datalist2={"Enable","Disable"};
        String [] datalist3={"Light theme","Dark theme"};

        ArrayAdapter<String> ad = new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,datalist1);
        spinnerLanguage.setAdapter(ad);
        ArrayAdapter<String> ad2 = new ArrayAdapter<>(view.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,datalist2);
        spinnerNotifications.setAdapter(ad2);
        ArrayAdapter<String> ad3 = new ArrayAdapter<>(view.getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,datalist3);
        spinnerAppearance.setAdapter(ad3);

        spinnerLanguage.setSelection(ad.getPosition(prefs.getString("Language", "English")));
        spinnerNotifications.setSelection(ad.getPosition(prefs.getString("Notifications", "Enable")));
        spinnerAppearance.setSelection(ad.getPosition(prefs.getString("Appearance", "Light theme")));

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
//                editor = prefs.edit();

                String selectedLanguage = spinnerLanguage.getSelectedItem().toString();
                String selectedNotifications = spinnerNotifications.getSelectedItem().toString();
                String selectedAppearance = spinnerAppearance.getSelectedItem().toString();

                editor.putString("Language", selectedLanguage);
                editor.putString("Notifications", selectedNotifications);
                editor.putString("Appearance", selectedAppearance);
                editor.commit();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
                requireActivity().recreate();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new AdminHomeFragment()).commit();
            }
        });

        return view;
    }
}