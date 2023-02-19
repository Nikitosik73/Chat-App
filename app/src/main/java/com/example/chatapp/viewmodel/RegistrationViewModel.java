package com.example.chatapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationViewModel extends AndroidViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private MutableLiveData<String> error = new MutableLiveData<>();

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
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
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            error.setValue(e.getMessage());
                        }
                    });
        }
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public LiveData<String> getError() {
        return error;
    }
}
