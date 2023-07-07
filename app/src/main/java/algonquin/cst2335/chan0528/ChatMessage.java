package algonquin.cst2335.chan0528;

/**
 * THis class stores the data required for each message sent (message,
 * time sent, and which button was clicked).
 */
public class ChatMessage {

    String message;
    String timeSent;
    boolean isSentButton;

    // constructor
    public ChatMessage(String m, String t, boolean sent){
        this.message = m;
        this.isSentButton = sent;
        this.timeSent = t;
    }

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
