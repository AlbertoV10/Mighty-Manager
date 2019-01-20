package com.example.albertovenegas.mightymanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {
    String assignmentsTest[] = new String [] {"Assignment 1", "Assignment 2", "Assignment 3", "Assignment 4"};
    private ListView mainList;
    private TextView title;
    private Boolean managerType;
    private Boolean employeeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        managerType = getIntent().getExtras().getBoolean("managerUserType");
        employeeType = getIntent().getExtras().getBoolean("employeeUserType");
        title = (TextView) findViewById(R.id.mainscreen_title);
        if(managerType)
        {
            title.setText("Manager: Assignments");
        }
        else
        {
            title.setText("Employee: Assignments");
        }

        mainList = (ListView) findViewById(R.id.mainscreen_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, assignmentsTest);
        mainList.setAdapter(adapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainScreen.this, assignmentsTest[position], Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item1:
                Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item2:
                Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
