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
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Adapter.EmployeeListAdapter;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class EmployeeList extends AppCompatActivity {
    public static final String EMPLOYEE_DESCRIPTION_EXTRA_KEY = "employee.list.screen.employee.id";
    public static final String REQUESTING_EMPLOYEE_EXTRA_KEY = "employee.list.screen.requester.privilege";
    public static final int EMPLOYEE_DESCRIPTION_TASK = 1;
    public static final int EMPLOYEE_TASKS_ACTIVITY = 2;
    private MightyManagerViewModel mmvm;
    private EmployeeListAdapter adapter;
    private int currentEmployeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        if (getIntent().hasExtra("user")) {
            currentEmployeeId = getIntent().getExtras().getInt("user");
        }

        RecyclerView recyclerView = findViewById(R.id.employee_screen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EmployeeListAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(Employee employee) {
                int employeeId = employee.getEmployeeID();
                Intent intent = new Intent(EmployeeList.this, EmployeeDetails.class);
                intent.putExtra("user", currentEmployeeId);
                intent.putExtra(EMPLOYEE_DESCRIPTION_EXTRA_KEY, employeeId);
                startActivityForResult(intent, EMPLOYEE_DESCRIPTION_TASK);
            }
            public void onTotalTasksClicked(Employee employee) {
                int employeeId = employee.getEmployeeID();
                Toast.makeText(EmployeeList.this, "See tasks for employee: " + employee.getEmployeeUsername(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EmployeeList.this, EmployeeTasks.class);
                intent.putExtra(EMPLOYEE_DESCRIPTION_EXTRA_KEY, employeeId);
                intent.putExtra(REQUESTING_EMPLOYEE_EXTRA_KEY, currentEmployeeId);
                startActivityForResult(intent, EMPLOYEE_TASKS_ACTIVITY);
            }
        });

        mmvm.getAllEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                adapter.setEmployeeList(employees);
            }
        });

        mmvm.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (android.support.v7.widget.SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
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
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EMPLOYEE_DESCRIPTION_TASK) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Employee Edited", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Employee Unchanged", Toast.LENGTH_SHORT).show();

            }
        }
//        else if (requestCode == EMPLOYEE_TASKS_ACTIVITY) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Employee tasks views", Toast.LENGTH_SHORT).show();
//            }
//            else
//            {
//                Toast.makeText(this, "Employee tasks viewed", Toast.LENGTH_SHORT).show();
//
//            }
//        }
    }

}
