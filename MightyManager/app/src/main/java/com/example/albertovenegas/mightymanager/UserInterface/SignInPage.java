package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;
import com.example.albertovenegas.mightymanager.TestMainScreen.MainAppScreen;
import com.example.albertovenegas.mightymanager.UnusedFiles.MainScreen;

public class SignInPage extends AppCompatActivity {
    private TextView signinTitle;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button cancelButton;
    private Boolean managerType;
    private Boolean employeeType;
    private MightyManagerViewModel mmvm;
    private Employee user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        signinTitle = (TextView) findViewById(R.id.signin_page_title);
        username = (EditText) findViewById(R.id.signin_page_username);
        password = (EditText) findViewById(R.id.signin_page_password);
        loginButton = (Button) findViewById(R.id.signin_page_login_button);
        cancelButton = (Button) findViewById(R.id.signin_page_cancel_button);
        managerType = getIntent().getExtras().getBoolean("managerUserType");
        employeeType = getIntent().getExtras().getBoolean("employeeUserType");
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);


        if(managerType) {
            signinTitle.setText("Manager Login");
        }
        else {
            signinTitle.setText("Employee Login");
        }

//        Employee te = mmvm.findEmployeeByName("employee");
//        if(te == null) {
//            Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(this, te.getEmployeeName(), Toast.LENGTH_LONG).show();
//
//        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mmvm.findEmployeeByName(username.getText().toString());
                validateCredentials(username.getText().toString(), password.getText().toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(SignInPage.this, MainLogin.class);
                startActivity(goBack);
            }
        });
    }

    private void validateCredentials(String username, String password) {
        if (user == null) {
            Toast.makeText(this, "user is null", Toast.LENGTH_LONG).show();
        }
        else {
            if (managerType) {
                if (username.equals(user.getEmployeeName()) && password.equals("manageradmin")) {
                    Toast.makeText(SignInPage.this, "valid manager", Toast.LENGTH_SHORT).show();
//                Intent mainScreen = new Intent(SignInPage.this, MainScreen.class);
//                mainScreen.putExtra("managerUserType", true); //manager boolean is true
//                mainScreen.putExtra("employeeUserType", false); //employee boolean is false
//                startActivity(mainScreen);
                    Intent intent = new Intent(SignInPage.this, MainAppScreen.class);
                    intent.putExtra("user", user.getEmployeeID());
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInPage.this, "invalid manager", Toast.LENGTH_SHORT).show();
                }

            } else if (employeeType) {
                if (username.equals(user.getEmployeeName()) && password.equals("employeeadmin")) {
                    Toast.makeText(SignInPage.this, "valid employee", Toast.LENGTH_SHORT).show();
//                Intent mainScreen = new Intent(SignInPage.this, MainScreen.class);
//                mainScreen.putExtra("managerUserType", false); //manager boolean is true
//                mainScreen.putExtra("employeeUserType", true); //employee boolean is false
//                startActivity(mainScreen);
                    Intent intent = new Intent(SignInPage.this, MainAppScreen.class);
                    intent.putExtra("user", user.getEmployeeID());
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInPage.this, "invalid employee", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
