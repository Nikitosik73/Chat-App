package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityLoginBinding;
import com.google.android.gms.common.internal.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextInputEmail.getText().toString().trim();
                String password = binding.editTextInputPassword.getText().toString().trim();
                if (email.isEmpty() && password.isEmpty()){
                    Toast.makeText(
                            LoginActivity.this,
                            "Вы не заполнили не одного поля",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Заполните поле email!!!",
                            Toast.LENGTH_SHORT
                    ).show();
                }else if (password.isEmpty()) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Заполните поле password!!!",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Intent intent = new Intent(
                            LoginActivity.this,
                            ChatActivity.class
                    );
                    startActivity(intent);
                }
                // login
            }
        });
        binding.textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        LoginActivity.this,
                        ForgotPasswordActivity.class
                );
                startActivity(intent);
            }
        });
        binding.textViewRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        LoginActivity.this,
                        RegistrationActivity.class
                );
                startActivity(intent);
            }
        });
    }
}