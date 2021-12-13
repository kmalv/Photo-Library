package softmeth.android.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.models.Album;
import softmeth.android.models.Photo;

public class ThumbnailItemAdapter extends RecyclerView.Adapter<ThumbnailItemAdapter.ViewHolder> {

    private final ArrayList<Photo> localDataSet;
    private final Context context;
    private int selectedIndex = RecyclerView.NO_POSITION;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public ThumbnailItemAdapter(Context context, ArrayList<Photo> dataSet) {
        this.context = context;
        this.localDataSet = dataSet;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);

            // To allow each item to be clicked
            view.setClickable(true);

            thumbnail = (ImageView) view.findViewById(R.id.album_thumbnail);
        }

        public ImageView getImageView() {
            return thumbnail;
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ThumbnailItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_thumbnail_item, viewGroup, false);

        return new ThumbnailItemAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ThumbnailItemAdapter.ViewHolder viewHolder, final int position) {
        // to prevent "do not treat position as fixed" error
        int pos = viewHolder.getAdapterPosition();

        // Ensure that the dataset (list of albums) can actually be rendered
        if (localDataSet == null)
            System.out.println("Null localDataSet");
        else if (localDataSet.isEmpty())
            System.out.println("Empty localDataSet");
        else {
            // Ensure there is actually a photo inside the arraylist
            if (localDataSet.get(pos) != null)
            {
                Bitmap image = null;

                // Ensure there are actually photos in the album
                if (localDataSet.get(pos).getImage() != null)
                    image = localDataSet.get(pos).getImage();
                else
                    System.out.println("Photo at position " + pos + " has no Bitmap.");

                viewHolder.getImageView().setImageBitmap(image);

                if (selectedIndex != pos)
                    viewHolder.itemView.setBackgroundColor(Color.argb(100, 255, 255, 255));

                // Set onClickListener for each item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedIndex == pos)
                        {
                            selectedIndex = RecyclerView.NO_POSITION;
                            notifyDataSetChanged();
                            return;
                        }
                        selectedIndex = pos;
                        viewHolder.itemView.setBackgroundColor(Color.argb(100, 235, 235, 235));
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
