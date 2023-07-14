package algonquin.cst2335.chan0528;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import algonquin.cst2335.chan0528.data.ChatRoomViewModel;

/**
 * Data Access Object that is responsible for running the sql commands.
 * It should be able to insert a ChatMessage, get all the stored messages, and delete a message.
 */
@Dao
public interface ChatMessageDAO {

    /**
     * this function will insert a ChatMessage object and then return the newly created ID as a long.
     * @param m ChatMessage object
     * @return newly created ID
     */
    @Insert
    public long insertMessage(ChatMessage m);

    /**
     * this function gets all the messages stored in the database.
     * the Query annotation specifies the SQL query. Room uses the object's class name as the table name,
     * so in this case, it's ChatMessage.
     * @return a List object contains all the ChatMessage objects
     */
    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessages();

    /**
     * this function deletes rows using primary key
     * @param m ChatMessage object
     */
    @Delete
    void deleteMessage(ChatMessage m);
}
