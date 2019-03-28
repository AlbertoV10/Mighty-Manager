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
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;
import java.util.List;

public class MainAppScreen extends AppCompatActivity {
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    public static final String OPEN_TASK_EXTRA_KEY = "main.app.screen.task.id";
    public static final String OPEN_TASK_INCOMING_ACTIVY = "MainAppScreen";
    private String[] filterChoices = {"All", "New", "In Progress", "Closed"};

    private MightyManagerViewModel mightyManagerViewModel;
    private FloatingActionButton fab;
    //private MainAppListAdapter adapter;
    private MainTaskListAdapter adapter;
    private TextView mainTitle;
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

        mainTitle = (TextView) findViewById(R.id.app_mainscreen_title);
        if(currentUser.isAdmin()) {
            mainTitle.setText("Manager Assignments");
        }
        else {
            mainTitle.setText("Employee Assignments");
        }

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
        adapter.setEmployees(mightyManagerViewModel.getEmployeesList());

        adapter.setOnItemClickListener(new MainTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Toast.makeText(MainAppScreen.this, "Edit Task: " + task.getTaskTitle(), Toast.LENGTH_SHORT).show();
                Intent openTask = new Intent(MainAppScreen.this, OpenTaskActivity.class);
                int taskID = task.getTaskId();
                openTask.putExtra(OPEN_TASK_EXTRA_KEY, taskID);
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

        mightyManagerViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                //adapter.setTasks(tasks);
                if (tasks != null) {
                    taskList.clear();
                    taskList.addAll(tasks);
                }
                if (!currentUser.isAdmin()) {
                    taskList = mightyManagerViewModel.findTaskByEmployee(currentUser.getEmployeeID());
                    adapter.setTasks(taskList);
                }
                else {
                    adapter.setTasks(taskList);
                }

            }
        });

        Toast.makeText(this, "Total tasks on phone: " + taskList.size(), Toast.LENGTH_SHORT).show();


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
                //data was changed
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
            case R.id.menu_create_customer:
                //createCustomer();
                return true;
            case R.id.menu_customers_list:
                //openCustomerList();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void createEmployee() {
        Intent newEmployeeIntent = new Intent(MainAppScreen.this, CreateEmployee.class);
        startActivity(newEmployeeIntent);
    }

    private void signOut() {
        Intent signOutIntent = new Intent(MainAppScreen.this, MainLogin.class);
        startActivity(signOutIntent);
    }

    private void openEmployeeList() {
        Intent employeeListIntent = new Intent(MainAppScreen.this, EmployeeList.class);
        startActivity(employeeListIntent);
    }

    private List<Task> filterTasks(int type) {
        //List<Task> tasks = mightyManagerViewModel.getTaskList();
        List<Task> tasksFiltered = new ArrayList<>();
        if (type == 0) {
            return taskList;
        }
        else {
            for (int i = 0; i < taskList.size(); i++) {
                // filter new tasks
                if (taskList.get(i).getTaskStatus() == type) {
                    tasksFiltered.add(taskList.get(i));
                }
            }
        }
        return tasksFiltered;
    }
}
