package softmeth.android.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.adapters.AlbumItemAdapter;
import softmeth.android.models.Album;
import softmeth.android.models.Photo;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

/*        // TODO: delete this, it's just for testing
        Photo testPhoto = new Photo(Uri.parse("content://com.android.providers.media.documents/document/image%3A33"));

        ArrayList<Photo> testList = new ArrayList<Photo>();
        testList.add(testPhoto);

        Album testAlbum = new Album("test", testList);

        ArrayList<Album> testAlbumList = new ArrayList<Album>();
        testAlbumList.add(testAlbum);*/

        // Set up recycler view
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.album_recyclerview);
        AlbumItemAdapter albumItemAdapter = new AlbumItemAdapter(null);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(albumItemAdapter);

        // Create onClickListener for opening the selected album
        Button openButton = (Button) view.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_albumFragment);
            }
        });

        return view;
    }
}