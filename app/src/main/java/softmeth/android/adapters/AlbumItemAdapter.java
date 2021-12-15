package softmeth.android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softmeth.android.MainActivity;
import softmeth.android.R;
import softmeth.android.models.Album;

public class AlbumItemAdapter extends RecyclerView.Adapter<AlbumItemAdapter.ViewHolder> {
    private final ArrayList<Album> localDataSet;
    private final Context context;
    private int selectedIndex = RecyclerView.NO_POSITION;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AlbumItemAdapter(Context context, ArrayList<Album> dataSet) {
        this.context = context;
        this.localDataSet = dataSet;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView caption;
        private final ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);

            // To allow each item to be clicked
            view.setClickable(true);

            caption = (TextView) view.findViewById(R.id.album_caption_text_view);
            thumbnail = (ImageView) view.findViewById(R.id.album_thumbnail);
        }

        public TextView getTextView() {
            return caption;
        }

        public ImageView getImageView() {
            return thumbnail;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_album_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // to prevent "do not treat position as fixed" error
        int pos = viewHolder.getAdapterPosition();

        // Ensure that the dataset (list of albums) can actually be rendered
        if (localDataSet == null)
            return;
        else if (localDataSet.isEmpty())
            return;
        else {
            // Ensure there is actually data inside the album
            if (localDataSet.get(pos) != null)
            {
                String caption = localDataSet.get(pos).toString();
                Bitmap image = null;

                // Ensure there are actually photos in the album
                if (!localDataSet.get(pos).getPhotos().isEmpty())
                    image = localDataSet.get(pos).getPhotos().get(0).getImage();

                viewHolder.getTextView().setText(caption);
                viewHolder.getImageView().setImageBitmap(image);

                System.out.println("SELECTED ITEM: " + selectedIndex + " CURR POS: " + pos);

                // Makes all items that aren't selected have a white background
                // and currently selected item have a selected background color
                if (selectedIndex != pos)
                    viewHolder.itemView.setBackgroundColor(Color.argb(100, 255, 255, 255));
                else
                    viewHolder.itemView.setBackgroundColor(Color.argb(100, 207, 207, 207));

                // Set onClickListener for each item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Handles unclicking a clicked item, changes its bg back to white
                        if (selectedIndex == pos)
                        {
                            selectedIndex = RecyclerView.NO_POSITION;
                            viewHolder.itemView.setBackgroundColor(Color.argb(100, 255, 255, 255));
                            notifyDataSetChanged();
                            return;
                        }
                        // If item is clicked, we want to change its color to show it's selected
                        selectedIndex = pos;
                        viewHolder.itemView.setBackgroundColor(Color.argb(100, 207, 207, 207));
                        notifyDataSetChanged();
                    }
                });
            }
            else
                System.out.println("Null album at position " + pos);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public int getSelectedIndex()
    {
        System.out.println(selectedIndex);
        return selectedIndex;
    }
}
