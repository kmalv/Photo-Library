package softmeth.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.models.Loader;

public class PersonTagFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_tag, container, false);

        // Get arguments
        Bundle bundle = getArguments();
        int albumIndex = bundle.getInt("albumIndex");
        int photoIndex = bundle.getInt("photoIndex");

        TextView peopleTextView = (AutoCompleteTextView) view.findViewById(R.id.people_text_field);
        ArrayList<String> peopleValue = Loader.getPeopleTagValues(albumIndex, photoIndex);
        if (peopleValue != null)
            peopleTextView.setText((CharSequence) Loader.getPeopleTagValues(albumIndex, photoIndex));

        // Add button listener
        Button addButton = (Button) view.findViewById(R.id.add_location_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptPersonValue(albumIndex, photoIndex, peopleTextView);
            }
        });


        return view;
    }

    // Prompts user for the new location tag value
    private void promptPersonValue(int albumIndex, int photoIndex, TextView peopleTextView)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.single_entry_dialog, null);

        // Set title
        builder.setTitle("Add a Person")
                .setMessage("Enter a value for the PEOPLE tag");

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        EditText editText = (EditText) d.findViewById(R.id.single_entry_edittext);
                        String newValue = editText.getText().toString();
                        if(false){}

                        else{
                            boolean success = Loader.addTagToPhoto(albumIndex, photoIndex, "PERSON", newValue);
                            if (success)
                                peopleTextView.setText(newValue);
                                // peopleTextView.setText(Loader.getPeopleTagValues(albumIndex, photoIndex));
                            else
                                Toast.makeText(getContext(), "Could not set PEOPLE tag. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                }).show();
    }
}