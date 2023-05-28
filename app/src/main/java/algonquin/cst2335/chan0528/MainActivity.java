package algonquin.cst2335.chan0528;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Switch sw;

    @Override //this is the starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do what the parent does

        // R is the res folder under app/src/main
        // layout is the layout folder under res
        // activity_main is an integer (according to the documentation)
        setContentView(R.layout.activity_main); // loads the screen

        imageView = findViewById(R.id.flagView);
        sw = findViewById(R.id.spin_switch);

        sw.setOnCheckedChangeListener((s, isChecked) -> {
            if(isChecked){
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imageView.startAnimation(rotate);
            }
            else {
                imageView.clearAnimation();
            }
        });
    }
}