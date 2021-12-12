package softmeth.android.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import softmeth.android.MainActivity;
import softmeth.android.R;
import softmeth.android.adapters.TabItemLayoutAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

        // Set image (if one was passed)
        if (getArguments() != null) {
            ImageView currentPhoto = view.findViewById(R.id.photo_image_view);
            currentPhoto.setImageURI(getArguments().getParcelable("uri"));
        }

        // Set layout of the tab
        TabLayout tabLayout = view.findViewById(R.id.tag_tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.tags_viewpager);

        final TabItemLayoutAdapter adapter = new TabItemLayoutAdapter(getActivity());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();


        return view;
    }
}