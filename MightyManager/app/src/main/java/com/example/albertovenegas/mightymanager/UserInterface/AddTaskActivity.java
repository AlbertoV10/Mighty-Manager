package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "add.task.title";
    public static final String EXTRA_ADDRESS = "add.task.address";
    public static final String EXTRA_EMPLOYEE_NAME = "add.task.employee.name";

    private EditText newTaskTitle;
    private EditText newTaskAddress;
    private Spinner employeeSpinner;
    private ImageButton saveButton;
    private ImageButton cancelButton;
    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);


        newTaskTitle = findViewById(R.id.add_task_title);
        newTaskAddress = findViewById(R.id.add_task_address);
        employeeSpinner = findViewById(R.id.add_task_employee_spinner);
        saveButton = findViewById(R.id.add_task_save_button);
        cancelButton = findViewById(R.id.add_task_cancel_button);

        List<Employee> employees = new ArrayList<>();
        employees = mmvm.getEmployeesList();
        ArrayList<String> employeeNames = new ArrayList<>();
        for(int i = 0; i < employees.size(); i++) {
            employeeNames.add(employees.get(i).getEmployeeName());
        }
        employeeNames.add(0,"Leave Unassigned");
        employeeNames.add(0,"-Employees-");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(spinnerAdapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        String title = newTaskTitle.getText().toString();
        String address = newTaskAddress.getText().toString();
        String employee = employeeSpinner.getSelectedItem().toString();
        if (title.isEmpty() || address.isEmpty() || employee.equals("-Employees-")) {
            Toast.makeText(this, "Please fill in all fields and select employee", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_ADDRESS, address);
            intent.putExtra(EXTRA_EMPLOYEE_NAME, employee);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
