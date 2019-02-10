package com.example.albertovenegas.mightymanager.UserInterface;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Adapter.MainListAdapter;
import com.example.albertovenegas.mightymanager.Data.Assignment;
import com.example.albertovenegas.mightymanager.Data.MockDataGathering;
import com.example.albertovenegas.mightymanager.R;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity implements MainListAdapter.itemClickCallback{
    private TextView title;
    private Boolean managerType;
    private Boolean employeeType;
    private Button newAssignmentButton;
    private RecyclerView mainList;
    private MainListAdapter adapter;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //get user access type from signin
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

        listData = (ArrayList) MockDataGathering.getAssignmentData();

        //instantiate recycler view
        mainList = (RecyclerView) findViewById(R.id.mainscreen_list);
        mainList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainListAdapter(MockDataGathering.getAssignmentData(), this);
        mainList.setAdapter(adapter);
        adapter.setItemClickCallback(this);

        //initialize and onclick listeners for buttons
        newAssignmentButton = (Button) findViewById(R.id.mainscreen_new_assignment_button);
        newAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }

    //creates the menu on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //gives functionality when clicking items in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item1:
                Toast.makeText(this, "Item 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item2:
                Toast.makeText(this, "Item 2", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item3:
                Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item4:
                Toast.makeText(this, "Item 4", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_item5:
                Toast.makeText(this, "Item 5", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //opens the dialog box to add new assignment
    public void openDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainScreen.this);
        View dialogView = getLayoutInflater().inflate(R.layout.new_assignment_dialog, null);
        final EditText newAssignmentName = (EditText) dialogView.findViewById(R.id.new_assignment_dialog_assign_title);
        final EditText newAssignmentAddress = (EditText) dialogView.findViewById(R.id.new_assignment_dialog_assign_address);
        Button createButton = (Button) dialogView.findViewById(R.id.new_assignment_dialog_add_button);
        dialogBuilder.setView(dialogView);
        final AlertDialog dialog = dialogBuilder.create();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainScreen.this, "Button in dialog works", Toast.LENGTH_SHORT).show();
                if(!newAssignmentName.getText().toString().isEmpty() && !newAssignmentAddress.getText().toString().isEmpty()) {
                    Assignment newAssignment = newAssignmentElement(newAssignmentName.getText().toString(), newAssignmentAddress.getText().toString());
                    listData.add(0,newAssignment);
                    adapter.setListData(listData);
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
                else {
                    Toast.makeText(MainScreen.this, "Field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    public Assignment newAssignmentElement(String title, String address){
        Assignment newAssignment = new Assignment();
        newAssignment.setTitle(title);
        newAssignment.setAddress(address);
        newAssignment.setAssignedEmployee("unassigned");
        newAssignment.setStatusIcon(R.drawable.ic_assignment_inprogress_24dp);
        newAssignment.setEditIcon(android.R.drawable.ic_menu_edit);
        newAssignment.setComplete(false);
        return newAssignment;
    }


    @Override
    public void onItemClick(int p) {
        Assignment assignment = (Assignment) listData.get(p);
        Toast.makeText(MainScreen.this, "Will open new page", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditIconClick(int p) {
        Assignment assignment = (Assignment) listData.get(p);
        Toast.makeText(MainScreen.this, "Will edit here", Toast.LENGTH_SHORT).show();
        assignment.setComplete(true);
        adapter.setListData(listData);
        adapter.notifyDataSetChanged();
    }
}
