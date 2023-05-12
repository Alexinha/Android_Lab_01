package algonquin.cst2335.chan0528;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override //this is the starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do what the parent does

        // R is the res folder under app/src/main
        // layout is the layout folder under res
        // activity_main is an integer (according to the documentation)
        setContentView(R.layout.activity_main); // loads the screen
    }
}