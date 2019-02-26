package com.example.albertovenegas.mightymanager.UserInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class OpenTaskActivity extends AppCompatActivity {
    private MightyManagerViewModel mmvm;
    private EditText otTitle;
    private Spinner otEmployeeSpinner;
    private Spinner otStatusSpinner;
    private String[] statusSelection = {"Open", "In Progress", "Closed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_task);

    }
}
