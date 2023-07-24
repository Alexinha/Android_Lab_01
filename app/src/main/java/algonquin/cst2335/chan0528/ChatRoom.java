package algonquin.cst2335.chan0528;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ActionBar;
import android.app.Notification;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;


import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.chan0528.R;
import algonquin.cst2335.chan0528.data.ChatRoomViewModel;
import algonquin.cst2335.chan0528.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.chan0528.databinding.ReceiveMessageBinding;
import algonquin.cst2335.chan0528.databinding.SentMessageBinding;

/**
 * this is a chat room
 */
public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
//    ArrayList<String> messages = new ArrayList<>();
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    ChatMessageDAO mDAO;
    MessageDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // binding
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // tool bar
        Toolbar toolbar = binding.myToolBar;
        setSupportActionBar(toolbar);

        FrameLayout fragmentLocation = findViewById(R.id.fragmentLocation);

        boolean IAmTablet = fragmentLocation != null;

        // view model
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        // register a listener to the MutableLiveData object
        chatModel.selectedMessage.observe(this, newChatMessage -> {

            if(newChatMessage != null){

                // this is a Singleton object
                FragmentManager fMgr = getSupportFragmentManager();

                FragmentTransaction tx = fMgr.beginTransaction();

                // what to show
                MessageDetailsFragment chatFragment = new MessageDetailsFragment( newChatMessage );

                // where to load
                tx.replace(R.id.fragmentLocation, chatFragment);
                tx.addToBackStack("anything here");

                // run the transaction
                tx.commit();

            }

        });

        // open a database
        db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        // verify the chatModel messages variable has never been set before
        if(messages == null){
            chatModel.messages.setValue(messages = new ArrayList<ChatMessage>());

            // run the query in a separate thread
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                List<ChatMessage> allMessages = mDAO.getAllMessages();
                messages.addAll(allMessages);
                runOnUiThread(() -> {
                    binding.recycleView.setAdapter(myAdapter);
                });
            });
        }

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            // this function creates a viewHolder object. It represents a single row
            // in the list
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                if(viewType == 1){
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            // this initializes a viewHolder to go at the row specified by the position parameter
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                holder.timeText.setText("");

                // retrieve the object that goes in row "position" in this list
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage()); // set the message text
                holder.timeText.setText(obj.getTimeSent());

            }

            // this function returns an int specifying how many items to draw
            // returns the number of rows in the list, in this case it's the size of the list
            @Override
            public int getItemCount() {
                return messages.size();
            }

            // this function returns an int which is the parameter which gets passed into the
            // onCreateViewHolder() function
            // if want to load different layouts for different rows, it lets you return an int to indicate
            // which layout to load
            @Override
            public int getItemViewType(int position) {
                ChatMessage obj = messages.get(position);
                if(obj.isSentButton == true){
                    return 0;
                }
                return 1;
            }
        }); // setAdapter

        /**
         * this is the listener for the "Send message" button
         */
        binding.messageButton.setOnClickListener(click -> {

            // get the message sent
            String newMessage = binding.messageEdit.getText().toString();
            // get the time stamp
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy hh-mm-ss a");
            String timeStamp = sdf.format(new Date());
            // set the boolean value
            boolean isSentButton = true;

            ChatMessage newMsg =  new ChatMessage(newMessage, timeStamp, isSentButton);

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute( () -> {
                newMsg.id = mDAO.insertMessage(newMsg);
            });

            messages.add(newMsg);

            // tell the adapter which row has to be redrawn
            // updating the back of the ArrayList, so the position is the last one
            myAdapter.notifyItemInserted(messages.size() - 1);

            // clear the previous text
            binding.messageEdit.setText("");
        }); // click listener for send button

        /**
         * this is the listener for the "receive message" button
         */
        binding.receiveButton.setOnClickListener(click -> {

            // get the message sent
            String newMessage = binding.messageEdit.getText().toString();
            // get the time stamp
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy hh-mm-ss a");
            String timeStamp = sdf.format(new Date());
            // set the boolean value
            boolean isSentButton = false;

            ChatMessage newMsg =  new ChatMessage(newMessage, timeStamp, isSentButton);

            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute( () -> {
                newMsg.id = mDAO.insertMessage(newMsg);
            });

            messages.add(newMsg);

            messages.add(new ChatMessage(newMessage, timeStamp, isSentButton));

            // tell the adapter which row has to be redrawn
            // updating the back of the ArrayList, so the position is the last one
            myAdapter.notifyItemInserted(messages.size() - 1);

            // clear the previous text
            binding.messageEdit.setText("");
        }); // click listener for receive button

        // specify a single column scrolling in a vertical direction
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));


    } // onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    /**
     * this is the function that is triggered when click on a menu item
     * @param item The menu item that was selected.
     *
     * @return a boolean value
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);

        // chat message deletion code here
        // when the item is clicked, an alert pops up asking if the user wants to
        // delete this message
        if(item.getItemId() == R.id.item_1){
            ChatMessage msgOpened = chatModel.selectedMessage.getValue();

            int position = messages.indexOf(msgOpened);

            if(msgOpened != null){

                builder.setMessage("Delete this message: " + msgOpened.message);
                builder.setTitle("Question: ");

                builder.setPositiveButton("Yes", ((dialog, clk) -> {
                    ChatMessage deletedMsg = msgOpened;

                    Executor thread2 = Executors.newSingleThreadExecutor();
                    thread2.execute(() -> {
                        mDAO.deleteMessage(deletedMsg);
                    });

                    messages.remove(position);
                    myAdapter.notifyItemRemoved(position);
                })); // set positive button

                builder.setNegativeButton("No", ((dialog, clk) -> {
                    // don't do anything
                }));

                builder.create().show();
            }else{
                Toast.makeText(this, "You need to select a message first", Toast.LENGTH_LONG).show();
            }

        } else if (item.getItemId() == R.id.item_2){

            Toast.makeText(this, "Version 1.0, created by Jingxuan Chang", Toast.LENGTH_LONG).show();

        }

        return true;
    }

    // inner class inside the ChatRoom which will be an object for representing
    // everything that goes on a row in the list
    class MyRowHolder extends RecyclerView.ViewHolder{
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView){
            super(itemView);


//             when click on a row, we'd like to load an alert window asking if you want to delete tis row
//            itemView.setOnClickListener( click -> {
//
//                // get the row position
//                int position = getAbsoluteAdapterPosition();
//
//                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this);
//                builder.setMessage("Do you want to delete the message: " + messageText.getText())
//
//                // set the title of the alert dialog
//                .setTitle("Question:")
//
//                // set the buttons for user answers
//                .setNegativeButton("No", (dialog, cl) -> {
//                    // when click on no, doesn't change anything, so leave empty
//                })
//                .setPositiveButton("Yes", (dialog, cl) -> {
//                    ChatMessage m = messages.get(position);
//                    Executor thread1 = Executors.newSingleThreadExecutor();
//                    thread1.execute(()->{
//                        mDAO.deleteMessage(m);
//                        messages.remove(position); // remove from the array list
//                    });
//
//                    // must be done on the main UI thread
//                    runOnUiThread(()->{
//                        myAdapter.notifyDataSetChanged();
//                    });
//
//                    Snackbar.make(messageText, "Delete your message #" + position, Snackbar.LENGTH_LONG)
//                            .setAction("UNDO", clk -> {
//                                Executor threadSnack = Executors.newSingleThreadExecutor();
//                                threadSnack.execute( () -> {
//                                    mDAO.insertMessage(m);
//                                    messages.add(position, m);
//                                    runOnUiThread( () -> myAdapter.notifyDataSetChanged());
//                                });
//                            })
//                            .show();
//
//                }) // setPositiveButton
//                .create().show();
//            }); // click listener

            // use click listener to show details about a message if it's a tablet,
            // or load a new fragment if it's a phone
            itemView.setOnClickListener( click -> {
                int position = getAbsoluteAdapterPosition();
                ChatMessage selected = messages.get(position);

                chatModel.selectedMessage.postValue(selected);
            });


            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

        } // MyRowHolder constructor
    } // MyRowHolder class
}// chat room