package algonquin.cst2335.chan0528;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.chan0528.databinding.ActivityMainBinding;

/**
 * MainActivity is the main activity class for the application.
 * It extends the AppCompatActivity class and serves as the entry point of the application.
 * @author Jingxuan Chang
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This holds the text at the center of the screen
     */

    protected String cityName;
    protected RequestQueue queue = null;

    @Override //this is the starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do what the parent does

        // R is the res folder under app/src/main
        // layout is the layout folder under res
        // activity_main is an integer (according to the documentation)


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue = Volley.newRequestQueue(this);

        // forcast button listener
        binding.forcastButton.setOnClickListener(click -> {
            cityName = binding.cityTextField.getText().toString();

            String stringURL = "";
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8") +
                            "&appid=4622d68d2b8d0e693fa02cbc3e3ad945&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }


            // this goes in the button click handler
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response)->{
                        JSONObject main = null;
                        try{
                            main = response.getJSONObject("main");
                            double currentTemp = main.getDouble("temp");
                            double min = main.getDouble("temp_min");
                            double max = main.getDouble("temp_max");
                            int humidity = main.getInt("humidity");

                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject pos0 = weatherArray.getJSONObject(0);
                            String description = pos0.getString("description");
                            String iconName = pos0.getString("icon");
                            String pictureURL = "https://openweathermap.org/img/w/" + iconName +".png";

                            // download the URL and store it as a bitmap
                            ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {int i = 0;}
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                    (error) -> {
                                    int i = 0;
                            }); // imagerequest
                            queue.add(imgReq);

//                            FileOutputStream fOut = null;
//                            try{
//                                fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
//                                image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                                fOut.flush();
//                                fOut.close();
//                            }catch(FileNotFoundException e){
//                                e.printStackTrace();
//                            }

                            runOnUiThread(() -> {

                                binding.temp.setText("The temperature is " + currentTemp + " degrees");
                                binding.temp.setVisibility(View.VISIBLE);
                                binding.maxTemp.setText("The max temperature is " + max + " degrees");
                                binding.maxTemp.setVisibility(View.VISIBLE);
                                binding.minTemp.setText("The min temperature is " + min + " degrees");
                                binding.minTemp.setVisibility(View.VISIBLE);
                                binding.humidity.setText("The humidity is " + humidity + ".");
                                binding.humidity.setVisibility(View.VISIBLE);
                                binding.description.setText(description);
                                binding.description.setVisibility(View.VISIBLE);


                            });

                        }catch(JSONException e){

                            e.printStackTrace();

                        }
                    },
                    (error)->{
                        int i = 0;
                    });
            queue.add(request);
        }); // click listener

    }

//    /**
//     * This method checks the complexity of the provided password.
//     * The password must contain at least one upper case, one lower case, one number and
//     * one special character.
//     *
//     * @param psw the password to be checked for complexity
//     * @return true if the password meets the complexity requirements, false otherwise
//     */
//    boolean checkPasswordComplexity(String psw){
//
//        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
//        foundNumber = foundSpecial = foundLowerCase = foundUpperCase = false;
//
//        for(int i = 0; i < psw.length(); i++){
//            char c = psw.charAt(i);
//            if(isDigit(c)){
//                foundNumber = true;
//            }
//            if(isUpperCase(c)){
//                foundUpperCase = true;
//            }
//            if(isLowerCase(c)){
//                foundLowerCase = true;
//            }
//            if(isSpecialCharacter(c)){
//                foundSpecial = true;
//            }
//        }
//        if(!foundUpperCase){
//            Toast.makeText(this, "Missing upper case letter", Toast.LENGTH_SHORT).show();
//        }else if(!foundLowerCase){
//            Toast.makeText(this, "Missing lower case letter", Toast.LENGTH_SHORT).show();
//        }else if(!foundNumber){
//            Toast.makeText(this, "Missing number", Toast.LENGTH_SHORT).show();
//        }else if(!foundSpecial){
//            Toast.makeText(this, "Missing special character", Toast.LENGTH_SHORT).show();
//        }else{
//            return true;
//        }
//        return false;
//    } // checkPasswordComplexity

//    /**
//     * This method checks if the given character is a special character.
//     *
//     * @param c the character to be checked
//     * @return true if the character is a special character, false otherwise
//     */
//    protected boolean isSpecialCharacter(char c){
//
//        char[] specialCharacters = {'#', '%', '$', '^', '&', '!', '*', '@', '?'};
//
//        for(int j = 0; j < specialCharacters.length; j++){
//            if(c == specialCharacters[j]){
//                return true;
//            }
//        }
//        return false;
//    }
} // MainActivity