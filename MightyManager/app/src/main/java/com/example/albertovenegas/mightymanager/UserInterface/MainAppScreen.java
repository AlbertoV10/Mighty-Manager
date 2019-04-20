package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Adapter.MainAppListAdapter;
import com.example.albertovenegas.mightymanager.Adapter.MainTaskListAdapter;
import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainAppScreen extends AppCompatActivity {
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    public static final String OPEN_TASK_EXTRA_KEY = "main.app.screen.task.id";
    public static final String OPEN_TASK_INCOMING_ACTIVY = "MainAppScreen";
    public static final String MY_PROFILE_USER_ID = "main.app.screen.user.id";
    private String[] filterChoices = {"All", "New", "In Progress", "Closed", "Sort by Appointment Date"};

    private MightyManagerViewModel mightyManagerViewModel;
    private FloatingActionButton fab;
    //private MainAppListAdapter adapter;
    private MainTaskListAdapter adapter;
    //private TextView mainTitle;
    private int employeeId; //change this to use employee id since no duplication possible
    Employee currentUser;
    private Spinner filterSpinner;
    private List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_screen);

        getSupportActionBar().setTitle("");

        mightyManagerViewModel = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        employeeId = getIntent().getExtras().getInt("user");
        currentUser = mightyManagerViewModel.findEmployeeById(employeeId);

//        mainTitle = (TextView) findViewById(R.id.app_mainscreen_title);
//        if(currentUser.isAdmin()) {
//            mainTitle.setText("Manager Assignments");
//        }
//        else {
//            mainTitle.setText("Employee Assignments");
//        }

        //intialize task filter
        filterSpinner = findViewById(R.id.app_mainscreen_filter_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterChoices);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);

        //set up recycler view
        RecyclerView recyclerView = findViewById(R.id.app_mainscreen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new MainAppListAdapter(this);
        adapter = new MainTaskListAdapter();
        recyclerView.setAdapter(adapter);
        //adapter.setItemClickCallback(this);

        adapter.setOnItemClickListener(new MainTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                //Toast.makeText(MainAppScreen.this, "Edit Task: " + task.getTaskTitle(), Toast.LENGTH_SHORT).show();
                Intent openTask = new Intent(MainAppScreen.this, OpenTaskActivity.class);
                int taskID = task.getTaskId();
                openTask.putExtra(OPEN_TASK_EXTRA_KEY, taskID);
                openTask.putExtra("user", currentUser.getEmployeeID());
                startActivityForResult(openTask, EDIT_TASK_REQUEST);
            }
        });
//        if(currentUser.isAdmin()) {
//            mightyManagerViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
//                @Override
//                public void onChanged(@Nullable List<Task> tasks) {
//                    adapter.setTasks(tasks);
//                    taskList.clear();
//                    taskList.addAll(tasks);
//                }
//            });
//        }
//        else {
//            taskList = mightyManagerViewModel.findTaskByEmployee(currentUser.getEmployeeID());
//            adapter.setTasks(taskList);
//        }
        //adapter.setEmployees(mightyManagerViewModel.getEmployeesList());
        mightyManagerViewModel.getAllEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });

        mightyManagerViewModel.getAllUnclosedTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                //adapter.setTasks(tasks);
                if (tasks != null) {
                    taskList.clear();
                    taskList.addAll(tasks);
                }
                if (!currentUser.isAdmin()) {
                    taskList = mightyManagerViewModel.findTaskByEmployee(currentUser.getEmployeeID());
                    taskList = filterUnclosedTasksForUser(taskList);
                    adapter.setTasks(taskList);
                }
                else {
                    adapter.setTasks(taskList);
                }

            }
        });

        //Toast.makeText(this, "Total tasks on phone: " + taskList.size(), Toast.LENGTH_SHORT).show();


        //filter the tasks by filter value
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(MainAppScreen.this, "spinner selection: " + adapterView.getSelectedItem().toString() + "pos: " + i, Toast.LENGTH_SHORT).show();
                adapter.setTasks(filterTasks(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //floating add task button
        fab = findViewById(R.id.app_mainscreen_new_assignment_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Task task = new Task("testfromScreen", "address", 1234, 1);
                //mightyManagerViewModel.insert(task);
                Intent intent = new Intent(MainAppScreen.this, AddTaskActivity.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });
        if (!currentUser.isAdmin()) {
            //if user is not admin, disable floating action button
            fab.setEnabled(false);
            fab.hide();
        }
    }

//    @Override
//    public void onItemClick(int p) {
//        Toast.makeText(MainAppScreen.this, "Will open activity here for task:" + p, Toast.LENGTH_SHORT).show();
//        //open selected task here
//        Intent openTask = new Intent(MainAppScreen.this, OpenTaskActivity.class);
//        int taskID = adapter.getTaskAt(p).getTaskId();
//        openTask.putExtra(OPEN_TASK_EXTRA_KEY, taskID);
//        startActivityForResult(openTask, EDIT_TASK_REQUEST);
//    }
//
//    @Override
//    public void onIconClick(int p) {
//        Toast.makeText(MainAppScreen.this, "Will edit here", Toast.LENGTH_SHORT).show();
////        Task currentTask = adapter.getTasks().get(p);
////        int currentStatus = currentTask.getTaskStatus();
////        currentStatus = (currentStatus + 1)%4;
////        currentTask.setTaskStatus(currentStatus);
////        adapter.notifyDataSetChanged();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST) {
            if (resultCode == RESULT_OK) {
//                intent.putExtra(EXTRA_TITLE, title);
//                intent.putExtra(EXTRA_ADDRESS, address);
//                intent.putExtra(EXTRA_EMPLOYEE_ID, eId);
//                intent.putExtra(EXTRA_CUSTOMER_ID, cId);
//                intent.putExtra(EXTRA_NOTES, notes);
//                intent.putExtra(EXTRA_DATE_CREATED, dateMade);
//                intent.putExtra(EXTRA_DATE_DUE, dateDue);
//                intent.putExtra(EXTRA_CUSTOMER_NAME, cName);
                String title = data.getStringExtra(AddTaskActivity.EXTRA_TITLE);
                String address = data.getStringExtra(AddTaskActivity.EXTRA_ADDRESS);
                int eId = data.getIntExtra(AddTaskActivity.EXTRA_EMPLOYEE_ID, -999);
                int cId = data.getIntExtra(AddTaskActivity.EXTRA_CUSTOMER_ID, -888);
                String notes = data.getStringExtra(AddTaskActivity.EXTRA_NOTES);
                String dateMade = data.getStringExtra(AddTaskActivity.EXTRA_DATE_CREATED);
                String appDate = data.getStringExtra(AddTaskActivity.EXTRA_DATE_DUE);
                String cName = data.getStringExtra(AddTaskActivity.EXTRA_CUSTOMER_NAME);
                if (cId == -888) {
                    if (!cName.equals("")) {
                        Customer findCustomer = mightyManagerViewModel.findCustomerByName(cName);
                        cId = findCustomer.getCustomerID();
                    }
                }
                mightyManagerViewModel.insert(new Task(title, address, eId, 1, cId, notes, dateMade, appDate));
                adapter.notifyDataSetChanged();
                filterSpinner.setSelection(0);
                Toast.makeText(this, "New task was added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "No new task added", Toast.LENGTH_SHORT).show();

            }
        }
        else if (requestCode == EDIT_TASK_REQUEST) {
            if (resultCode == RESULT_OK) {
                //data was changedF
                adapter.notifyDataSetChanged();
                filterSpinner.setSelection(0);
                Toast.makeText(this, "data was changed in edit mode", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //data unchanged
                Toast.makeText(this, "data was NOT changed in edit mode", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        MenuItem createEmployee = menu.findItem(R.id.menu_create_employee);
        MenuItem createCustomer = menu.findItem(R.id.menu_create_customer);
        if (!currentUser.isAdmin()) {
            if (createEmployee != null) {
                createEmployee.setVisible(false);
            }
            if (createCustomer != null) {
                createCustomer.setVisible(false);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_sign_out:
                signOut();
                return true;
            case R.id.menu_create_employee:
                createEmployee();
                return true;
            case R.id.menu_employees_list:
                openEmployeeList();
                return true;
            case R.id.menu_my_profile:
                openMyProfile();
                return true;
            case R.id.menu_create_customer:
                createCustomer();
                return true;
            case R.id.menu_customers_list:
                openCustomerList();
                return true;
            case R.id.menu_item_view_org:
                openOrganizationDetails();
                return true;
            case R.id.menu_completed_tasks:
                openCompletedTasks();
                return true;
            case R.id.menu_debug:
                debug();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void debug() {
        Intent newDebugIntent = new Intent(MainAppScreen.this, Debug.class);
        startActivity(newDebugIntent);
    }

    private void createEmployee() {
        Intent newEmployeeIntent = new Intent(MainAppScreen.this, CreateEmployee.class);
        newEmployeeIntent.putExtra("user", currentUser.getEmployeeID());
        startActivity(newEmployeeIntent);
    }

    private void createCustomer() {
        Intent newCustomerIntent = new Intent(MainAppScreen.this, CreateCustomer.class);
        startActivity(newCustomerIntent);
    }

    private void signOut() {
        Intent signOutIntent = new Intent(MainAppScreen.this, SignInPage.class);
        startActivity(signOutIntent);
    }

    private void openEmployeeList() {
        Intent employeeListIntent = new Intent(MainAppScreen.this, EmployeeList.class);
        employeeListIntent.putExtra("user", employeeId);
        startActivity(employeeListIntent);
    }

    private void openMyProfile() {
        Intent myProfileIntent = new Intent(MainAppScreen.this, MyProfile.class);
        myProfileIntent.putExtra(MY_PROFILE_USER_ID, currentUser.getEmployeeID());
        startActivity(myProfileIntent);
    }

    private void openCustomerList() {
        Intent customerListIntent = new Intent(MainAppScreen.this, CustomerList.class);
        customerListIntent.putExtra("user", employeeId);
        startActivity(customerListIntent);
    }

    private void openOrganizationDetails() {
        Intent orgDetailsIntent = new Intent(MainAppScreen.this, OrganizationDetails.class);
        startActivity(orgDetailsIntent);
    }

    private void openCompletedTasks() {
        Intent completedTasksIntent = new Intent(MainAppScreen.this, CompletedTasks.class);
        completedTasksIntent.putExtra("user", currentUser.getEmployeeID());
        startActivity(completedTasksIntent);
    }

    private List<Task> filterTasks(int type) {
        //List<Task> tasks = mightyManagerViewModel.getTaskList();
        List<Task> tasksFiltered = new ArrayList<>();
        if (type == 0) {
            return taskList;
        }
        else if (type >= 1 && type <= 3){
            for (int i = 0; i < taskList.size(); i++) {
                // filter new tasks
                if (taskList.get(i).getTaskStatus() == type) {
                    tasksFiltered.add(taskList.get(i));
                }
            }
        }
        else {
            tasksFiltered = sortByDate(type);
        }
        return tasksFiltered;
    }

    private List<Task> sortByDate(int type) {
        List<Task> dateFilteredTasks = new ArrayList<>();
        //ArrayList<String> stringDates = new ArrayList<>();
        //ArrayList<Date> dates = new ArrayList<>();


        if (type == 4) {
            //sort by most soon due
            dateFilteredTasks.addAll(taskList);
            quickSortdates(dateFilteredTasks, 0, dateFilteredTasks.size()-1);
        }
        return dateFilteredTasks;
    }

    private List<Task> quickSortdates(List<Task> tasks, int begin, int end) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if (begin < end) {
            int partitionIndex = partition(tasks, begin, end, df);

            quickSortdates(tasks, begin, partitionIndex-1);
            quickSortdates(tasks, partitionIndex+1, end);
        }
        return tasks;
    }

    private int partition(List<Task> tasks, int begin, int end, DateFormat df) {
        Date pivot = new Date();
        try {
            pivot = df.parse(tasks.get(end).getTaskDateDue());
        }
        catch (ParseException pe) {
            pe.printStackTrace();
        }
        int i = begin-1;
        Date currentDate = new Date();
        for (int j = begin; j < end; j++) {
            try{
                currentDate = df.parse(tasks.get(j).getTaskDateDue());
            }
            catch (ParseException pe) {
                pe.printStackTrace();
            }
            if (currentDate.getTime() <= pivot.getTime()) {
                i++;

                Task swapTemp = tasks.get(i);
                tasks.set(i, tasks.get(j));
                tasks.set(j, swapTemp);
            }
        }
        Task swapTemp = tasks.get(i+1);
        tasks.set(i+1, tasks.get(end));
        tasks.set(end, swapTemp);
        return i+1;

    }

    private List<Task> filterUnclosedTasksForUser(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskStatus() != 3) {
                filteredTasks.add(tasks.get(i));
            }
        }
        return filteredTasks;
    }
}
