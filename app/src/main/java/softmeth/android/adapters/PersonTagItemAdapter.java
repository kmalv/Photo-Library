package softmeth.android.adapters;

import android.app.Person;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.models.Album;
import softmeth.android.models.Tag;

public class PersonTagItemAdapter extends RecyclerView.Adapter<PersonTagItemAdapter.ViewHolder> {
    private final ArrayList<Tag> localDataSet;
    private final Context context;
    private int selectedIndex = RecyclerView.NO_POSITION;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet ArrayList<String></String> containing the data to populate views to be used
     * by RecyclerView.
     */
    public PersonTagItemAdapter(Context context, ArrayList<Tag> dataSet) {
        this.context = context;
        this.localDataSet = dataSet;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tagValue;

        public ViewHolder(View view) {
            super(view);

            // To allow each item to be clicked
            view.setClickable(true);

            tagValue = (TextView) view.findViewById(R.id.person_tag_value_textview);
        }

        public TextView getTextView() {
            return tagValue;
        }
    }
    @NonNull
    @Override
    public PersonTagItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_tag_item, parent, false);

        return new PersonTagItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonTagItemAdapter.ViewHolder viewHolder, int position) {
        // to prevent "do not treat position as fixed" error
        int pos = viewHolder.getAdapterPosition();

        // Ensure that the dataset (list of albums) can actually be rendered
        if (localDataSet == null)
            return;
        else if (localDataSet.isEmpty())
            return;
        else {
            // Ensure there is actually data at this position in the string ArrayList
            if (localDataSet.get(pos) != null) {
                String value = localDataSet.get(pos).getValue();

                viewHolder.getTextView().setText(value);

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
                        if (selectedIndex == pos) {
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
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public int getSelectedIndex()
    {
        return selectedIndex;
    }
}
