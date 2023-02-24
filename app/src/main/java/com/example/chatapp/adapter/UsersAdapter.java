package com.example.chatapp.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.data.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    private onUsersClickListener onUsersClickListener;

    public void setOnUsersClickListener(UsersAdapter.onUsersClickListener onUsersClickListener) {
        this.onUsersClickListener = onUsersClickListener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.user_item,
                parent,
                false
        );
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        User user = users.get(position);

        String userInfo = String.format(
                "%s %s, %s", user.getName(), user.getLastName(), user.getAge()
        );
        holder.textViewUsersInfo.setText(userInfo);

        int backgroundIsResult;

        if (user.isOnline()){
            backgroundIsResult = R.drawable.circle_green;
        } else {
            backgroundIsResult = R.drawable.circle_red;
        }

        Drawable background = ContextCompat.getDrawable(
                holder.itemView.getContext(), backgroundIsResult
        );
        holder.viewIsStatus.setBackground(background);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUsersClickListener != null){
                    onUsersClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface onUsersClickListener{

        void onUserClick(User user);
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewUsersInfo;
        private View viewIsStatus;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsersInfo = itemView.findViewById(R.id.textViewInfoUser);
            viewIsStatus = itemView.findViewById(R.id.viewIsStatus);
        }
    }
}
