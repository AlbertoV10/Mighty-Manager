package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.albertovenegas.mightymanager.Adapter.MainAppListAdapter;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDetails extends AppCompatActivity {
    private EditText eFirstName;
    private EditText eLastName;
    private EditText ePassword;
    private EditText ePhone;
    private EditText eEmail;
    private CheckBox adminCheck;
    private MightyManagerViewModel mmvm;
    private Employee currentEmployee;
    private MainAppListAdapter eAdapter;
    private List<Task> taskList = new ArrayList<>();
    private Menu menu;
    private Boolean editable = false;
    //values for current data
    private String currentFirstName;
    private String currentLastName;
    private String currentPassword;
    private String currentPhone;
    private String currentEmail;
    private boolean currentAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        final int employeeId = getIntent().getExtras().getInt(EmployeeList.EMPLOYEE_DESCRIPTION_EXTRA_KEY);
        currentEmployee = mmvm.findEmployeeById(employeeId);

        //intialize views
        eFirstName = findViewById(R.id.employee_details_first_name);
        eLastName = findViewById(R.id.employee_details_last_name);
        ePassword = findViewById(R.id.employee_details_password);
        ePhone = findViewById(R.id.employee_details_phone);
        eEmail = findViewById(R.id.employee_details_email);
        adminCheck = findViewById(R.id.employee_details_admin_check);

        //populate each view and disable editing by default
        eFirstName.setText(currentEmployee.getEmployeeFirstName());
        eFirstName.setEnabled(false);
        eLastName.setText(currentEmployee.getEmployeeLastName());
        eLastName.setEnabled(false);
        ePassword.setText(currentEmployee.getEmployeePassword());
        ePassword.setEnabled(false);
        ePhone.setText(currentEmployee.getEmployeePhone());
        ePhone.setEnabled(false);
        eEmail.setText(currentEmployee.getEmployeeEmail());
        eEmail.setEnabled(false);
        if (currentEmployee.isAdmin()) {
            adminCheck.setChecked(true);
        }
        else {
            adminCheck.setChecked(false);
        }
        adminCheck.setEnabled(false);

        //save current data
        currentFirstName = eFirstName.getText().toString().trim();
        currentLastName = eLastName.getText().toString().trim();
        currentPassword = ePassword.getText().toString();
        currentPhone = ePhone.getText().toString().trim();
        currentEmail = eEmail.getText().toString().trim();
        currentAdmin = adminCheck.isChecked();


        RecyclerView eRecyclerView = findViewById(R.id.employee_details_recyclerview);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eAdapter = new MainAppListAdapter(this);
        eAdapter.setEmployees(mmvm.getEmployeesList());
        eRecyclerView.setAdapter(eAdapter);

        mmvm.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskList = mmvm.findTaskByEmployee(currentEmployee.getEmployeeID());
                eAdapter.setTasks(taskList);
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
            eFirstName.setEnabled(true);
            eLastName.setEnabled(true);
            ePassword.setEnabled(true);
            ePhone.setEnabled(true);
            eEmail.setEnabled(true);
            adminCheck.setEnabled(true);
        }
        else {
            if (!eFirstName.getText().toString().equals(currentFirstName) || !eLastName.getText().toString().equals(currentLastName)
                || !ePassword.getText().toString().equals(currentPassword) || !ePhone.getText().toString().equals(currentPhone)
                || !eEmail.getText().toString().equals(currentEmail) || adminCheck.isChecked() != currentAdmin)
            {
                currentEmployee.setEmployeeFirstName(eFirstName.getText().toString());
                currentEmployee.setEmployeeLastName(eLastName.getText().toString());
                currentEmployee.setEmployeePassword(ePassword.getText().toString());
                currentEmployee.setEmployeePhone(ePhone.getText().toString());
                currentEmployee.setEmployeeEmail(eEmail.getText().toString());
                currentEmployee.setAdmin(adminCheck.isChecked());

                mmvm.update(currentEmployee);
                closeActivity(RESULT_OK);
            }
        }
    }

    private void cancelScreen() {
        closeActivity(RESULT_CANCELED);
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
