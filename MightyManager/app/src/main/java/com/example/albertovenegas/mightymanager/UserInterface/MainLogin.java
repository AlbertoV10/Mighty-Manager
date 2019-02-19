package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class MainLogin extends AppCompatActivity {

    private Button managerButton;
    private Button employeeButton;
    private MightyManagerViewModel mmvm;
    private List<Employee> employeesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        mmvm.deleteAllEmployees();
        mmvm.insert(new Employee("jon", "123", true));
        Employee te = mmvm.findEmployeeByName("jon");
        if(te == null) {
            Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, te.getEmployeeName(), Toast.LENGTH_LONG).show();

        }

        managerButton = (Button) findViewById(R.id.login_manager_button);
        employeeButton = (Button) findViewById(R.id.login_employee_button);

        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainLogin.this, "Testing manager button", Toast.LENGTH_SHORT).show();
                Intent signin = new Intent(MainLogin.this, SignInPage.class);
                signin.putExtra("managerUserType", true); //manager boolean is true
                signin.putExtra("employeeUserType", false); //employee boolean is false
                startActivity(signin);
            }
        });

        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainLogin.this, "Testing employee button", Toast.LENGTH_SHORT).show();
                Intent signin = new Intent(MainLogin.this, SignInPage.class);
                signin.putExtra("managerUserType", false); //manager boolean is true
                signin.putExtra("employeeUserType", true); //employee boolean is false
                startActivity(signin);
            }
        });

    }



}
