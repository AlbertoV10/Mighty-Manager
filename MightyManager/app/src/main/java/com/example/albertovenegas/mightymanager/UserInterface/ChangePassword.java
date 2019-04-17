package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class ChangePassword extends AppCompatActivity {
    private TextView message;
    private TextView errorMessage;
    private EditText newPassword;
    private EditText confirmPassword;
    private Button changePasswordButton;
    private MightyManagerViewModel mmvm;
    private Employee currentEmployee;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toast.makeText(this, "Change password", Toast.LENGTH_SHORT).show();

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
        int id = getIntent().getExtras().getInt("user");
        currentEmployee = mmvm.findEmployeeById(id);

        if (!currentEmployee.isFirstSignIn()) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
            getSupportActionBar().setTitle("");
        }


        message = findViewById(R.id.change_password_message);
        errorMessage = findViewById(R.id.change_password_retry_message);
        newPassword = findViewById(R.id.change_password_new_password);
        confirmPassword = findViewById(R.id.change_password_confirm_password);
        changePasswordButton = findViewById(R.id.change_password_button);
        changePasswordButton.setEnabled(false);

        errorMessage.setVisibility(View.INVISIBLE);

        message.setText("Hello " + currentEmployee.getEmployeeFirstName() + " " + currentEmployee.getEmployeeLastName()+ ". Please enter and confirm your new password.");

        if (currentEmployee.isFirstSignIn()) {
            changePasswordButton.setVisibility(View.VISIBLE);
            changePasswordButton.setEnabled(true);
        }
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAndChange();
            }
        });
    }

    private void confirmAndChange() {
        String nPassword = newPassword.getText().toString();
        String cPassword = confirmPassword.getText().toString();
        if (nPassword.equals(cPassword)) {
            currentEmployee.setEmployeePassword(nPassword);
            mmvm.update(currentEmployee);
            Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show();
            if (currentEmployee.isFirstSignIn()) {
                Intent intent = new Intent(ChangePassword.this, FirstTimeSignUp.class);
                intent.putExtra("user", currentEmployee.getEmployeeID());
                startActivity(intent);
            }
            else {
                finish();
            }
        }
        else {
            errorMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_task_menu, menu);
        menu.getItem(0).setIcon(R.drawable.ic_save_white);
        if (currentEmployee.isFirstSignIn()) {
            menu.getItem(0).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_task:
                confirmAndChange();
                return true;
//            case R.id.save_edits:
//                saveTask();
//                return true;
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
}
