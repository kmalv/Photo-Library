package softmeth.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.MenuPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import softmeth.android.R;
import softmeth.android.adapters.AlbumItemAdapter;
import softmeth.android.adapters.ThumbnailItemAdapter;
import softmeth.android.models.Album;
import softmeth.android.models.Loader;
import softmeth.android.models.Photo;
import softmeth.android.models.User;

public class AlbumFragment extends Fragment {
    private final int SPAN_COUNT = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        // Get the bundle with the album to open
        Bundle bundle = getArguments();
        int index = bundle.getInt("index");

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.thumbnail_recyclerview);
        ThumbnailItemAdapter thumbnailItemAdapter = new ThumbnailItemAdapter(getContext(), Loader.getPhotosFromAlbum(index));

        recyclerView.setAdapter(thumbnailItemAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));

        // For handling the image picker
        ActivityResultLauncher<Intent> imagePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // if photo was successfully added we need to update the recycler view
                        if (addPhotoFromURI(index, result.getData().getData()))
                            thumbnailItemAdapter.notifyDataSetChanged();
                        else
                            Toast.makeText(getContext(), "Could not add photo to this album", Toast.LENGTH_SHORT).show();
                    }
                });

        // Open user's Gallery and prompt them to add a photo
        Button addButton = (Button) view.findViewById(R.id.add_photo_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imagePicker.launch(intent);
            }
        });

        // Open user's Gallery and prompt them to add a photo
        Button moveButton = (Button) view.findViewById(R.id.move_photo_button);
        Button move_to_button = (Button) view.findViewById(R.id.move_to);
        Photo photo_to_move;
        Photo temp;
        Album dest;
        Album origin;
        moveButton.setOnClickListener(new View.OnClickListener() {
//            selected photo
//            move button clicked
//            get list of albums
//            popup window
//            list of albums displayed
//            album selected
//            exit popupwindow
//            selected photo deleted from current album
//            selcted photo added to album selected
            @Override
            public void onClick(View view) {
                int selected = thumbnailItemAdapter.getSelectedIndex();
                if (thumbnailItemAdapter.getItemCount() != 0 && selected != RecyclerView.NO_POSITION) {
                    Object[] albums = Loader.getAlbums().toArray();
                    Bundle bundle = new Bundle();
                    bundle.putInt("albumIndex", index);
                    bundle.putInt("photoIndex", selected);
                    Navigation.findNavController(view).navigate(R.id.action_albumFragment_to_itemFragment, bundle);
                } else {
                    Toast.makeText(getContext(), "No photo was selected. Please select a photo to move.", Toast.LENGTH_SHORT);
                }

            }});


        // Open button listener
        Button openButton = (Button) view.findViewById(R.id.open_photo_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = thumbnailItemAdapter.getSelectedIndex();
                if (thumbnailItemAdapter.getItemCount() != 0 && selected != RecyclerView.NO_POSITION)
                {
                    Bundle bundle = new Bundle();
                    bundle.putInt("albumIndex", index);
                    bundle.putInt("photoIndex", selected);

                    System.out.println("INITIAL: " + selected);

                    Navigation.findNavController(view).navigate(R.id.action_albumFragment_to_photoViewPagerFragment, bundle);
                }
                else
                    Toast.makeText(getContext(), "No photo was selected. Please select a photo to open.", Toast.LENGTH_SHORT);
            }
        });

        // Move  button listener

        // Delete button listener
        Button deleteButton = (Button) view.findViewById(R.id.delete_photo_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int selected = thumbnailItemAdapter.getSelectedIndex();
                if (thumbnailItemAdapter.getItemCount() != 0 && selected != -1)
                {
                    if (Loader.deletePhotoFromAlbum(index, selected))
                        thumbnailItemAdapter.notifyDataSetChanged();
                    else
                        Toast.makeText(getContext(), "Could not delete photo. Please try again.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "No photo was selected. Please select an photo to open.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    // Adds a photo to the album given the album index and a URI
    private boolean addPhotoFromURI(int index, Uri uri)
    {
        ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), uri);

        Bitmap bitmap = null;
        try {
            bitmap = ImageDecoder.decodeBitmap(source);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return Loader.addPhotoToAlbum(index, bitmap, getFileNameFromURI(uri));
    }

    // Given a uri, returns the filename (title) of the image
    private String getFileNameFromURI(Uri uri)
    {
        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        cursor.moveToFirst();
        String temp = new String(cursor.getString(nameIndex));

        return (temp == null) ? "(Could not get filename)" : temp;
    }
}