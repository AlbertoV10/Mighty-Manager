package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
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
    private ImageButton editSaveBtn;
    private ImageButton cancelBtn;
    private String[] statusSelection = {"Open", "In Progress", "Closed"};
    private boolean editable = false;
    private Task task;
    //variables for current data
    private String currentTitle;
    private String currentAddress;
    private String currentEmployee;
    private String currentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_task);

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        int taskID = getIntent().getExtras().getInt(MainAppScreen.OPEN_TASK_ID);
        task = mmvm.findTaskById(taskID);

        //initialize views
        otTitle = findViewById(R.id.open_task_title);
        otAddress = findViewById(R.id.open_task_address);
        otEmployeeSpinner = findViewById(R.id.open_task_employee_spinner);
        otStatusSpinner = findViewById(R.id.open_task_status_spinner);
        editSaveBtn = findViewById(R.id.open_task_save_btn);
        cancelBtn = findViewById(R.id.open_task_cancel_button);

        //set data and images for buttons
        otTitle.setText(task.getTaskTitle());
        otTitle.setEnabled(false);
        otAddress.setText(task.getTaskAddress());
        otAddress.setEnabled(false);
        editSaveBtn.setImageResource(R.drawable.ic_edit);
        editSaveBtn.setTag(R.drawable.ic_edit);

        //set employee spinner
        List<Employee> employees = new ArrayList<>();
        employees = mmvm.getEmployeesList();
        ArrayList<String> employeeNames = new ArrayList<>();
        for(int i = 0; i < employees.size(); i++) {
            employeeNames.add(employees.get(i).getEmployeeName());
        }
        employeeNames.add(0,"Leave Unassigned");
        ArrayAdapter<String> employeeSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, employeeNames);
        employeeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otEmployeeSpinner.setAdapter(employeeSpinnerAdapter);
        otEmployeeSpinner.setEnabled(false);

        //set status spinner
        ArrayAdapter<String> statusSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusSelection);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otStatusSpinner.setAdapter(statusSpinnerAdapter);
        otStatusSpinner.setEnabled(false);

        editSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editable) {
                    //check if data changed
                    if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
                    || !otEmployeeSpinner.getSelectedItem().toString().trim().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().trim().equals(currentStatus))
                    {
                        //save data and close activity
                    }
                    else {
                        //data did not change so return from activity
                    }
                } else {
                    editSaveBtn.setImageResource(R.drawable.ic_save);
                    editSaveBtn.setTag(R.drawable.ic_save);
                    //save current task data and check if user edited before exiting
                    currentTitle = otTitle.getText().toString().trim();
                    currentAddress = otAddress.getText().toString().trim();
                    currentEmployee = otEmployeeSpinner.getSelectedItem().toString().trim();
                    currentStatus = otStatusSpinner.getSelectedItem().toString().trim();
                    otTitle.setEnabled(true);
                    otAddress.setEnabled(true);
                    otEmployeeSpinner.setEnabled(true);
                    otStatusSpinner.setEnabled(true);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //return and dont change data
            }
        });
    }
}
