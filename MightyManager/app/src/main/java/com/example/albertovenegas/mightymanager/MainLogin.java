package com.example.albertovenegas.mightymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainLogin extends AppCompatActivity {

    private Button managerButton;
    private Button employeeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        managerButton = (Button) findViewById(R.id.managerLoginButton);
        employeeButton = (Button) findViewById(R.id.employeeLoginButton);
    }


}
