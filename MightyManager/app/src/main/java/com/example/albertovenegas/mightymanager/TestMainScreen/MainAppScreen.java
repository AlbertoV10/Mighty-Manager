package com.example.albertovenegas.mightymanager.TestMainScreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.CpuUsageInfo;
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
import com.example.albertovenegas.mightymanager.UnusedFiles.MainListAdapter;

import java.util.List;

public class MainAppScreen extends AppCompatActivity implements MainAppListAdapter.itemClickCallback{
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
                Task task = new Task("testfromScreen", "address", 1234, 1);
                mightyManagerViewModel.insert(task);
            }
        });
    }

    @Override
    public void onItemClick(int p) {
        Toast.makeText(MainAppScreen.this, "Will open activity here", Toast.LENGTH_SHORT).show();

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
}
