package algonquin.cst2335.chan0528;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.chan0528.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage selected;

    public MessageDetailsFragment(ChatMessage m){
        selected = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater);

        binding.messageFragment.setText(selected.message);
        binding.timeFragment.setText(selected.timeSent);
        binding.sendReceiveFragment.setText("Send? " + selected.isSentButton);
        binding.databaseIdFragment.setText("id = " + selected.id);

        return binding.getRoot();
    }
}
