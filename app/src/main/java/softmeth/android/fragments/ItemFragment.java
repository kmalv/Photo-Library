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

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_popup_window, container, false);

        // Get the bundle with the album to open
        Bundle bundle = getArguments();
        int index = bundle.getInt("albumIndex");
        int photoIndex = bundle.getInt("photoIndex");
        // Set the adapter

        ArrayList<Album> filter = Loader.getAlbums();
        filter.remove(index);

        // Set up recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_of_available_albums);
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(getContext(), filter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(albumItemAdapter);

        Button move_to_button = (Button) view.findViewById(R.id.move_to);
        move_to_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = albumItemAdapter.getSelectedIndex();
                if(selected >= index){
                    selected = selected+1;
                }

                // if(Loader.addPhotoToAlbum())
                if (Loader.movePhoto(index, photoIndex, selected)){
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("index", index);

                    Navigation.findNavController(view).navigate(R.id.action_itemFragment_to_albumFragment, bundle2);
                }

            }
        });

        return view;
    }
}