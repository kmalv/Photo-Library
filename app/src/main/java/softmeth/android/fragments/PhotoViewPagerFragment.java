package softmeth.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import softmeth.android.R;
import softmeth.android.adapters.PhotoInfoAdapter;
import softmeth.android.models.Loader;

public class PhotoViewPagerFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_view_pager, container, false);

        Bundle bundle = getArguments();
        int albumIndex = bundle.getInt("albumIndex");
        int photoIndex = bundle.getInt("photoIndex");
        int numPhotos = Loader.getAlbum(albumIndex).getNumPhotos();

        // Set up viewPager2
        ViewPager2 viewPager = (ViewPager2) view.findViewById(R.id.photo_info_viewpager);
        PhotoInfoAdapter photoInfoAdapter = new PhotoInfoAdapter(getActivity(), bundle);

        viewPager.setAdapter(photoInfoAdapter);

        // Don't let users swipe betwen photos
        viewPager.setUserInputEnabled(false);

        // Disable buttons if there are no previous/next photos
        Button previousButton = (Button) view.findViewById(R.id.previous_photo_button);
        Button nextButton     = (Button) view.findViewById(R.id.next_photo_button);

        changeButtonEnabled(photoIndex, numPhotos, previousButton, nextButton);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                changeButtonEnabled(viewPager.getCurrentItem(), numPhotos, previousButton, nextButton);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                changeButtonEnabled(viewPager.getCurrentItem(), numPhotos, previousButton, nextButton);
            }
        });

        return view;
    }

    private void changeButtonEnabled(int index, int size, Button previousButton, Button nextButton)
    {
        previousButton.setEnabled(index != 0);
        nextButton.setEnabled(size - 1 != index);
    }
}