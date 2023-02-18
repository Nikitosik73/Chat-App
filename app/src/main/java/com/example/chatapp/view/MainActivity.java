package com.example.chatapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chatapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signOut();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    Log.d(TAG, "Такого пользователя не существует");
                } else {
                    Log.d(TAG, "Данный пользователь уже зарегистрирован: " + mAuth.getUid());
                }
            }
        });

//        mAuth.createUserWithEmailAndPassword("paramonov20@bk.ru", "1111112")
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        if (user == null) {
//                            Log.d(TAG, "Такого пользователя не существует");
//                        } else {
//                            Log.d(TAG, "Данный пользователь уже зарегистрирован: " + mAuth.getUid());
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                        Toast.makeText(
//                                MainActivity.this,
//                                e.getMessage(),
//                                Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                });

        mAuth.signInWithEmailAndPassword("paramonov20@bk.ru", "yu9742002")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "Пользователь вошёл: " + mAuth.getUid());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getMessage());
                        Toast.makeText(
                                MainActivity.this,
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
        mAuth.sendPasswordResetEmail("paramonov20@bk.ru")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "Письмо отправлено");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                });
    }
}