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

        //save current information
        currentFName = fName.getText().toString();
        currentLName = lName.getText().toString();
        currentEmail = email.getText().toString();
        currentPhone = phone.getText().toString();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAndUpdate();
            }
        });
    }

    private void verifyAndUpdate() {
       if (!fName.getText().toString().equals(currentFName) || !lName.getText().toString().equals(currentLName)
            || !email.getText().toString().equals(currentEmail) || !phone.getText().toString().equals(currentPhone)) {
           currentEmployee.setEmployeeFirstName(fName.getText().toString());
           currentEmployee.setEmployeeLastName(lName.getText().toString());
           currentEmployee.setEmployeePhone(phone.getText().toString());
           currentEmployee.setEmployeeEmail(email.getText().toString());
           //set first sign in flag to false
           currentEmployee.setFirstSignIn(false);
           mmvm.update(currentEmployee);
           Toast.makeText(this, "Verified with changes", Toast.LENGTH_SHORT).show();

       }
        Toast.makeText(this, "Verified", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(FirstTimeSignUp.this, MainAppScreen.class);
        intent.putExtra("user", currentEmployee.getEmployeeID());
        startActivity(intent);
    }
}
