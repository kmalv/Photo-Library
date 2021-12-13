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
            System.out.println("Null localDataSet");
        else if (localDataSet.isEmpty())
            System.out.println("Empty localDataSet");
        else {
            // Ensure there is actually data inside the album
            if (localDataSet.get(pos) != null)
            {
                String caption = localDataSet.get(pos).toString();
                Bitmap image = null;

                // Ensure there are actually photos in the album
                if (!localDataSet.get(pos).getPhotos().isEmpty())
                    image = localDataSet.get(pos).getPhotos().get(0).getImage();
                else
                    System.out.println("Album at position " + pos + " has no photos.");

                viewHolder.getTextView().setText(caption);
                viewHolder.getImageView().setImageBitmap(image);

                if (selectedIndex == pos)
                    viewHolder.itemView.setBackgroundColor(Color.argb(100, 235, 235, 235));
                else
                    viewHolder.itemView.setBackgroundColor(Color.argb(100, 255, 255, 255));

                // Set onClickListener for each item
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (selectedIndex == viewHolder.getAdapterPosition())
                        {
                            selectedIndex = RecyclerView.NO_POSITION;
                            notifyDataSetChanged();
                            return;
                        }
                        selectedIndex = viewHolder.getAdapterPosition();
                        notifyItemChanged(pos);
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
