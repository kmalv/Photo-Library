package softmeth.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import softmeth.android.models.Loader;
import softmeth.android.models.User;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Loader.loadUser(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}