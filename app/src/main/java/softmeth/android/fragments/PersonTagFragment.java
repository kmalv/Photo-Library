package softmeth.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Person;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import softmeth.android.adapters.PersonTagItemAdapter;
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

        // Set up recyclerView stuff
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.tag_recycler_view);
        PersonTagItemAdapter personTagItemAdapter = new PersonTagItemAdapter(getContext(), Loader.getPeopleTagOnly(albumIndex, photoIndex));

        recyclerView.setAdapter(personTagItemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Add button listener
        Button addButton = (Button) view.findViewById(R.id.add_person_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptPersonValue(albumIndex, photoIndex, personTagItemAdapter);
            }
        });

        // Delete button listener
        Button deleteButton = (Button) view.findViewById(R.id.delete_tag_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = personTagItemAdapter.getSelectedIndex();
                if (personTagItemAdapter.getItemCount() != 0 && selected != -1)
                {
                    // sorry this is long AF
                    TextView textViewAtSel = (TextView) recyclerView.findViewHolderForAdapterPosition(selected).itemView.findViewById(R.id.person_tag_value_textview);
                    String valAtSel = textViewAtSel.getText().toString().toLowerCase();

                    if (Loader.deleteTag(albumIndex, photoIndex, "PERSON", valAtSel))
                        personTagItemAdapter.notifyDataSetChanged();
                    else
                        Toast.makeText(getContext(), "Could not delete tag. Please try again.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "No tag was selected. Please select a tag to delete.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    // Prompts user for the new location tag value
    private void promptPersonValue(int albumIndex, int photoIndex, PersonTagItemAdapter personTagItemAdapter)
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
                        String newValue = editText.getText().toString().toLowerCase();

                        boolean success = Loader.addTagToPhoto(albumIndex, photoIndex, "PERSON", newValue);
                        if (success)
                            personTagItemAdapter.notifyDataSetChanged();
                        else
                            Toast.makeText(getContext(), "Could not add PEOPLE tag. Please try a different value.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                }).show();
    }
}