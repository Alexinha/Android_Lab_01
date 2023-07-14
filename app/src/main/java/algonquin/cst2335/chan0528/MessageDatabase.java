package algonquin.cst2335.chan0528;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * this class has one abstract function that returns the DAO for interacting with this database.
 * in this case, it's ChatMessageDAO.
 */
@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {

    public abstract ChatMessageDAO cmDAO();

}
