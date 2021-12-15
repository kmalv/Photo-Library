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
import android.widget.ToggleButton;

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
    private enum Filter { LOCATION, PERSON };
    private ArrayList<String> autoCompleteList1;
    private ArrayList<String> autoCompleteList2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        autoCompleteList1 = createFormattedTagList(Filter.LOCATION, Loader.getAllTags());
        autoCompleteList2 = createFormattedTagList(Filter.LOCATION, Loader.getAllTags());

        // Radio groups
        RadioButton orrdb = (RadioButton) view.findViewById(R.id.OR_radio);
        RadioButton andrdb = (RadioButton) view.findViewById(R.id.AND_radio);
        RadioButton clearrdb = (RadioButton) view.findViewById(R.id.clear_radio_button);

        // Toggles
        ToggleButton firstToggle = (ToggleButton) view.findViewById(R.id.textfield1_toggle);
        ToggleButton secondToggle = (ToggleButton) view.findViewById(R.id.textfield2_toggle);

        // Text fields and their adapters
        AutoCompleteTextView editText1 = view.findViewById(R.id.textfield1);
        AutoCompleteTextView editText2 = view.findViewById(R.id.textfield2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, autoCompleteList1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, autoCompleteList2);

        editText1.setAdapter(adapter1);
        editText2.setAdapter(adapter2);

        // On click listeners to reset filters
        firstToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstToggle.isChecked())
                {
                    autoCompleteList1 = createFormattedTagList(Filter.PERSON, Loader.getAllTags());
                    adapter1.clear();
                    adapter1.addAll(autoCompleteList1);
                }
                else
                {
                    autoCompleteList1 = createFormattedTagList(Filter.LOCATION, Loader.getAllTags());
                    adapter1.clear();
                    adapter1.addAll(autoCompleteList1);
                }
            }
        });

        secondToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secondToggle.isChecked()) {
                    autoCompleteList2 = createFormattedTagList(Filter.PERSON, Loader.getAllTags());
                    adapter2.clear();
                    adapter2.addAll(autoCompleteList2);
                }
                else
                {
                    autoCompleteList2 = createFormattedTagList(Filter.LOCATION, Loader.getAllTags());
                    adapter2.clear();
                    adapter2.addAll(autoCompleteList2);
                }
            }
        });

        // Ok button listener
        Button okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query1 = editText1.getText().toString();
                String query2 = editText2.getText().toString();

                // INVALID QUERY HANDLING/SINGLE SEARCH
                // Null queries
                if (query1.isEmpty() && query2.isEmpty()) {
                    Toast.makeText(getContext(), "Invalid search. Please enter at least one query.", Toast.LENGTH_LONG).show();
                    return;
                }
                // Single tag search
                else if (query1.isEmpty() && !query2.isEmpty()) {
                    // Make sure clear is selected
                    if (clearrdb.isChecked()) {

                        query2 = query2.toLowerCase();

                        String key2 = (secondToggle.isChecked()) ? "PERSON" : "LOCATION";

                        Loader.singleTagSearch(key2, query2);
                        if (Loader.getSearchResults().isEmpty()) {
                            Toast.makeText(getContext(), "No results for these queries. Please try again.", Toast.LENGTH_LONG).show();
                            return;
                        } else
                            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultsFragment);
                    } else {
                        Toast.makeText(getContext(), "Invalid search criteria. Choose CLEAR for a single tag search.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // Single tag search
                else if (!query1.isEmpty() && query2.isEmpty()) {
                    // Make sure clear is selected
                    if (clearrdb.isChecked()) {
                        query1 = query1.toLowerCase();

                        String key1 = (firstToggle.isChecked()) ? "PERSON" : "LOCATION";

                        Loader.singleTagSearch(key1, query1);
                        if (Loader.getSearchResults().isEmpty()) {
                            Toast.makeText(getContext(), "No results for these queries. Please try again.", Toast.LENGTH_LONG).show();
                            return;
                        } else
                            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultsFragment);
                    } else {
                        Toast.makeText(getContext(), "Invalid search criteria. Choose CLEAR for a single tag search.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // Double tag search
                else if (!(query1.isEmpty() && query2.isEmpty())) {
                    // Make sure clear is NOT checked
                    if (clearrdb.isChecked()) {
                        Toast.makeText(getContext(), "Invalid search criteria. Choose AND or OR for a double tag search.", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (andrdb.isChecked()) {
                            query1 = query1.toLowerCase();
                            query2 = query2.toLowerCase();

                            String key1 = (firstToggle.isChecked()) ? "PERSON" : "LOCATION";
                            String key2 = (secondToggle.isChecked()) ? "PERSON" : "LOCATION";

                            Loader.doubleTagSearch(Loader.Search.AND, key1, query1, key2, query2);
                            if (Loader.getSearchResults().isEmpty()) {
                                Toast.makeText(getContext(), "No results for these queries. Please try again.", Toast.LENGTH_LONG).show();
                                return;
                            } else
                                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultsFragment);
                        } else if (orrdb.isChecked()) {

                            query1 = query1.toLowerCase();
                            query2 = query2.toLowerCase();

                            String key1 = (firstToggle.isChecked()) ? "PERSON" : "LOCATION";
                            String key2 = (secondToggle.isChecked()) ? "PERSON" : "LOCATION";

                            Loader.doubleTagSearch(Loader.Search.OR, key1, query1, key2, query2);
                            if (Loader.getSearchResults().isEmpty()) {
                                Toast.makeText(getContext(), "No results for these queries. Please try again.", Toast.LENGTH_LONG).show();
                                return;
                            } else
                                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_searchResultsFragment);
                        } else {
                            Toast.makeText(getContext(), "Unexpected error in search button listener. Please try again.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
        });

        // Cancel button listener
        Button cancelButton = (Button) view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_homeFragment);
            }
        });

        return view;
    }

    private ArrayList<String> createFormattedTagList(Filter filter, ArrayList<Tag> tags)
    {
        ArrayList<String> formattedTags = new ArrayList<String>();
        String filterKey = (filter == Filter.LOCATION) ? "LOCATION" : "PERSON";

        for (Tag t : tags)
        {
            if (t.getKey().equals(filterKey) && !formattedTags.contains(t.getValue()))
            {
                formattedTags.add(t.getValue());
            }
        }
        System.out.println(formattedTags);
        return formattedTags;
    }
}