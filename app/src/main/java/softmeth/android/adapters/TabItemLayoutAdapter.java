package softmeth.android.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import softmeth.android.fragments.LocationTagFragment;
import softmeth.android.fragments.PersonTagFragment;

public class TabItemLayoutAdapter extends FragmentStateAdapter {
    private String[] titles = { "Location", "People" };
    private Bundle bundle;

    public TabItemLayoutAdapter(FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 0:
            {
                LocationTagFragment locationTagFragment = new LocationTagFragment();
                locationTagFragment.setArguments(bundle);

                return locationTagFragment;
            }
            case 1:
            {
                PersonTagFragment personTagFragment = new PersonTagFragment();
                personTagFragment.setArguments(bundle);

                return personTagFragment;
            }
        }
        return new LocationTagFragment();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}