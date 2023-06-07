package algonquin.cst2335.chan0528;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import algonquin.cst2335.chan0528.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    protected ActivityMainBinding binding;
    Button loginButton = binding.loginButton;
    EditText editEmail = binding.editEmail;

    @Override //this is the starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do what the parent does

        // R is the res folder under app/src/main
        // layout is the layout folder under res
        // activity_main is an integer (according to the documentation)
//        setContentView(R.layout.activity_main); // loads the screen
        Log.w(TAG, "In onCreate() - Loading Widgets");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(click -> {
            // transfer from MainActivity to SecondActivity
            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
            nextPage.putExtra("EmailAddress", editEmail.getText().toString());
            startActivity(nextPage); // make the transition

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "The application is now visible on screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Any memory used by the application is freed");
    }
}