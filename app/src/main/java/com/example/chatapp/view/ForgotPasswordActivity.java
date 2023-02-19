package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ActivityForgotPasswordBinding;
import com.example.chatapp.viewmodel.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;

    private static final String EXTRA_EMAIL = "email";

    private ForgotPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        observable();

        String email = getIntent().getStringExtra(EXTRA_EMAIL);
        binding.editInputEmail.setText(email);

        binding.buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendEmail = binding.editInputEmail.getText().toString().trim();
                // sendEmail
                viewModel.sendEmail(sendEmail);
                if (sendEmail.isEmpty()){
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            "Заполните поле \'email\' для сбороса пароля",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private void observable() {

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            error,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        viewModel.getSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(
                            ForgotPasswordActivity.this,
                            R.string.send_email,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context, String email) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }
}