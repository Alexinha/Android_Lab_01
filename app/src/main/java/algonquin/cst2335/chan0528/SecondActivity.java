package algonquin.cst2335.chan0528;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import algonquin.cst2335.chan0528.databinding.ActivityMainBinding;
import algonquin.cst2335.chan0528.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    protected ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // retrieve the Intent object that has the variables from the first page
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        binding.welcomeText.setText("Welcome back " + emailAddress);

        // pre-defined phone call
        // set click listener to button to make phone call
        binding.button.setOnClickListener(click -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = binding.editTextPhone.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        // change the profile pic
        binding.button2.setOnClickListener(click -> {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //get data back from the NEXT activity
            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode() == Activity.RESULT_OK){
                                Intent data = result.getData();
                                // store the image under the variable name "data" as a Bitmap object
                                Bitmap thumbnail = data.getParcelableExtra("data");
                                binding.imageView.setImageBitmap(thumbnail);

                                // save the bitmap that is returned
//                                FileOutputStream fOut = null;
//                                try{
//                                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
//                                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                                    fOut.flush();
//                                    fOut.close();
//                                }
//                                catch(FileNotFoundException e){
//                                    e.printStackTrace();
//                                }
                            }
                        }
                    }
            );
//            // launch the camera intent
            cameraResult.launch(cameraIntent);
        });

        // test if a file exists
//        File file = new File(getFilesDir(), "Picture.png");
//        if(file.exists()){
//            Bitmap theImage = BitmapFactory.decodeFile("")
//        }
    }
}