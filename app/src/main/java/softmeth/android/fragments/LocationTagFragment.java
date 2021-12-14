package softmeth.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import softmeth.android.R;
import softmeth.android.models.Loader;

public class LocationTagFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_tag, container, false);

        // Get arguments
        Bundle bundle = getArguments();
        int albumIndex = bundle.getInt("albumIndex");
        int photoIndex = bundle.getInt("photoIndex");

        // Set initial location value
        TextView locationTextView = (TextView) view.findViewById(R.id.location_text_view);
        String locationValue = Loader.getLocationValue(albumIndex, photoIndex);
        if (locationValue != null)
            locationTextView.setText(Loader.getLocationValue(albumIndex, photoIndex));

        // Add button listener
        Button addButton = (Button) view.findViewById(R.id.add_location_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptLocationValue(albumIndex, photoIndex, locationTextView);
            }
        });

        // Clear button listener
        Button clearButton = (Button) view.findViewById(R.id.clear_tag_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Loader.deleteTag(albumIndex, photoIndex, "LOCATION", Loader.getLocationValue(albumIndex, photoIndex)))
                        locationTextView.setText(R.string.location_tag_default_text);
                else
                    Toast.makeText(getContext(), "Could not clear this photo's LOCATION value", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Prompts user for the new location tag value
    private void promptLocationValue(int albumIndex, int photoIndex, TextView locationTextView)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.single_entry_dialog, null);

        // Set title
        builder.setTitle("Set Location")
                .setMessage("Enter a value for the LOCATION tag");

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

                        boolean success = Loader.addTagToPhoto(albumIndex, photoIndex, "LOCATION", newValue.toLowerCase());
                        if (success)
                            locationTextView.setText(Loader.getLocationValue(albumIndex, photoIndex));
                        else
                            Toast.makeText(getContext(), "Could not set LOCATION tag. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                }).show();
    }
}