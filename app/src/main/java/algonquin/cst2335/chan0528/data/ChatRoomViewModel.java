package algonquin.cst2335.chan0528.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.chan0528.ChatMessage;

public class ChatRoomViewModel extends ViewModel {

    // make the arrayList which holds the messages data to survive the rotate
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();
}
