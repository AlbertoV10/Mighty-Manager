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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class MainAppScreen extends AppCompatActivity implements MainAppListAdapter.itemClickCallback{
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    public static final String OPEN_TASK_ID = "main.app.screen.task.id";

    private MightyManagerViewModel mightyManagerViewModel;
    private FloatingActionButton fab;
    private MainAppListAdapter adapter;
    private TextView mainTitle;
    private int employeeId; //change this to use employee id since no duplication possible
    Employee currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_screen);

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

        RecyclerView recyclerView = findViewById(R.id.app_mainscreen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAppListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        mightyManagerViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

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

    @Override
    public void onItemClick(int p) {
        Toast.makeText(MainAppScreen.this, "Will open activity here for task:" + p, Toast.LENGTH_SHORT).show();
        //open selected task here
//        Intent openTask = new Intent(MainAppScreen.this, OpenTaskActivity.class);
//        int taskID = adapter.getTaskAt(p).getTaskId();
//        openTask.putExtra(OPEN_TASK_ID, taskID);
//        startActivity(openTask);
    }

    @Override
    public void onIconClick(int p) {
        Toast.makeText(MainAppScreen.this, "Will edit here", Toast.LENGTH_SHORT).show();
        Task currentTask = adapter.getTasks().get(p);
        int currentStatus = currentTask.getTaskStatus();
        currentStatus = (currentStatus + 1)%4;
        currentTask.setTaskStatus(currentStatus);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddTaskActivity.EXTRA_TITLE);
            String address = data.getStringExtra(AddTaskActivity.EXTRA_ADDRESS);
            String employee = data.getStringExtra(AddTaskActivity.EXTRA_EMPLOYEE_NAME);
            if (!employee.equals("Leave Unassigned")) {
                int employeeID = mightyManagerViewModel.findEmployeeByName(employee).getEmployeeID();
            }
            Toast.makeText(this, title +" "+address+""+employee, Toast.LENGTH_LONG).show();
            //add new task here
        }
    }
}