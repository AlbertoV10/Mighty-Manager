package com.example.albertovenegas.mightymanager.UserInterface;

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
    private CheckBox adminCheck;
    private boolean fromCreateOrg = false;
    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_employee);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        eFirstName = findViewById(R.id.create_employee_first_name);
        eLastName = findViewById(R.id.create_employee_last_name);
        ePassword = findViewById(R.id.create_employee_password);
        ePhone = findViewById(R.id.create_employee_phone);
        eEmail = findViewById(R.id.create_employee_email);
        adminCheck = findViewById(R.id.create_employee_admin_check);

        if (getIntent().hasExtra("fromCreateOrg")) {
            fromCreateOrg = true;
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
            boolean admin = adminCheck.isChecked();

            String username = generateUsername(fName, lName);

            mmvm.insert(new Employee(fName, lName, password, admin, username, phone, email));
            Toast.makeText(this, "New Username: " + username, Toast.LENGTH_SHORT).show();
            if (fromCreateOrg) {
                Intent signinScreenIntent = new Intent(CreateEmployee.this, SignInPage.class);
                startActivity(signinScreenIntent);
            }
            finish();
        }
    }

    private void cancelScreen() {
        finish();
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
