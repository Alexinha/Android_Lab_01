package algonquin.cst2335.chan0528;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2335.chan0528.databinding.ActivityMainBinding;
import algonquin.cst2335.chan0528.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    protected ActivitySecondBinding binding;

    @Override
    protected void onPause() {
        super.onPause();
        // create a SharedPreferences object that stores the phone number
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        int phoneNumber = prefs.getInt("PhoneNumber", 0);

        // save the phone number that is typed in
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("PhoneNumber", Integer.parseInt(binding.editTextPhone.getText().toString()));
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // retrieve the Intent object that has the variables from the first page
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        binding.welcomeText.setText("Welcome back " + emailAddress);

        //load and retrieve the phone number that has been saved
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.editTextPhone.setText(String.valueOf(prefs.getInt("PhoneNumber", 0)));

        // pre-defined phone call
        // set click listener to button to make phone call
        binding.button.setOnClickListener(click -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNumber = binding.editTextPhone.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        });

        // change the profile pic
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
                            FileOutputStream fOut = null;
                            try{
                                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                            catch(FileNotFoundException e){
                                e.printStackTrace();
                            }catch(IOException e){
                                throw new RuntimeException(e);
                            }

                            // find the file that is saved and open it
                            File file = new File(getFilesDir(), "Picture.png");
                            // test if a file exists
                            if(file.exists()){
                            Bitmap theImage = BitmapFactory.decodeFile(file.getPath());
                            binding.imageView.setImageBitmap(theImage);
                            }
                        }
                    }
                }
        );
        binding.button2.setOnClickListener(click -> {
            // launch the camera intent
            cameraResult.launch(cameraIntent);
        });
    }
}