package softmeth.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import softmeth.android.R;
import softmeth.android.adapters.ThumbnailItemAdapter;
import softmeth.android.models.Loader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.results_recycleview);
        ThumbnailItemAdapter thumbnailItemAdapter = new ThumbnailItemAdapter(getContext(), Loader.getSearchResults());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(thumbnailItemAdapter);

        // Exit button listener
        Button goBackButton = (Button) view.findViewById(R.id.back_to_album_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_searchResultsFragment_to_homeFragment);
            }
        });

        return view;
    }
}