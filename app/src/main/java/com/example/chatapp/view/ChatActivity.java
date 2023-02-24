package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chatapp.R;
import com.example.chatapp.adapter.MessagesAdapter;
import com.example.chatapp.data.Message;
import com.example.chatapp.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";

    private ActivityChatBinding binding;

    private String currentUserId;
    private String otherUserId;

    private MessagesAdapter messagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        messagesAdapter = new MessagesAdapter(currentUserId);

        binding.recyclerViewMessage.setAdapter(messagesAdapter);

        // Проверяем, что всё работает
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Message message = new Message(
                    "Text" + i,
                    currentUserId,
                    otherUserId
            );
            messages.add(message);
        }
        for (int i = 0; i < 10; i++){
            Message message = new Message(
                    "Text" + i,
                    otherUserId,
                    currentUserId
            );
            messages.add(message);
        }
        messagesAdapter.setMessages(messages);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}