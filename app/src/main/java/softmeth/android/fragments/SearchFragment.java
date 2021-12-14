package softmeth.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import softmeth.android.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RadioGroup radioGroup;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_login_for_search, container, false);
        RadioGroup rdgrp = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton orrdb = (RadioButton) view.findViewById(R.id.OR_radio);
        RadioButton andrdb = (RadioButton) view.findViewById(R.id.AND_radio);
        RadioButton clearrdb = (RadioButton) view.findViewById(R.id.clear_radio_button);
        EditText location = view.findViewById(R.id.location_text_field);
        EditText people = view.findViewById(R.id.people_text_field);



        // Ok button listener
        Button okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {   // Search only if conditions are met
                Boolean orSearch = false;
                Boolean andSearch = false;
                Boolean singleSearch = false;
                Boolean hasLocation = false;
                Boolean hasPeople = false;

                if(location.getText().toString().isEmpty() && (people.getText().toString().isEmpty())){
                    Toast.makeText(getContext(), "No Tags Entered", Toast.LENGTH_SHORT).show();
                }
                else if (!people.getText().toString().isEmpty()){
                    String ppl = people.getText().toString().toUpperCase();
                    hasPeople = true;
                }
                else if (!location.getText().toString().isEmpty()){
                    String loc = location.getText().toString().toUpperCase();
                    hasLocation = true;
                }

                if(hasLocation && hasPeople && orrdb.isChecked()){
                    orSearch = true;
                    // search loc || ppl;
                    Toast.makeText(getContext(), "OR SEARCH", Toast.LENGTH_SHORT).show();
                }
                else if(hasLocation && hasPeople && clearrdb.isChecked()){
                    Toast.makeText(getContext(), "SELECT A RADIO", Toast.LENGTH_SHORT).show();
                }
                else if(hasLocation && hasPeople && andrdb.isChecked()){
                    andSearch = true;
                    // search loc && ppl;
                    Toast.makeText(getContext(), "AND SEARCH", Toast.LENGTH_SHORT).show();
                }
                else if ((hasLocation && !hasPeople) && clearrdb.isChecked()){
                    singleSearch = true;
                    // search loc;
                    Toast.makeText(getContext(), "SINGLE SEARCH", Toast.LENGTH_SHORT).show();
                }
                else if ((!hasLocation && hasPeople) && clearrdb.isChecked()){
                    singleSearch = true;
                    // search ppl;
                }
                else
                    Toast.makeText(getContext(), "Invalid search criteria", Toast.LENGTH_SHORT).show();
                }
        });

        // Cancel button listener
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });


        rdgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check which radiobutton was pressed
            }
        });



        return view;
    }


}