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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
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
    private EditText customerName;
    private EditText customerPhone;
    private EditText customerEmail;
    private EditText taskDetails;
    //private ImageButton saveButton;
    //private ImageButton cancelButton;
    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);


        newTaskTitle = findViewById(R.id.add_task_title);
        newTaskAddress = findViewById(R.id.add_task_address);
        employeeSpinner = findViewById(R.id.add_task_employee_spinner);
        customerName = findViewById(R.id.add_task_customer_name);
        customerPhone = findViewById(R.id.add_task_customer_phone);
        customerEmail = findViewById(R.id.add_task_customer_email);
        taskDetails = findViewById(R.id.add_task_details);
        //saveButton = findViewById(R.id.add_task_save_button);
        //cancelButton = findViewById(R.id.add_task_cancel_button);

        List<Employee> employees = new ArrayList<>();
        employees = mmvm.getEmployeesList();
        ArrayList<String> employeeNames = new ArrayList<>();
        for(int i = 0; i < employees.size(); i++) {
            employeeNames.add(employees.get(i).getEmployeeUsername());
        }
        employeeNames.add(0,"Leave Unassigned");
        employeeNames.add(0,"-Employees-");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeSpinner.setAdapter(spinnerAdapter);

        List<Customer> customers = new ArrayList<>();
        customers = mmvm.getCustomerList();
        ArrayList<String> customerNames = new ArrayList<>();
        for (int i = 0; i < customers.size(); i++)
        {
            customerNames.add(customers.get(i).getCustomerName());
        }

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveTask();
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelScreen();
//            }
//        });
    }

    private void saveTask() {
        int employeeId;
        String title = newTaskTitle.getText().toString().trim();
        String address = newTaskAddress.getText().toString().trim();
        String employee = employeeSpinner.getSelectedItem().toString().trim();
        String cName = customerName.getText().toString().trim();
        String cPhone = customerPhone.getText().toString().trim();
        String cEmail = customerEmail.getText().toString().trim();
        String notes = taskDetails.getText().toString();

        if (title.isEmpty() || address.isEmpty() || employee.equals("-Employees-")) {
            Toast.makeText(this, "Please fill in title, address, and select employee", Toast.LENGTH_LONG).show();
        } else {
//            Intent intent = new Intent();
////            intent.putExtra(EXTRA_TITLE, title);
////            intent.putExtra(EXTRA_ADDRESS, address);
////            intent.putExtra(EXTRA_EMPLOYEE_NAME, employee);
////            setResult(RESULT_OK, intent);
            if (employee.equals("Leave Unassigned"))
            {
                employeeId = -999;
            }
            else {
                employeeId = findEmployeeId(employee);
            }
            if (cName.isEmpty()) {
                cName = "";
            }
            if (cPhone.isEmpty()) {
                cPhone = "";
            }
            if (cEmail.isEmpty()) {
                cEmail = "";
            }
            if (notes.isEmpty()) {
                notes = "";
            }
            mmvm.insert(new Task(title, address, employeeId, 1, -888, notes));
            closeAddActivity(RESULT_OK);
        }
    }

    private void cancelScreen() {
       closeAddActivity(RESULT_CANCELED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task:
                saveTask();
                return true;
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void closeAddActivity(int resultCode) {
        Intent intent = new Intent();
        if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED, intent);
        }
        else  {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private int findEmployeeId(String eUsername) {
        Employee employee = mmvm.findEmployeeByUsername(eUsername);
        return employee.getEmployeeID();
    }
}
