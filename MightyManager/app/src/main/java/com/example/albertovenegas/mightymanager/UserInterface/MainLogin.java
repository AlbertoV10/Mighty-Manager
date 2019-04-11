package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class MainLogin extends AppCompatActivity {

    private Button managerButton;
    private Button employeeButton;
    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //hide action bar
        getSupportActionBar().hide();

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        //mmvm.deleteAllEmployees();
        //mmvm.deleteAllCustomers();
        //mmvm.deleteAllTasks();
        //mmvm.insert(new Customer("Test Customer", "(123)555-3553"));
        //mmvm.insert(new Employee("manager", "admin", "manageradmin", true, "madmin", "(800)555-1234", "manager@mm.com"));
        //mmvm.insert(new Employee("employee", "admin", "employeeadmin", false, "eadmin", "(800)555-5678", "employee@mm.com"));
        List<Employee> employees = mmvm.getEmployeesList();
        Toast.makeText(this, "number of employees: " + employees.size(), Toast.LENGTH_SHORT).show();

        managerButton = (Button) findViewById(R.id.login_manager_button);
        employeeButton = (Button) findViewById(R.id.login_employee_button);

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(MainLogin.this, SignInPage.class);
                signin.putExtra("managerUserType", true); //manager boolean is true
                signin.putExtra("employeeUserType", false); //employee boolean is false
                startActivity(signin);
            }
        });

        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signin = new Intent(MainLogin.this, SignInPage.class);
                signin.putExtra("managerUserType", false); //manager boolean is true
                signin.putExtra("employeeUserType", true); //employee boolean is false
                startActivity(signin);
            }
        });

    }



}
