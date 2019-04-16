package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class FirstTimeSignUp extends AppCompatActivity {
    private TextView firstLoginMessage;
    private TextView username;
    private EditText fName;
    private EditText lName;
    private EditText phone;
    private EditText email;
    private Button verifyButton;

    private MightyManagerViewModel mmvm;
    private Employee currentEmployee;

    //save current data incase there is a change
    private String currentFName;
    private String currentLName;
    private String currentPhone;
    private String currentEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_sign_up);

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        int id = getIntent().getExtras().getInt("user");
        currentEmployee = mmvm.findEmployeeById(id);

        firstLoginMessage = findViewById(R.id.first_login_message);
        username = findViewById(R.id.first_login_username);
        fName = findViewById(R.id.first_login_first_name);
        lName = findViewById(R.id.first_login_last_name);
        phone = findViewById(R.id.first_login_phone);
        email = findViewById(R.id.first_login_email);
        verifyButton = findViewById(R.id.first_login_verify_button);

        firstLoginMessage.setText("Welcome "  + currentEmployee.getEmployeeFirstName() + " " + currentEmployee.getEmployeeLastName() + "! " +
                "Below you will find your username and some personal information. You will use this username to login. Please verify your profile.");
        username.setText(currentEmployee.getEmployeeUsername());
        fName.setText(currentEmployee.getEmployeeFirstName());
        lName.setText(currentEmployee.getEmployeeLastName());
        phone.setText(currentEmployee.getEmployeePhone());
        email.setText(currentEmployee.getEmployeeEmail());

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAndUpdate();
            }
        });
    }

    private void verifyAndUpdate() {
       
    }
}
