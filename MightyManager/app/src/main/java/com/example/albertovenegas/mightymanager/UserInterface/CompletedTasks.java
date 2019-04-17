package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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

public class CompletedTasks extends AppCompatActivity {
    public static final int ADD_TASK_REQUEST = 1;
    public static final int EDIT_TASK_REQUEST = 2;
    public static final String OPEN_TASK_EXTRA_KEY = "main.app.screen.task.id";
    public static final String OPEN_TASK_INCOMING_ACTIVY = "CompletedTasks";
    public static final String MY_PROFILE_USER_ID = "completed.tasks.screen.user.id";
    private String[] filterChoices = {"All", "New", "In Progress", "Closed", "Sort by Due Date"};

    private MightyManagerViewModel mightyManagerViewModel;
    //private MainAppListAdapter adapter;
    private MainTaskListAdapter adapter;
    private TextView mainTitle;
    private int employeeId; //change this to use employee id since no duplication possible
    Employee currentUser;
    private Spinner filterSpinner;
    private List<Task> taskList = new ArrayList<>();

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_tasks);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        mightyManagerViewModel = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        employeeId = getIntent().getExtras().getInt("user");
        currentUser = mightyManagerViewModel.findEmployeeById(employeeId);

        //intialize task filter
        filterSpinner = findViewById(R.id.completed_tasks_filter_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filterChoices);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);

        //set up recycler view
        RecyclerView recyclerView = findViewById(R.id.completed_tasks_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new MainAppListAdapter(this);
        adapter = new MainTaskListAdapter();
        recyclerView.setAdapter(adapter);
        //adapter.setItemClickCallback(this);

        adapter.setOnItemClickListener(new MainTaskListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {
                Toast.makeText(CompletedTasks.this, "Edit Task: " + task.getTaskTitle(), Toast.LENGTH_SHORT).show();
                Intent openTask = new Intent(CompletedTasks.this, OpenTaskActivity.class);
                int taskID = task.getTaskId();
                openTask.putExtra(OPEN_TASK_EXTRA_KEY, taskID);
                startActivityForResult(openTask, EDIT_TASK_REQUEST);
            }
        });

        mightyManagerViewModel.getAllEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                adapter.setEmployees(employees);
            }
        });

        mightyManagerViewModel.getAllClosedTasks().observe(this, new Observer<List<Task>>() {
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

        //filter the tasks by filter value
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CompletedTasks.this, "spinner selection: " + adapterView.getSelectedItem().toString() + "pos: " + i, Toast.LENGTH_SHORT).show();
                adapter.setTasks(filterTasks(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_task_menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                cancelScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void cancelScreen() {
        //closeActivity(RESULT_CANCELED);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_TASK_REQUEST) {
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
}
