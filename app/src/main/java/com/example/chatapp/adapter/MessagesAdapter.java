package com.example.chatapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.data.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder> {

    private static final int VIEW_TYPE_MY_MESSAGE = 0;
    private static final int VIEW_TYPE_YOUR_MESSAGE = 1;

    private String currentUserId;

    public MessagesAdapter(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    private List<Message> messages = new ArrayList<>();

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutResId;
        if (viewType == VIEW_TYPE_MY_MESSAGE) {
            layoutResId = R.layout.my_message_item;
        } else {
            layoutResId = R.layout.your_message_item;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(
                layoutResId,
                parent,
                false
        );
        return new MessageViewHolder(view);
    }

    // Проверяем кто отправил сообщение
    @Override
    public int getItemViewType(int position) {

        Message message = messages.get(position);
        if (currentUserId.equals(message.getSenderId())) {
            return VIEW_TYPE_MY_MESSAGE;
        } else {
            return VIEW_TYPE_YOUR_MESSAGE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message message = messages.get(position);
        holder.textViewMessage.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
        }
    }
}
