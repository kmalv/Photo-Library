package softmeth.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AlbumFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_album, container, false);

        // For handling the image picker
        ActivityResultLauncher<Intent> imagePicker = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        String imagePath = result.getData().getData().getPath();
                        Toast.makeText(view.getContext(), "Image " + imagePath + " successfully loaded", Toast.LENGTH_LONG).show();

                        // Attempt to navigate with path to load photo
                        Uri uri = result.getData().getData();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("uri", uri);
                        Navigation.findNavController(view).navigate(R.id.action_albumFragment_to_photoFragment, bundle);
                    }
                });

        // Create onClickListener for opening the selected photo
        Button openButton = view.findViewById(R.id.open_photo_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_albumFragment_to_photoFragment);
            }
        });

        // Open user's Gallery and prompt them to add a photo
        Button addButton = view.findViewById(R.id.add_photo_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imagePicker.launch(intent);
            }
        });

        return view;
    }
}