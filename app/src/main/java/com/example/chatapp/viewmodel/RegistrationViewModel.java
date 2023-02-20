package com.example.chatapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatapp.data.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    private FirebaseDatabase database;
    private DatabaseReference reference;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void registration(
            String email,
            String password,
            String name,
            String lastname,
            String age
    ){

        if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !lastname.isEmpty() && !age.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            FirebaseUser userDB = authResult.getUser();
                            if (userDB == null){
                                return;
                            }
                            User user = new User(
                                    userDB.getUid(),
                                    name,
                                    lastname,
                                    age,
                                    false
                            );
                            reference.child(user.getId()).setValue(user);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            error.setValue(e.getMessage());
                        }
                    });
        }
    }
}
