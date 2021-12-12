package softmeth.android.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import softmeth.android.fragments.LocationTagFragment;
import softmeth.android.fragments.PersonTagFragment;

public class TabItemLayoutAdapter extends FragmentStateAdapter {
    String[] titles = { "Location", "People" };

    public TabItemLayoutAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 0:
                return new LocationTagFragment();
            case 1:
                return new PersonTagFragment();
        }
        return new LocationTagFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}