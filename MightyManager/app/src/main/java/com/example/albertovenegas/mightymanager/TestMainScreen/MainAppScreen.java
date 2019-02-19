package com.example.albertovenegas.mightymanager.TestMainScreen;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.Database.Task;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class MainAppScreen extends AppCompatActivity {
    private MightyManagerViewModel mightyManagerViewModel;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_screen);

        RecyclerView recyclerView = findViewById(R.id.app_mainscreen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final MainAppListAdapter adapter = new MainAppListAdapter(this);
        recyclerView.setAdapter(adapter);

        mightyManagerViewModel = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
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
}
