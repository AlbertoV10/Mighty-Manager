package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Adapter.EmployeeListAdapter;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class EmployeeList extends AppCompatActivity {
    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.employee_screen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final EmployeeListAdapter adapter = new EmployeeListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EmployeeListAdapter.OnItemClickListener() {
            @Override
            public void onItemCLick(Employee employee) {
                Toast.makeText(EmployeeList.this, "Edit employee: " + employee.getEmployeeUsername(), Toast.LENGTH_SHORT).show();
            }
        });

        mmvm.getAllEmployees().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                adapter.setEmployeeList(employees);
            }
        });

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
}
