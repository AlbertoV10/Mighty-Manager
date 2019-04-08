package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
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
    private EditText password;
    private EditText phoneNumber;
    private EditText email;
    private TextView employeeType;

    //Strings to save currentDate
    private String currentUserName;
    private String currentFirstName;
    private String currentLastName;
    private String currentPassword;
    private String currentPhone;
    private String currentEmail;

    private boolean editable = false;
    private Menu menu;

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
        password = findViewById(R.id.my_profile_password);
        phoneNumber = findViewById(R.id.my_profile_phone);
        email = findViewById(R.id.my_profile_email);
        employeeType = findViewById(R.id.my_profile_employee_type);

        username.setText(currentEmployee.getEmployeeUsername());
        firstName.setText(currentEmployee.getEmployeeFirstName());
        firstName.setEnabled(false);
        lastName.setText(currentEmployee.getEmployeeLastName());
        lastName.setEnabled(false);
        password.setText(currentEmployee.getEmployeePassword());
        password.setEnabled(false);
        phoneNumber.setText(currentEmployee.getEmployeePhone());
        phoneNumber.setEnabled(false);
        email.setText(currentEmployee.getEmployeeEmail());
        email.setEnabled(false);
        if (currentEmployee.isAdmin()) {
            employeeType.setText("Status: Admin");
        }
        else {
            employeeType.setText("Status: Employee");
        }

        //save current data
        currentFirstName = currentEmployee.getEmployeeFirstName();
        currentLastName = currentEmployee.getEmployeeLastName();
        currentPassword = currentEmployee.getEmployeePassword();
        currentPhone = currentEmployee.getEmployeePhone();
        currentEmail = currentEmployee.getEmployeeEmail();
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
            password.setEnabled(true);
            phoneNumber.setEnabled(true);
            email.setEnabled(true);
            editable = true;
        }
        else {
            if (!firstName.getText().toString().equals(currentFirstName) || !lastName.getText().toString().equals(currentLastName)
                    || !password.getText().toString().equals(currentPassword) || !phoneNumber.getText().toString().equals(currentPhone)
                    || !email.getText().toString().equals(currentEmail))
            {
                currentEmployee.setEmployeeFirstName(firstName.getText().toString());
                currentEmployee.setEmployeeLastName(lastName.getText().toString());
                currentEmployee.setEmployeePassword(password.getText().toString());
                currentEmployee.setEmployeePhone(phoneNumber.getText().toString());
                currentEmployee.setEmployeeEmail(email.getText().toString());

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
