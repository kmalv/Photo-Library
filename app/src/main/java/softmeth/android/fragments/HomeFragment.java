package softmeth.android.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.adapters.AlbumItemAdapter;
import softmeth.android.models.Album;
import softmeth.android.models.Loader;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Set up recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_recyclerview);
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(getContext(), Loader.getAlbums());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(albumItemAdapter);

        // Open button listener
        Button openButton = (Button) view.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int index = albumItemAdapter.getSelectedIndex();
                if (albumItemAdapter.getItemCount() != 0 && index != RecyclerView.NO_POSITION)
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", index);

                    Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_albumFragment, bundle);
                }
                else
                    Toast.makeText(getContext(), "No album was selected. Please select an album to open.", Toast.LENGTH_SHORT).show();
            }
        });

        // Rename button listener
        Button renameButton = (Button) view.findViewById(R.id.rename_button);
        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (albumItemAdapter.getItemCount() != 0 && albumItemAdapter.getSelectedIndex() != -1)
                {
                    if (Loader.renameAlbum(albumItemAdapter.getSelectedIndex(), "TEST_RENAME"))
                        albumItemAdapter.notifyDataSetChanged();
                    else
                        Toast.makeText(getContext(), "Could not rename album. Please try again.", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getContext(), "No album was selected. Please select an album to rename.", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete button listener
        Button deleteButton = (Button) view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (albumItemAdapter.getItemCount() != 0 && albumItemAdapter.getSelectedIndex() != -1)
                {
                    if (Loader.deleteAlbum(albumItemAdapter.getSelectedIndex()))
                        albumItemAdapter.notifyDataSetChanged();
                    else
                        Toast.makeText(getContext(), "Could not delete album. Please try again.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "No album was selected. Please select an album to delete.", Toast.LENGTH_LONG).show();
            }
        });

        // Create button listener
        Button createButton = (Button) view.findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptNewAlbum(albumItemAdapter);
            }
        });

        // Search button listener
        Button searchButton = (Button) view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {   // Bring up search page only if there is at least one tag attached to a photo
                if (true)
                    Navigation.findNavController(view).navigate(R.id.searchFragment);
                else
                    Toast.makeText(getContext(), "No tags available to search.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Creates the AlertDialog that asks the user for the new album name
    private void promptNewAlbum(AlbumItemAdapter albumItemAdapter)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.single_entry_dialog, null);

        builder.setTitle("New Album")
                .setMessage("Enter a name for the new album");

        // Inflate and set the layout for the dialog
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        EditText editText = (EditText) d.findViewById(R.id.single_entry_edittext);
                        String newValue = editText.getText().toString();

                        boolean success = Loader.addAlbum(newValue);
                        if (success)
                            albumItemAdapter.notifyDataSetChanged();
                        else
                            Toast.makeText(getContext(), "Could not add album. Please try a different name.", Toast.LENGTH_SHORT).show();
                       }
                    })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                }).show();
    }
}