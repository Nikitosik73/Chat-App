package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.databinding.ActivityRegistrationBinding;
import com.example.chatapp.viewmodel.RegistrationViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        observable();

        binding.buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTrimmedValue(binding.editInputEmail);
                String password = getTrimmedValue(binding.editTextInputPassword);
                String name = getTrimmedValue(binding.editInputName);
                String lastName = getTrimmedValue(binding.editInputLastName);
                String age = getTrimmedValue(binding.editInputAge);

                if (
                        email.isEmpty() && password.isEmpty() && name.isEmpty()
                                && lastName.isEmpty() && age.isEmpty()
                ) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Вы не заполнили не одного поля",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Вы не заполнили поле email!",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Вы не заполнили поле password",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Вы не заполнили поле name!",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (lastName.isEmpty()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Вы не заполнили поле lastname!",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (age.isEmpty()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "Вы не заполнили поле age!",
                            Toast.LENGTH_SHORT
                    ).show();
                }

                // create account
                viewModel.registration(email, password, name, lastName, age);
            }
        });
    }

    private void observable() {

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            error,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {

                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(
                            RegistrationActivity.this,
                            firebaseUser.getUid()
                    );
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}