package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class CreateEmployee extends AppCompatActivity {
    private EditText eFirstName;
    private EditText eLastName;
    private EditText ePassword;
    private EditText ePhone;
    private EditText eEmail;
    private EditText eDescription;
    private CheckBox adminCheck;
    private boolean fromCreateOrg = false;
    private boolean firstTimeSignUp = true;
    private MightyManagerViewModel mmvm;
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        eFirstName = findViewById(R.id.create_employee_first_name);
        eLastName = findViewById(R.id.create_employee_last_name);
        ePassword = findViewById(R.id.create_employee_password);
        ePhone = findViewById(R.id.create_employee_phone);
        eEmail = findViewById(R.id.create_employee_email);
        eDescription = findViewById(R.id.create_employee_description);
        adminCheck = findViewById(R.id.create_employee_admin_check);

        if (getIntent().hasExtra("fromCreateOrg")) {
            fromCreateOrg = true;
        }
        if (getIntent().hasExtra("user")) {
            currentUserId = getIntent().getExtras().getInt("user");
        }

        if (fromCreateOrg) {
            adminCheck.setChecked(true);
            adminCheck.setEnabled(false);
        }

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveEmployee();
                return true;
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void saveEmployee() {
        if (eFirstName.getText().toString().isEmpty() || eLastName.getText().toString().isEmpty() || ePassword.getText().toString().isEmpty()
            || ePhone.getText().toString().isEmpty() || eEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
        else {
            String fName = eFirstName.getText().toString().trim();
            String lName = eLastName.getText().toString().trim();
            String password = ePassword.getText().toString();
            String phone = ePhone.getText().toString().trim();
            String email = eEmail.getText().toString().trim();
            String description = eDescription.getText().toString().trim();
            boolean admin = adminCheck.isChecked();

            String username = generateUsername(fName, lName);

            if (fromCreateOrg) {
                firstTimeSignUp = false;
            }

            mmvm.insert(new Employee(fName, lName, password, admin, username, phone, email, description, firstTimeSignUp));
            //Toast.makeText(this, "New Username: " + username, Toast.LENGTH_SHORT).show();
            if (fromCreateOrg) {
                showFirstTimeManagerDialog(username, password);
            }
            else {
                showNewUsernameDialog(fName, lName, username, password);
            }
            //finish();
        }
    }

    private void cancelScreen() {
        if (fromCreateOrg){
            mmvm.deleteAllOrganizations();
        }
        finish();
    }

    public void showNewUsernameDialog(String fName, String lName, String username, String password) {
        NewEmployeeUsernameDialog dialog = new NewEmployeeUsernameDialog();
        Bundle bundle = new Bundle();
        bundle.putString("eFName", fName);
        bundle.putString("eLName", lName);
        bundle.putString("eUsername", username);
        bundle.putString("ePassword", password);
        bundle.putInt("managerID", currentUserId);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NewEmployeeUsernameDialog");
    }

    public void showFirstTimeManagerDialog(String username, String password) {
        NewMainManagerUsernameDialog dialog = new NewMainManagerUsernameDialog();
        Bundle bundle = new Bundle();
        bundle.putString("mUsername", username);
        bundle.putString("mPassword", password);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "NewMainManagerUsernameDialog");
    }

    private boolean nameExists(String username) {
        if(mmvm.findEmployeeByUsername(username) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    private String generateUsername(String firstName, String lastName) {
        String candidateName = "";
        candidateName = candidateName.concat(firstName.toLowerCase().charAt(0) + lastName.toLowerCase());
        int i = 1;
        while(nameExists(candidateName) && i < firstName.length())
        {
            candidateName = "";
            for (int j = 0; j <= i; j++)
            {
                candidateName = candidateName.concat(firstName.charAt(j) + "");
            }
            candidateName = candidateName.concat(lastName);
        }
        return candidateName;
    }
}
