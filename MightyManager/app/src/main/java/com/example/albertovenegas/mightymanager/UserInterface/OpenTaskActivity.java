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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class OpenTaskActivity extends AppCompatActivity {
    private MightyManagerViewModel mmvm;
    private EditText otTitle;
    private EditText otAddress;
    private Spinner otEmployeeSpinner;
    private Spinner otStatusSpinner;
    private EditText otCustomerName;
    private EditText otCustomerPhone;
    private EditText otCustomerEmail;
    private EditText taskNotes;
    //private ImageButton editSaveBtn;
    //private ImageButton cancelBtn;
    private String[] statusSelection = {"Open", "In Progress", "Closed"};
    private boolean editable = false;
    private Task task;
    //variables for current data
    private String currentTitle;
    private String currentAddress;
    private String currentEmployee;
    private String currentStatus;
    private String currentCustomer;
    private String currentCustomerPhone;
    private String currentCustomerEmail;
    private String currentNotes;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_task);
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);

        final int taskID = getIntent().getExtras().getInt(MainAppScreen.OPEN_TASK_EXTRA_KEY);
        task = mmvm.findTaskById(taskID);

        //initialize views
        otTitle = findViewById(R.id.open_task_title);
        otAddress = findViewById(R.id.open_task_address);
        otEmployeeSpinner = findViewById(R.id.open_task_employee_spinner);
        otStatusSpinner = findViewById(R.id.open_task_status_spinner);
        otCustomerName = findViewById(R.id.open_task_customer_name);
        otCustomerPhone = findViewById(R.id.open_task_customer_phone);
        otCustomerEmail = findViewById(R.id.open_task_customer_email);
        taskNotes = findViewById(R.id.open_task_task_notes);
        //editSaveBtn = findViewById(R.id.open_task_save_btn);
        //cancelBtn = findViewById(R.id.open_task_cancel_button);

        //set data and images for buttons
        otTitle.setText(task.getTaskTitle());
        otTitle.setEnabled(false);
        otAddress.setText(task.getTaskAddress());
        otAddress.setEnabled(false);
        otCustomerName.setText("N/A will be fixed");
        otCustomerName.setEnabled(false);
        otCustomerPhone.setText("N/A will be fixed");
        otCustomerPhone.setEnabled(false);
        otCustomerEmail.setText("N/A will be fixed");
        otCustomerEmail.setEnabled(false);
        taskNotes.setText(task.getTaskDescription());
        taskNotes.setEnabled(false);
        //editSaveBtn.setImageResource(R.drawable.ic_edit);
        //editSaveBtn.setTag(R.drawable.ic_edit);

        //set employee spinner
        List<Employee> employees = new ArrayList<>();
        employees = mmvm.getEmployeesList();
        ArrayList<String> employeeNames = new ArrayList<>();
        for(int i = 0; i < employees.size(); i++) {
            employeeNames.add(employees.get(i).getEmployeeUsername());
        }
        employeeNames.add(0,"Leave Unassigned");
        int employeeId = task.getEmployeeID();
        String currentAssignedEmployee = "Not Found";
        for (int j = 0; j < employees.size(); j++)
        {
            if (employees.get(j).getEmployeeID() == employeeId)
            {
                currentAssignedEmployee = employees.get(j).getEmployeeUsername();
            }
        }
        int index = employeeNames.indexOf(currentAssignedEmployee);
        if (!(index == -1)) {
            currentAssignedEmployee = employeeNames.remove(index);
            employeeNames.add(0,currentAssignedEmployee);
        }
        ArrayAdapter<String> employeeSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeNames);
        employeeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otEmployeeSpinner.setAdapter(employeeSpinnerAdapter);
        otEmployeeSpinner.setEnabled(false);

        //set status spinner
        ArrayAdapter<String> statusSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusSelection);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otStatusSpinner.setAdapter(statusSpinnerAdapter);
        otStatusSpinner.setEnabled(false);

        //save current task data and check if user edited before exiting
        currentTitle = otTitle.getText().toString().trim();
        currentAddress = otAddress.getText().toString().trim();
        currentEmployee = otEmployeeSpinner.getSelectedItem().toString().trim();
        currentStatus = otStatusSpinner.getSelectedItem().toString().trim();
        currentCustomer = otCustomerName.getText().toString().trim();
        currentCustomerPhone = otCustomerPhone.getText().toString().trim();
        currentCustomerEmail = otCustomerEmail.getText().toString().trim();
        currentNotes = taskNotes.getText().toString();

//        editSaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (editable) {
//                    //check if data changed
//                    if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
//                    || !otEmployeeSpinner.getSelectedItem().toString().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().equals(currentStatus))
//                    {
//                        //save data and close activity
//                        String status = otStatusSpinner.getSelectedItem().toString();
//                        if (status.equals("Open")) {
//                            task.setTaskStatus(1);
//                        }
//                        else if (status.equals("In Progress")) {
//                            task.setTaskStatus(2);
//                        }
//                        else if (status.equals("Closed")) {
//                            task.setTaskStatus(3);
//                        }
//                        task.setTaskTitle(otTitle.getText().toString().trim());
//                        task.setTaskAddress(otAddress.getText().toString().trim());
//                        String employeeName = otEmployeeSpinner.getSelectedItem().toString();
//                        Employee employee = mmvm.findEmployeeByName(employeeName);
//                        task.setEmployeeID(employee.getEmployeeID());
//                        mmvm.update(task);
//
//                        closeActivity(RESULT_OK);
//                    }
//                    else {
//                        //data did not change so return from activity
//
//                        closeActivity(RESULT_CANCELED);
//                    }
//                } else {
//                    editSaveBtn.setImageResource(R.drawable.ic_save_black);
//                    editSaveBtn.setTag(R.drawable.ic_save_black);
//                    //enable editing in fields
//                    otTitle.setEnabled(true);
//                    otAddress.setEnabled(true);
//                    otEmployeeSpinner.setEnabled(true);
//                    otStatusSpinner.setEnabled(true);
//                    editable = true;
//                }
//            }
//        });

//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
//                        || !otEmployeeSpinner.getSelectedItem().toString().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().equals(currentStatus))
//                {
//                    Toast.makeText(OpenTaskActivity.this, "Data was changed but canceling", Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    Toast.makeText(OpenTaskActivity.this, "Data unchanged canceling", Toast.LENGTH_LONG).show();
//                }
//                closeActivity(RESULT_CANCELED);
//            }
//        });
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
                editSaveTask();
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

    private void editSaveTask() {
       if (!editable) {
           //change the edit icon to save
           menu.getItem(0).setIcon(R.drawable.ic_save_white);
           //enable edit text fields
           otTitle.setEnabled(true);
           otAddress.setEnabled(true);
           otEmployeeSpinner.setEnabled(true);
           otStatusSpinner.setEnabled(true);
           otCustomerName.setEnabled(true);
           otCustomerPhone.setEnabled(true);
           otCustomerEmail.setEnabled(true);
           taskNotes.setEnabled(true);
           editable = true;
       }
       else {
           if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
                   || !otEmployeeSpinner.getSelectedItem().toString().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().equals(currentStatus)
                   || !otCustomerName.getText().toString().trim().equals(currentCustomer) || !otCustomerPhone.getText().toString().trim().equals(currentCustomerPhone)
                   || !otCustomerEmail.getText().toString().trim().equals(currentCustomerEmail) || !taskNotes.getText().toString().equals(currentNotes))
           {
               //save data and close activity
               String status = otStatusSpinner.getSelectedItem().toString();
               if (status.equals("Open")) {
                   task.setTaskStatus(1);
               }
               else if (status.equals("In Progress")) {
                   task.setTaskStatus(2);
               }
               else if (status.equals("Closed")) {
                   task.setTaskStatus(3);
               }
               task.setTaskTitle(otTitle.getText().toString().trim());
               task.setTaskAddress(otAddress.getText().toString().trim());
               String employeeName = otEmployeeSpinner.getSelectedItem().toString();
               Employee employee = mmvm.findEmployeeByUsername(employeeName);
               task.setEmployeeID(employee.getEmployeeID());
               task.setCustomerID(-888);
               task.setTaskDescription(taskNotes.getText().toString());
               mmvm.update(task);

               closeActivity(RESULT_OK);
           }
           else {
               //data did not change so return from activity

               closeActivity(RESULT_CANCELED);
           }
       }
    }

    private void cancelScreen() {
        if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
                || !otEmployeeSpinner.getSelectedItem().toString().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().equals(currentStatus))
        {
            Toast.makeText(OpenTaskActivity.this, "Data was changed but canceling", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(OpenTaskActivity.this, "Data unchanged canceling", Toast.LENGTH_LONG).show();
        }
        closeActivity(RESULT_CANCELED);
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
