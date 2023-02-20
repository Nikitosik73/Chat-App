package com.example.chatapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.chatapp.R;
import com.example.chatapp.adapter.UsersAdapter;
import com.example.chatapp.data.User;
import com.example.chatapp.databinding.ActivityUsersBinding;
import com.example.chatapp.viewmodel.UsersViewModel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    private UsersViewModel viewModel;

    private UsersAdapter usersAdapter = new UsersAdapter();

    private ActivityUsersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        binding.recyclerViewUsers.setAdapter(usersAdapter);

        // Проверка адаптера
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            User user = new User(
                    "id" + i,
                    "Nikita" + i,
                    "Paramonov" + i,
                    i,
                    new Random().nextBoolean()
            );
            users.add(user);
        }
        usersAdapter.setUsers(users);

        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null){
                    Intent intent = new Intent(
                            UsersActivity.this,
                            LoginActivity.class
                    );
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuChat){
            viewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, UsersActivity.class);
    }
}