package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddTaskActivity extends AppCompatActivity {
    public static final String EXTRA_TITLE = "add.task.title";
    public static final String EXTRA_ADDRESS = "add.task.address";
    public static final String EXTRA_EMPLOYEE_ID = "add.task.employee.id";
    public static final String EXTRA_CUSTOMER_ID = "add.task.customer.id";
    public static final String EXTRA_NOTES = "add.task.notes";
    public static final String EXTRA_DATE_CREATED = "add.task.date.created";
    public static final String EXTRA_DATE_DUE = "add.task.date.due";
    public static final String EXTRA_CUSTOMER_NAME = "add.task.customer.name";

    private EditText newTaskTitle;
    private EditText newTaskAddress;
    private Spinner employeeSpinner;
    private AutoCompleteTextView customerName;
    private EditText customerPhone;
    private EditText customerEmail;
    private EditText taskDetails;
    private TextView taskDate;
    private ImageButton dateButton;
    private String dueDate;
    private String dateCreated;

    private DatePickerDialog.OnDateSetListener dateListener;
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
        taskDate = findViewById(R.id.add_task_due_date);
        dateButton = findViewById(R.id.add_task_date_button);
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
        //set adapter for customer name autocomplete
        ArrayAdapter<String> customerAutoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, customerNames);
        customerName.setAdapter(customerAutoCompleteAdapter);
        //watch as text changes and if user selects a suggested customer, autofill their info
        customerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Customer findCustomer = mmvm.findCustomerByName(charSequence.toString());
                if (findCustomer != null) {
                    customerPhone.setText(findCustomer.getCustomerPhone());
                    customerEmail.setText(findCustomer.getCustomerEmail());
                }
                else {
                    customerPhone.setText("");
                    customerEmail.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //get day on which note is created
        getCurrentDate();

        //set calendar dialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String sMonth;
                String sDay;
                if (month < 10) {
                    sMonth = "0" + month;
                }
                else {
                    sMonth = "" + month;
                }
                if (day < 10) {
                    sDay = "0" + day;
                }
                else {
                    sDay = "" + day;
                }
                dueDate = sMonth + "/" + sDay + "/" + year;
                taskDate.setText("Due: " + dueDate);
                //Toast.makeText(AddTaskActivity.this, "Setting date as: " + taskDate.getText().toString().substring(5), Toast.LENGTH_LONG).show();
            }
        };

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
        String taskDueDate = taskDate.getText().toString().substring(5);
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
            int cId = customerExists(cName, cPhone, cEmail);
            Task newTask = new Task(title, address, employeeId, 1, cId, notes, dateCreated, taskDueDate);
            //mmvm.insert(new Task(title, address, employeeId, 1, cId, notes, dateCreated, taskDueDate));
            closeAddActivity(RESULT_OK, newTask, cName);
        }
    }

    private void cancelScreen() {
       closeAddActivity(RESULT_CANCELED, null, "");
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
                saveTask();
                return true;
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void closeAddActivity(int resultCode, Task task, String customerName) {
        Intent intent = new Intent();
        if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED, intent);
        }
        else  {
            //mmvm.insert(new Task(title, address, employeeId, 1, cId, notes, dateCreated, taskDueDate));
            String title = task.getTaskTitle();
            String address = task.getTaskAddress();
            int eId = task.getEmployeeID();
            int cId = task.getCustomerID();
            String notes = task.getTaskDescription();
            String dateMade = task.getTaskDateCreated();
            String dateDue = task.getTaskDateDue();
            String cName = customerName;
            intent.putExtra(EXTRA_TITLE, title);
            intent.putExtra(EXTRA_ADDRESS, address);
            intent.putExtra(EXTRA_EMPLOYEE_ID, eId);
            intent.putExtra(EXTRA_CUSTOMER_ID, cId);
            intent.putExtra(EXTRA_NOTES, notes);
            intent.putExtra(EXTRA_DATE_CREATED, dateMade);
            intent.putExtra(EXTRA_DATE_DUE, dateDue);
            intent.putExtra(EXTRA_CUSTOMER_NAME, cName);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    private int findEmployeeId(String eUsername) {
        Employee employee = mmvm.findEmployeeByUsername(eUsername);
        Toast.makeText(this, "For employee" + employee.getEmployeeUsername() + "found id" + employee.getEmployeeID(), Toast.LENGTH_SHORT).show();
        return employee.getEmployeeID();
    }

    private void getCurrentDate() {
        Calendar currentDate = Calendar.getInstance();
        int month = currentDate.get(Calendar.MONTH) + 1;
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int year = currentDate.get(Calendar.YEAR);
        String sDay;
        String sMonth;
        if (month < 10) {
            sMonth = "0" + month;
        }
        else {
            sMonth = "" + month;
        }
        if (day < 10) {
            sDay = "0" + day;
        }
        else {
            sDay = "" + day;
        }
        dateCreated = sMonth + "/" + sDay + "/" + year;
        //Toast.makeText(AddTaskActivity.this, "current date would be: " + dateCreated, Toast.LENGTH_LONG).show();
    }

    private int customerExists(String name, String phone, String email) {
        int customerId = -888;
        Customer customer = mmvm.findCustomerByName(name);
        //found a match by name
        if (customer != null) {
            customerId = customer.getCustomerID();
            if ((!phone.equals(customer.getCustomerPhone()) && !phone.equals("")) || (!email.equals(customer.getCustomerEmail()) && !email.equals(""))) {
                //update existing customer. Ask to update later
                customer.setCustomerPhone(phone);
                customer.setCustomerEmail(email);
                mmvm.update(customer);
            }
        }
        else {
            // create the new customer
            if (!name.equals("")) {
                Customer newCustomer = new Customer(name, phone, email);
                mmvm.insert(newCustomer);
                Customer addCustomer = mmvm.findCustomerByName(name);
                if (addCustomer != null) {
                    customerId = addCustomer.getCustomerID();
                }
                else {
                    //Toast.makeText(this, "New customer was null", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return customerId;
    }
}
