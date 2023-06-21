package algonquin.cst2335.chan0528;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    protected TextView textView = null;

    /**
     * This is for password input
     */
    protected EditText password = null;

    /**
     * This is the button for performing password check action
     */
    protected Button button = null;

    @Override //this is the starting point
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // do what the parent does

        // R is the res folder under app/src/main
        // layout is the layout folder under res
        // activity_main is an integer (according to the documentation)
        setContentView(R.layout.activity_main); // loads the screen

        textView = findViewById(R.id.textView);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        button.setOnClickListener( click -> {
            String psw = password.getText().toString();
            if(checkPasswordComplexity(psw)){
                textView.setText("Your password meets the requirements.");
            }else{
                textView.setText("You shall not pass!");
            }

        });

    }

    /**
     * This method checks the complexity of the provided password.
     * The password must contain at least one upper case, one lower case, one number and
     * one special character.
     *
     * @param psw the password to be checked for complexity
     * @return true if the password meets the complexity requirements, false otherwise
     */
    boolean checkPasswordComplexity(String psw){

        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundNumber = foundSpecial = foundLowerCase = foundUpperCase = false;

        for(int i = 0; i < psw.length(); i++){
            char c = psw.charAt(i);
            if(isDigit(c)){
                foundNumber = true;
            }
            if(isUpperCase(c)){
                foundUpperCase = true;
            }
            if(isLowerCase(c)){
                foundLowerCase = true;
            }
            if(isSpecialCharacter(c)){
                foundSpecial = true;
            }
        }
        if(!foundUpperCase){
            Toast.makeText(this, "Missing upper case letter", Toast.LENGTH_SHORT).show();
        }else if(!foundLowerCase){
            Toast.makeText(this, "Missing lower case letter", Toast.LENGTH_SHORT).show();
        }else if(!foundNumber){
            Toast.makeText(this, "Missing number", Toast.LENGTH_SHORT).show();
        }else if(!foundSpecial){
            Toast.makeText(this, "Missing special character", Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }
        return false;
    } // checkPasswordComplexity

    /**
     * This method checks if the given character is a special character.
     *
     * @param c the character to be checked
     * @return true if the character is a special character, false otherwise
     */
    protected boolean isSpecialCharacter(char c){

        char[] specialCharacters = {'#', '%', '$', '^', '&', '!', '*', '@', '?'};

        for(int j = 0; j < specialCharacters.length; j++){
            if(c == specialCharacters[j]){
                return true;
            }
        }
        return false;
    }
} // MainActivity