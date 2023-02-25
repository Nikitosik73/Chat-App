package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.adapter.MessagesAdapter;
import com.example.chatapp.data.Message;
import com.example.chatapp.data.User;
import com.example.chatapp.databinding.ActivityChatBinding;
import com.example.chatapp.viewmodel.ChatViewModel;
import com.example.chatapp.viewmodelfactory.ChatViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";

    private ActivityChatBinding binding;

    private String currentUserId;
    private String otherUserId;

    private MessagesAdapter messagesAdapter;

    private ChatViewModel viewModel;
    private ChatViewModelFactory modelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);

        modelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        viewModel = new ViewModelProvider(this, modelFactory).get(ChatViewModel.class);

        messagesAdapter = new MessagesAdapter(currentUserId);
        binding.recyclerViewMessage.setAdapter(messagesAdapter);

        observable();

        binding.imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = new Message(
                        binding.editTextSendMessage.getText().toString().trim(),
                        currentUserId,
                        otherUserId
                );
                viewModel.sendMessage(message);
            }
        });
    }

    private void observable(){
        viewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String send) {
                if (send != null){
                    Toast.makeText(
                            ChatActivity.this,
                            send,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        viewModel.getMessageSend().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sent) {
                if (sent){
                    binding.editTextSendMessage.setText("");
                }
            }
        });
        viewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getLastName(), user.getName());
                binding.textViewUser.setText(userInfo);

                int backgroundResId;
                if (user.isOnline()){
                    backgroundResId = R.drawable.circle_green;
                } else {
                    backgroundResId = R.drawable.circle_red;
                }
                Drawable background = ContextCompat.getDrawable(
                        ChatActivity.this,
                        backgroundResId
                );
                binding.viewIsStatus.setBackground(background);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUserIsStatus(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setUserIsStatus(false);
    }

    public static Intent newIntent(Context context, String currentId, String otherId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherId);
        return intent;
    }
}