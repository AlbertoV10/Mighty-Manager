package com.example.albertovenegas.mightymanager.UserInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.R;

public class ChangePassword extends AppCompatActivity {
    private TextView message;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        message = findViewById(R.id.change_password_message);
        newPassword = findViewById(R.id.change_password_new_password);
        confirmPassword = findViewById(R.id.change_password_confirm_password);
        changePasswordButton = findViewById(R.id.change_password_button);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAndChange();
            }
        });
    }

    private void confirmAndChange() {
        if (newPassword.getText().toString().equals(confirmPassword.getText().toString())) {

        }
    }
}
