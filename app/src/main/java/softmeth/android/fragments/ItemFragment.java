package softmeth.android.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.adapters.AlbumItemAdapter;
import softmeth.android.fragments.placeholder.PlaceholderContent;
import softmeth.android.models.Album;
import softmeth.android.models.Loader;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_popup_window, container, false);

        // Get the bundle with the album to open
        Bundle bundle = getArguments();
        int index = bundle.getInt("albumIndex");
        int photoIndex = bundle.getInt("photoIndex");

        // Set up recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_of_available_albums);
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(getContext(), Loader.getAlbums());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(albumItemAdapter);

        Button move_to_button = (Button) view.findViewById(R.id.move_to);
        move_to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = albumItemAdapter.getSelectedIndex();
                if (selected == index)
                {
                    Toast.makeText(getContext(), "You cannot move a photo to the album it's in already.", Toast.LENGTH_SHORT).show();
                }
                else if (selected == RecyclerView.NO_POSITION)
                {
                    Toast.makeText(getContext(), "No destination album was selected, please try again.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (Loader.movePhoto(index, photoIndex, selected))
                    {
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("index", index);

                        Toast.makeText(getContext(), "Move Successful.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_homeFragment, bundle2);
                    }
                }

            }
        });

        return view;
    }
}