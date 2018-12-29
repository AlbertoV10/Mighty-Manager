package com.example.albertovenegas.mightymanager;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainLogin extends AppCompatActivity {

    private Button managerButton;
    private Button employeeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        managerButton = (Button) findViewById(R.id.manager_button);
        employeeButton = (Button) findViewById(R.id.employee_button);

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openManagerLoginDialog();
                final AlertDialog.Builder managerLoginPage = new AlertDialog.Builder(MainLogin.this);
                View mView = getLayoutInflater().inflate(R.layout.manager_login_dialog, null);

                Button loginButton = (Button) findViewById(R.id.manager_login_button);
                Button cancelButton = (Button) findViewById(R.id.manager_login_cancel_button);
                final EditText managerUsername = (EditText) findViewById(R.id.manager_login_username);
                final EditText managerPassword = (EditText) findViewById(R.id.manager_login_password);

                loginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = managerUsername.getText().toString();
                        String password = managerPassword.getText().toString();

                        if(username.equals("employee") && password.equals("admin"))
                        {
                            Toast successfulLogin = Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG);
                            successfulLogin.show();
                        }
                        else
                        {
                            Toast failLogin = Toast.makeText(getBaseContext(), "failure", Toast.LENGTH_LONG);
                            failLogin.show();
                        }
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        managerLoginPage.setCancelable(true);
                        finish();
                    }
                });

                managerLoginPage.setView(mView);
            }
        });
    }

//    void openManagerLoginDialog()
//    {
//        final AlertDialog.Builder managerLoginPage = new AlertDialog.Builder(this);
//        View mView = getLayoutInflater().inflate(R.layout.manager_login_dialog, null);
//        managerLoginPage.setView(mView);
//
//        Button loginButton = (Button) findViewById(R.id.manager_login_button);
//        Button cancelButton = (Button) findViewById(R.id.manager_login_cancel_button);
//        final EditText managerUsername = (EditText) findViewById(R.id.manager_login_username);
//        final EditText managerPassword = (EditText) findViewById(R.id.manager_login_password);
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = managerUsername.getText().toString();
//                String password = managerPassword.getText().toString();
//
//                if(username.equals("employee") && password.equals("admin"))
//                {
//                    Toast successfulLogin = Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG);
//                    successfulLogin.show();
//                }
//                else
//                {
//                    Toast failLogin = Toast.makeText(getBaseContext(), "failure", Toast.LENGTH_LONG);
//                    failLogin.show();
//                }
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                managerLoginPage.setCancelable(true);
//                finish();
//            }
//        });
//    }


}
