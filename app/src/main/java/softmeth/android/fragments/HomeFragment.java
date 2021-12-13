package softmeth.android.fragments;

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

        // TODO: Delete this whenever we are done implementing serialization logic
        // Get the user's albums
        Loader.getUser().addAlbum(new Album("test"));

        // Set up recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_recyclerview);
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(getContext(), Loader.getUser().getAlbums());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(albumItemAdapter);

        // Open button listener
        Button openButton = (Button) view.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int index = albumItemAdapter.getSelectedIndex();
                if (albumItemAdapter.getItemCount() != 0 && index != -1)
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
            public void onClick(View view) {

                if (albumItemAdapter.getItemCount() != 0 && albumItemAdapter.getSelectedIndex() != -1)
                {
                    if (Loader.deleteAlbum(albumItemAdapter.getSelectedIndex()))
                        albumItemAdapter.notifyDataSetChanged();
                    else
                        Toast.makeText(getContext(), "Could not delete album. Please try again.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "No album was selected. Please select an album to open.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}