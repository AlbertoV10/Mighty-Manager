package com.example.albertovenegas.mightymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInPage extends AppCompatActivity {
    private TextView signinTitle;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private Boolean managerType;
    private Boolean employeeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        signinTitle = (TextView) findViewById(R.id.signin_page_title);
        username = (EditText) findViewById(R.id.signin_page_username);
        password = (EditText) findViewById(R.id.signin_page_password);
        loginButton = (Button) findViewById(R.id.signin_page_login_button);
        managerType = getIntent().getExtras().getBoolean("managerUserType");
        employeeType = getIntent().getExtras().getBoolean("employeeUserType");

        if(managerType) {
            signinTitle.setText("Manager Login");
        }
        else {
            signinTitle.setText("Employee Login");
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredentials(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private void validateCredentials(String username, String password) {
        if(managerType) {
            if(username.equals("manager") && password.equals("manageradmin")) {
                Toast.makeText(SignInPage.this, "valid manager", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignInPage.this, "invalid manager", Toast.LENGTH_SHORT).show();
            }
        }
        else if(employeeType) {
            if(username.equals("employee") && password.equals("employeeadmin")) {
                Toast.makeText(SignInPage.this, "valid employee", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignInPage.this, "invalid employee", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
