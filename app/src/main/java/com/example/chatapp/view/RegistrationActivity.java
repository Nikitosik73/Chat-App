package com.example.chatapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapp.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTrimmedValue(binding.editInputEmail);
                String password = getTrimmedValue(binding.editTextInputPassword);
                String name = getTrimmedValue(binding.editInputName);
                String lastName = getTrimmedValue(binding.editInputLastName);
                int age = Integer.parseInt(getTrimmedValue(binding.editInputAge));

                if (
                        email.isEmpty() && password.isEmpty() && name.isEmpty() && lastName.isEmpty()
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
                } else {
                    Intent intent = new Intent(
                            RegistrationActivity.this,
                            ChatActivity.class
                    );
                    startActivity(intent);
                }

                // create account
            }
        });
    }

    private String getTrimmedValue(EditText editText){
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context){
        return new Intent(context, RegistrationActivity.class);
    }
}