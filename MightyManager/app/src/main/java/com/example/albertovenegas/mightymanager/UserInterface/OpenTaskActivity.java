package com.example.albertovenegas.mightymanager.UserInterface;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OpenTaskActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    public static final int REQUEST_CALL = 1;
    public static final int REQUEST_MAP = 2;

    public static final String NEW_CUSTOMER_NAME_DURING_EDIT = "open.task.new.customer";
    public static final String EDIT_TASK_ID = "open.task.id";

    private MightyManagerViewModel mmvm;
    private EditText otTitle;
    private EditText otAddress;
    private Spinner otEmployeeSpinner;
    private Spinner otStatusSpinner;
    private EditText otCustomerName;
    private EditText otCustomerPhone;
    private EditText otCustomerEmail;
    private EditText taskNotes;
    private TextView appDate;
    private ImageButton dateButton;
    private TextView appTime;
    private ImageButton timeButton;

    String appDateString = "";
    String appTimeString = "";
    //private ImageButton editSaveBtn;
    //private ImageButton cancelBtn;
    private ArrayList<String> statusSelection = new ArrayList<>();
    private boolean editable = false;
    private Task task;
    //variables for current data
    private String currentTitle;
    private String currentAddress;
    private String currentEmployee;
    private String currentStatus;
    private String currentCustomerName;
    private String currentCustomerPhone;
    private String currentCustomerEmail;
    private String currentNotes;
    private String currentAppDate;
    private String currentAppTime;

    private DatePickerDialog.OnDateSetListener dateListener;
    private Menu menu;

    //for testing date difference
    private String currentDateString;

    private int eId;
    private Employee requestingEmployee;
    ColorStateList originalTextColor;

    boolean newCustomer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_task);
        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        //prevent keyboard from opening at start
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final int taskID = getIntent().getExtras().getInt(MainAppScreen.OPEN_TASK_EXTRA_KEY);
        if (getIntent().hasExtra("user")) {
            eId = getIntent().getExtras().getInt("user");
            requestingEmployee = mmvm.findEmployeeById(eId);
        }
        task = mmvm.findTaskById(taskID);

        //for testing date diff
        //Toast.makeText(this, "Date created: " + task.getTaskDateCreated(), Toast.LENGTH_SHORT).show();
        getCurrentDate();

        //initialize views
        otTitle = findViewById(R.id.open_task_title);
        otAddress = findViewById(R.id.open_task_address);
        otEmployeeSpinner = findViewById(R.id.open_task_employee_spinner);
        otStatusSpinner = findViewById(R.id.open_task_status_spinner);
        otCustomerName = findViewById(R.id.open_task_customer_name);
        otCustomerPhone = findViewById(R.id.open_task_customer_phone);
        otCustomerEmail = findViewById(R.id.open_task_customer_email);
        taskNotes = findViewById(R.id.open_task_task_notes);
        appDate = findViewById(R.id.open_task_due_date);
        dateButton = findViewById(R.id.open_task_date_button);
        appTime = findViewById(R.id.open_task_time);
        timeButton = findViewById(R.id.open_task_time_button);
        //editSaveBtn = findViewById(R.id.open_task_save_btn);
        //cancelBtn = findViewById(R.id.open_task_cancel_button);

        //set data and images for buttons
        Customer currentTaskCustomer = mmvm.findCustomerById(task.getCustomerID());
        originalTextColor = otCustomerPhone.getTextColors();
        otTitle.setText(task.getTaskTitle());
        otTitle.setEnabled(false);
        otAddress.setText(task.getTaskAddress());
        otAddress.setFocusable(false);
        otAddress.setTextColor(ContextCompat.getColor(this, R.color.clickable_text));
        otAddress.setClickable(true);
        otCustomerName.setEnabled(false);
        otCustomerPhone.setFocusable(false);
        otCustomerPhone.setTextColor(ContextCompat.getColor(this, R.color.clickable_text));
        otCustomerEmail.setFocusable(false);
        otCustomerEmail.setTextColor(ContextCompat.getColor(this, R.color.clickable_text));

        if (currentTaskCustomer == null) {
            otCustomerName.setText("");
            otCustomerPhone.setText("");
            otCustomerEmail.setText("");
        }
        else {
            otCustomerName.setText(currentTaskCustomer.getCustomerName());
            otCustomerPhone.setText(currentTaskCustomer.getCustomerPhone());
            otCustomerEmail.setText(currentTaskCustomer.getCustomerEmail());
            otCustomerPhone.setClickable(true);
            otCustomerEmail.setClickable(true);
        }
        taskNotes.setText(task.getTaskDescription());
        taskNotes.setEnabled(false);
        appDate.setText("Date: " + task.getTaskDateDue());
        appDateString = task.getTaskDateDue();
        dateButton.setEnabled(false);
        dateButton.setVisibility(View.INVISIBLE);
        appTime.setText("Time: " + task.getTaskAppTime());
        appTimeString = task.getTaskAppTime();
        timeButton.setEnabled(false);
        timeButton.setVisibility(View.INVISIBLE);
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
        statusSelection.add("New");
        statusSelection.add("In Progress");
        statusSelection.add("Closed");
        int currentStatusNumber = task.getTaskStatus();
        String currentStatusText = statusSelection.get(currentStatusNumber - 1);
        statusSelection.remove(currentStatusNumber-1);
        statusSelection.add(0, currentStatusText);
        ArrayAdapter<String> statusSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusSelection);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otStatusSpinner.setAdapter(statusSpinnerAdapter);
        otStatusSpinner.setEnabled(false);

        //save current task data and check if user edited before exiting
        currentTitle = otTitle.getText().toString().trim();
        currentAddress = otAddress.getText().toString().trim();
        currentEmployee = otEmployeeSpinner.getSelectedItem().toString().trim();
        currentStatus = otStatusSpinner.getSelectedItem().toString().trim();
        currentCustomerName = otCustomerName.getText().toString().trim();
        currentCustomerPhone = otCustomerPhone.getText().toString().trim();
        currentCustomerEmail = otCustomerEmail.getText().toString().trim();
        currentNotes = taskNotes.getText().toString();
        currentAppDate = appDateString;
        currentAppTime = appTimeString;

        //set calendar dialog
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(OpenTaskActivity.this,
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
                appDateString = sMonth + "/" + sDay + "/" + year;
                appDate.setText("Date: " + appDateString);
                long days = getDateDiff(currentDateString, appDateString);
                //Toast.makeText(OpenTaskActivity.this, "Setting date as: " + dueDate.getText().toString().substring(5), Toast.LENGTH_LONG).show();
                //Toast.makeText(OpenTaskActivity.this, "Date difference from " + currentDateString + " to " + newDate + " is " + days, Toast.LENGTH_LONG).show();
            }
        };

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        otAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMaps();
            }
        });

        //set click listener for calling phone number
        otCustomerPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhoneNumber();
            }
        });

        //set click listener to send email
        otCustomerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

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
        if (!requestingEmployee.isAdmin()) {
            menu.getItem(0).setVisible(false);
        }
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
            case R.id.delete_entity:
                deleteTask();
                return true;
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void deleteTask() {
        DeleteTaskDialog deleteTaskDialog = new DeleteTaskDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("user", eId);
        bundle.putInt("taskId", task.getTaskId());
        deleteTaskDialog.setArguments(bundle);
        deleteTaskDialog.show(getSupportFragmentManager(), "DeleteTaskDialog");
    }

    private void editSaveTask() {
       if (!editable) {
           //change the edit icon to save
           menu.getItem(1).setIcon(R.drawable.ic_save_white);
           getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
           //enable edit text fields
           otTitle.setEnabled(true);
           otAddress.setTextColor(originalTextColor);
           otAddress.setFocusableInTouchMode(true);
           otAddress.setFocusable(true);
           otAddress.setClickable(false);
           if (requestingEmployee.isAdmin()) {
               otEmployeeSpinner.setEnabled(true);
           }
           otStatusSpinner.setEnabled(true);
           otCustomerName.setEnabled(true);
           otCustomerPhone.setTextColor(originalTextColor);
           otCustomerPhone.setFocusableInTouchMode(true);
           otCustomerPhone.setFocusable(true);
           otCustomerPhone.setClickable(false);
           otCustomerEmail.setTextColor(originalTextColor);
           otCustomerEmail.setFocusableInTouchMode(true);
           otCustomerEmail.setFocusable(true);
           otCustomerEmail.setClickable(false);
           taskNotes.setEnabled(true);
           dateButton.setVisibility(View.VISIBLE);
           dateButton.setEnabled(true);
           timeButton.setVisibility(View.VISIBLE);
           timeButton.setEnabled(true);
           editable = true;
       }
       else {
           if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
                   || !otEmployeeSpinner.getSelectedItem().toString().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().equals(currentStatus)
                   || !otCustomerName.getText().toString().trim().equals(currentCustomerName) || !otCustomerPhone.getText().toString().trim().equals(currentCustomerPhone)
                   || !otCustomerEmail.getText().toString().trim().equals(currentCustomerEmail) || !taskNotes.getText().toString().equals(currentNotes)
                   || !appDateString.equals(currentAppDate) || !appTimeString.equals(currentAppTime))
           {
               //save data and close activity
               if (!otTitle.getText().toString().isEmpty()) {
                   task.setTaskTitle(otTitle.getText().toString().trim());
               }
               else {
                   task.setTaskTitle("Default Title");
               }
               task.setTaskAddress(otAddress.getText().toString().trim());
               if (!otEmployeeSpinner.getSelectedItem().toString().equals("Leave Unassigned")) {
                   String employeeName = otEmployeeSpinner.getSelectedItem().toString();
                   Employee employee = mmvm.findEmployeeByUsername(employeeName);
                   task.setEmployeeID(employee.getEmployeeID());
               }
               else {
                   task.setEmployeeID(-999);
               }
               String status = otStatusSpinner.getSelectedItem().toString();
               if (status.equals("New")) {
                   task.setTaskStatus(1);
               }
               else if (status.equals("In Progress")) {
                   task.setTaskStatus(2);
               }
               else if (status.equals("Closed")) {
                   task.setTaskStatus(3);
               }
               int cId = updateCustomer(otCustomerName.getText().toString(), otCustomerPhone.getText().toString(), otCustomerEmail.getText().toString());
               task.setCustomerID(cId);
               task.setTaskDescription(taskNotes.getText().toString());
               task.setTaskDateDue(appDateString);
               task.setTaskAppTime(appTimeString);
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
//        if (!otTitle.getText().toString().trim().equals(currentTitle) || !otAddress.getText().toString().trim().equals(currentAddress)
//                || !otEmployeeSpinner.getSelectedItem().toString().equals(currentEmployee) || !otStatusSpinner.getSelectedItem().toString().equals(currentStatus))
//        {
//            Toast.makeText(OpenTaskActivity.this, "Data was changed but canceling", Toast.LENGTH_LONG).show();
//        }
//        else
//        {
//            Toast.makeText(OpenTaskActivity.this, "Data unchanged canceling", Toast.LENGTH_LONG).show();
//        }
        closeActivity(RESULT_CANCELED);
    }

    private void closeActivity(int resultCode) {
        Intent intent = new Intent();
        if (resultCode == RESULT_CANCELED) {
            setResult(RESULT_CANCELED, intent);
        }
        else  {
            if (newCustomer) {
                intent.putExtra(NEW_CUSTOMER_NAME_DURING_EDIT, otCustomerName.getText().toString());
                intent.putExtra(EDIT_TASK_ID, task.getTaskId());
            }
            setResult(RESULT_OK, intent);
        }
        finish();
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
        currentDateString = sMonth + "/" + sDay + "/" + year;
        //Toast.makeText(AddTaskActivity.this, "current date would be: " + dateCreated, Toast.LENGTH_LONG).show();
    }

    private long getDateDiff(String startDate, String endDate) {
        long daysTotal = 0;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startingDate = new Date();
        Date endingDate = new Date();
        try {
            startingDate = df.parse(startDate);
            endingDate = df.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMilliseconds = Math.abs(endingDate.getTime() - startingDate.getTime());
        daysTotal = TimeUnit.DAYS.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
        return daysTotal;
    }

    private int updateCustomer(String name, String phone, String email) {
        int id = -888;
        Customer cust;
        if (!name.equals("")) {
            cust = mmvm.findCustomerByName(name);
            if (cust != null) {
                id = cust.getCustomerID();
                if ((!phone.equals(cust.getCustomerPhone()) && !phone.equals("")) || (!email.equals(cust.getCustomerEmail()) && !email.equals(""))) {
                    cust.setCustomerPhone(phone);
                    cust.setCustomerEmail(email);
                    mmvm.update(cust);
                }
            }
            else {
                //new customer
                cust = new Customer(name, phone, email);
                mmvm.insert(cust);
                //cust = mmvm.findCustomerByName(name);
                //id = cust.getCustomerID();
                id = -888;
                newCustomer = true;
            }
        }
        return id;
    }

    private void openMaps() {
        double latitude;
        double longitude;
        if (ContextCompat.checkSelfPermission(OpenTaskActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OpenTaskActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_MAP);
        }
        else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            else {
                latitude = 0.0;
                longitude = 0.0;
            }
            String address = otAddress.getText().toString().trim();
            Uri uri = Uri.parse("geo:" + latitude + ", " + longitude + "?q=" + address);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
    }

    private void callPhoneNumber() {
        String phoneNumber = otCustomerPhone.getText().toString();
        if (phoneNumber.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(OpenTaskActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(OpenTaskActivity.this,
                        new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
            else {
                String dialNumber = "tel:" + phoneNumber;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dialNumber)));
            }
        }
        else {
            Toast.makeText(this, "No Number To Call", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            }
            else {
                Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendEmail() {
        String emailRecipient = otCustomerEmail.getText().toString();
        if (emailRecipient.trim().length() > 0){
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailRecipient);
            emailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String amPm = "AM";
        String leadingZero = "";
        if(hourOfDay >= 12) {
            amPm = "PM";
            if (hourOfDay >= 13 && hourOfDay < 24) {
                hourOfDay-=12;
            }
            else {
                hourOfDay = 12;
            }
        }
        else if (hourOfDay == 0) {
            hourOfDay = 12;
        }
        if (minute < 10) {
            leadingZero = "0";
        }
        appTimeString = hourOfDay + ":" + leadingZero + minute + " " + amPm;
        appTime.setText("Time: " + appTimeString);
    }
}
