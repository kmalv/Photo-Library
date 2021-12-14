package softmeth.android.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import softmeth.android.fragments.PhotoFragment;
import softmeth.android.models.Loader;

public class PhotoInfoAdapter extends FragmentStateAdapter {
    private Bundle bundle;

    public PhotoInfoAdapter(@NonNull FragmentActivity fragmentActivity, Bundle bundle) {
        super(fragmentActivity);
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PhotoFragment photoFragment = new PhotoFragment();

        // Add new arguments to the fragment which specifies where we are in the list
        Bundle newBundle = new Bundle();
        newBundle.putInt("albumIndex", bundle.getInt("albumIndex"));
        newBundle.putInt("photoIndex", position);

        photoFragment.setArguments(newBundle);

        return photoFragment;
    }

    @Override
    public int getItemCount() {
        return Loader.getAlbum(bundle.getInt("albumIndex")).getNumPhotos();
    }
}
