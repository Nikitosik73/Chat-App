package com.example.chatapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private MutableLiveData<List<User>> users = new MutableLiveData<>();

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public UsersViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser currentUser = auth.getCurrentUser();
                if (currentUser == null){
                    return;
                }
                List<User> usersFromDB = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null){
                        return;
                    }
                    if (!user.getId().equals(currentUser.getUid())){
                        usersFromDB.add(user);
                    }
                }
                users.setValue(usersFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("UsersViewModel", "Ошибка чтения базы данных", error.toException());
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logout() {
        auth.signOut();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
