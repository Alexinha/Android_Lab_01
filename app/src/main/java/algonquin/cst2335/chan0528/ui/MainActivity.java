package algonquin.cst2335.chan0528.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.chan0528.data.MainViewModel;
import algonquin.cst2335.chan0528.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override //this is the starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do what the parent does

        // connect the AppCompactActivity to the ViewModel class that is created
        model = new ViewModelProvider(this).get(MainViewModel.class);

        // view binding
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());

        // loads XML on screen
        setContentView(variableBinding.getRoot());

        // make button event listeners, associate it with edited text (without view binding)
        // Button btn = findViewById(R.id.mybutton);

        // with view binding
        Button btn = variableBinding.mybutton;
        TextView theText = variableBinding.theText;
        EditText myedit = variableBinding.myedittext;

        // without Lambda function
//        btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                EditText myedit = findViewById(R.id.myedittext);
//                // Code here executes on main thread after user presses button
//                String editString = myedit.getText().toString(); // get what text is current in the EditText
//                myedit.setText("Your edit text has: " + editString); // set the string on the TextView
//            }
//        });

        // with Lambda function, View.OnClickListener interface has only one function, then no need to
        // declare a class, declare a function and everything
        // it's important that the getText().toString() is called after the event listener is triggered
        btn.setOnClickListener( view -> {
            // postValue(): changes the value of the variable it stores, saves the value, notifies any objects
            // that are observing this variable for changes
            model.editString.postValue(myedit.getText().toString()); // for View Model

//            myedit.setText(("Your edit text has: " + myedit.getText().toString()));
        });

        // for any objects that are observing the variable for changes
        model.editString.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                theText.setText("Your edit text has: " + s);
            }
        });





    }
}