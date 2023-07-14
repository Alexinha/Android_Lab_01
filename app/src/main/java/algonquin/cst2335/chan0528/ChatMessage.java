package algonquin.cst2335.chan0528;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * THis class stores the data required for each message sent (message,
 * time sent, and which button was clicked).
 */
@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public long id;

    @ColumnInfo(name="message")
    String message;

    @ColumnInfo(name="TimeSent")
    String timeSent;

    @ColumnInfo(name="SendOrReceive")
    boolean isSentButton;

    // constructor
    public ChatMessage(String m, String t, boolean sent){
        this.message = m;
        this.isSentButton = sent;
        this.timeSent = t;
    }

    // for database queries
    public ChatMessage(){}
    // getters
    public String getMessage(){
        return this.message;
    }

    public String getTimeSent(){
        return this.timeSent;
    }

    public boolean getIsSentButton(){
        return this.isSentButton;
    }
}
