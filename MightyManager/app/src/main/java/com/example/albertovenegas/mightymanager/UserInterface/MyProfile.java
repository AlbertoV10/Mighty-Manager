package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class MyProfile extends AppCompatActivity {
    private MightyManagerViewModel mightyManagerViewModel;
    private Employee currentEmployee;
    private int employeeID;
    private TextView username;
    private EditText firstName;
    private EditText lastName;
    //private EditText password;
    private EditText phoneNumber;
    private EditText email;
    private EditText description;
    private TextView employeeType;

    //Strings to save currentDate
    private String currentUserName;
    private String currentFirstName;
    private String currentLastName;
    //private String currentPassword;
    private String currentPhone;
    private String currentEmail;
    private String currentDescription;

    private boolean editable = false;
    private Menu menu;
    private Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //prevent keyboard from opening at start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mightyManagerViewModel = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        employeeID = getIntent().getExtras().getInt(MainAppScreen.MY_PROFILE_USER_ID);
        currentEmployee = mightyManagerViewModel.findEmployeeById(employeeID);

        username = findViewById(R.id.my_profile_username);
        firstName = findViewById(R.id.my_profile_first_name);
        lastName = findViewById(R.id.my_profile_last_name);
        phoneNumber = findViewById(R.id.my_profile_phone);
        email = findViewById(R.id.my_profile_email);
        description = findViewById(R.id.my_profile_employee_description);
        employeeType = findViewById(R.id.my_profile_employee_type);
        changePasswordButton = findViewById(R.id.my_profile_change_password_button);

        username.setText(currentEmployee.getEmployeeUsername());
        firstName.setText(currentEmployee.getEmployeeFirstName());
        firstName.setEnabled(false);
        lastName.setText(currentEmployee.getEmployeeLastName());
        lastName.setEnabled(false);
        phoneNumber.setText(currentEmployee.getEmployeePhone());
        phoneNumber.setEnabled(false);
        email.setText(currentEmployee.getEmployeeEmail());
        email.setEnabled(false);
        description.setText(currentEmployee.getEmployeeDescription());
        description.setEnabled(false);
        if (currentEmployee.isAdmin()) {
            employeeType.setText("Status: Manager");
        }
        else {
            employeeType.setText("Status: Employee");
        }

        //save current data
        currentFirstName = currentEmployee.getEmployeeFirstName();
        currentLastName = currentEmployee.getEmployeeLastName();
        currentPhone = currentEmployee.getEmployeePhone();
        currentEmail = currentEmployee.getEmployeeEmail();
        currentDescription = currentEmployee.getEmployeeDescription();

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePasswordIntent = new Intent(MyProfile.this, ChangePassword.class);
                changePasswordIntent.putExtra("user", currentEmployee.getEmployeeID());
                startActivity(changePasswordIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_task:
                editSaveEmployee();
                return true;
//            case R.id.save_edits:
//                saveTask();
//                return true;
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void editSaveEmployee() {
        if (!editable) {
            //change edit icon to save
            menu.getItem(0).setIcon(R.drawable.ic_save_white);
            //enable the edit text fields
            firstName.setEnabled(true);
            lastName.setEnabled(true);
            phoneNumber.setEnabled(true);
            email.setEnabled(true);
            description.setEnabled(true);
            editable = true;
        }
        else {
            if (!firstName.getText().toString().equals(currentFirstName) || !lastName.getText().toString().equals(currentLastName)
                    || !phoneNumber.getText().toString().equals(currentPhone) || !email.getText().toString().equals(currentEmail)
                    || !description.getText().toString().equals(currentDescription))
            {
                currentEmployee.setEmployeeFirstName(firstName.getText().toString());
                currentEmployee.setEmployeeLastName(lastName.getText().toString());
                currentEmployee.setEmployeePhone(phoneNumber.getText().toString());
                currentEmployee.setEmployeeEmail(email.getText().toString());
                currentEmployee.setEmployeeDescription(description.getText().toString());

                mightyManagerViewModel.update(currentEmployee);
                //closeActivity(RESULT_OK);
                finish();
            }
            else {
                cancelScreen();
            }
        }
    }

    private void cancelScreen() {
        //closeActivity(RESULT_CANCELED);
        finish();
    }

    private void closeActivity(int resultCode) {
        Intent intent = new Intent();
        if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED, intent);
        }
        else  {
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}
