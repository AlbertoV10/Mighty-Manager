package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Organization;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class SignInPage extends AppCompatActivity {
    private TextView signinTitle;
    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button signupButton;
    private Button setUpOrgButton;
    private Button cancelButton;
    private Boolean managerType;
    private Boolean employeeType;
    private MightyManagerViewModel mmvm;
    private Employee user;
    private List<Organization> org;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        //hide action bar
        getSupportActionBar().hide();

        //signinTitle = (TextView) findViewById(R.id.signin_page_title);
        username = (EditText) findViewById(R.id.signin_page_username);
        password = (EditText) findViewById(R.id.signin_page_password);
        loginButton = (Button) findViewById(R.id.signin_page_login_button);
        signupButton = findViewById(R.id.signin_page_first_use_button);
        setUpOrgButton = findViewById(R.id.signin_page_setup_org_button);
        //cancelButton = (Button) findViewById(R.id.signin_page_cancel_button);
        //managerType = getIntent().getExtras().getBoolean("managerUserType");
        //employeeType = getIntent().getExtras().getBoolean("employeeUserType");
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        org = mmvm.getOrganizations();
        if (org.size() != 0) {
            Toast.makeText(this, "setupOrgButton will be invisible", Toast.LENGTH_SHORT).show();
        }



//        if(managerType) {
//            signinTitle.setText("Manager Login");
//        }
//        else {
//            signinTitle.setText("Employee Login");
//        }
        //signinTitle.setText("Login");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mmvm.findEmployeeByUsername(username.getText().toString());
                validateCredentials(username.getText().toString(), password.getText().toString());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newUser = new Intent(SignInPage.this, FirstTimeSignUp.class);
                startActivity(newUser);
            }
        });

        setUpOrgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setUpOrgIntent = new Intent(SignInPage.this, CreateOrganization.class);
                startActivity(setUpOrgIntent);
            }
        });

//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goBack = new Intent(SignInPage.this, MainLogin.class);
//                startActivity(goBack);
//            }
//        });
    }

    private void validateCredentials(String username, String password) {
        if (user == null) {
            Toast.makeText(this, "user is null", Toast.LENGTH_LONG).show();
        }
        else {
            if (username.equals(user.getEmployeeUsername()) && password.equals(user.getEmployeePassword())) {
                    Toast.makeText(SignInPage.this, "valid", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInPage.this, MainAppScreen.class);
                    intent.putExtra("user", user.getEmployeeID());
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInPage.this, "invalid", Toast.LENGTH_SHORT).show();
                }
//            if (managerType) {
//                if (username.equals(user.getEmployeeUsername()) && password.equals(user.getEmployeePassword())) {
//                    Toast.makeText(SignInPage.this, "valid manager", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignInPage.this, MainAppScreen.class);
//                    intent.putExtra("user", user.getEmployeeID());
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SignInPage.this, "invalid manager", Toast.LENGTH_SHORT).show();
//                }
//
//            } else if (employeeType) {
//                if (username.equals(user.getEmployeeUsername()) && password.equals(user.getEmployeePassword())) {
//                    Toast.makeText(SignInPage.this, "valid employee", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignInPage.this, MainAppScreen.class);
//                    intent.putExtra("user", user.getEmployeeID());
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(SignInPage.this, "invalid employee", Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }
}
