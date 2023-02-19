package com.example.chatapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public ForgotPasswordViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public void sendEmail(String email) {

        if (!email.isEmpty()) {
             auth.sendPasswordResetEmail(email)
                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unused) {
                             success.setValue(true);
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             error.setValue(e.getMessage());
                         }
                     });
        }
    }
}
