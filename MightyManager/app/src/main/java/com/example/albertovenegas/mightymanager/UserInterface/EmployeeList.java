package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class EmployeeList extends AppCompatActivity {
    private MightyManagerViewModel mmvm;
    private EmployeeListAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.employee_screen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employeeAdapter = new EmployeeListAdapter(this);
        recyclerView.setAdapter(employeeAdapter);

        mmvm.getAllEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                employeeAdapter.setEmployeeList(employees);
            }
        });

    }
}
