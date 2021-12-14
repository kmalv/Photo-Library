package softmeth.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.models.Album;
import softmeth.android.models.Loader;
import softmeth.android.models.Photo;
import softmeth.android.models.Tag;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<Album> albums = Loader.getAlbums();
        ArrayList<Photo> photos;
        ArrayList<String> tags = new ArrayList<>();
        // ArrayList<String> people_tags;
        int num_albums = albums.size();
        int num_photos;

        for (int i = 0; i <= num_albums; ++i){
            photos = Loader.getPhotosFromAlbum(i);
            num_photos = photos.size();
            for(int j = 0; j <= num_photos; ++j){
                tags.add(Loader.getLocationValue(i,j));
            }

        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_for_search, container, false);

        RadioGroup rdgrp = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton orrdb = (RadioButton) view.findViewById(R.id.OR_radio);
        RadioButton andrdb = (RadioButton) view.findViewById(R.id.AND_radio);
        RadioButton clearrdb = (RadioButton) view.findViewById(R.id.clear_radio_button);

        EditText location = view.findViewById(R.id.location_text_field);
        EditText people = view.findViewById(R.id.people_text_field);

        AutoCompleteTextView editText = view.findViewById(R.id.location_text_field);
        AutoCompleteTextView editText2 = view.findViewById(R.id.people_text_field);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, tags);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

        editText.setAdapter(adapter1);

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