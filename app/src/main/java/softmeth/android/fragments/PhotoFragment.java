package softmeth.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import softmeth.android.MainActivity;
import softmeth.android.R;
import softmeth.android.adapters.TabItemLayoutAdapter;
import softmeth.android.models.Loader;

public class PhotoFragment extends Fragment {
    String[] titles = { "Location", "People" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        // Get arguments
        Bundle bundle = getArguments();
        int albumIndex = bundle.getInt("albumIndex");
        int photoIndex = bundle.getInt("photoIndex");

        // Set the image and filename
        ImageView photoImage = (ImageView) view.findViewById(R.id.photo_image_view);
        photoImage.setImageBitmap(Loader.getPhotoFromAlbum(albumIndex, photoIndex).getImage());

        TextView filenameTextView = (TextView) view.findViewById(R.id.filename_text_view);
        filenameTextView.setText(Loader.getPhotoFromAlbum(albumIndex, photoIndex).getFilename());

        // Set layout of the tab
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tag_tab_layout);
        ViewPager2 viewPager = (ViewPager2) view.findViewById(R.id.tags_viewpager);

        final TabItemLayoutAdapter adapter = new TabItemLayoutAdapter(getActivity(), bundle);
        viewPager.setAdapter(adapter);

        // don't let use swipe between tabs!
        viewPager.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();

        return view;
    }
}