package softmeth.android.adapters;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softmeth.android.MainActivity;
import softmeth.android.R;
import softmeth.android.models.Album;

public class AlbumItemAdapter extends RecyclerView.Adapter<AlbumItemAdapter.ViewHolder> {

    private ArrayList<Album> localDataSet;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public AlbumItemAdapter(ArrayList<Album> dataSet) {
        localDataSet = dataSet;
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
            // Define click listener for the ViewHolder's View

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
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        if (localDataSet == null)
            Toast.makeText(viewHolder.thumbnail.getContext(), "Empty Album list", Toast.LENGTH_LONG).show();
        else
        {
            System.out.println(localDataSet.isEmpty());
            if (!localDataSet.isEmpty())
            {
                String caption = localDataSet.get(position).toString();
                Bitmap image = localDataSet.get(position).getPhotos().get(0).getImage();

                viewHolder.getTextView().setText(caption);
                viewHolder.getImageView().setImageBitmap(image);
            }
            else
                Toast.makeText(viewHolder.thumbnail.getContext(), "Empty album", Toast.LENGTH_LONG).show();
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
