package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class ForgotPassword extends AppCompatActivity {
    public static final String FORGOT_PASSWORD_ACTIVITY = "forgot.password.activity.screen";
    private MightyManagerViewModel mmvm;
    private EditText username;
    private TextView errorMessage;
    private Button nextPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        username = findViewById(R.id.forgot_password_username);
        errorMessage = findViewById(R.id.forgot_password_user_not_found);
        nextPageButton = findViewById(R.id.forgot_password_button);

        errorMessage.setVisibility(View.INVISIBLE);

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateUsername();
            }
        });
    }

    private void validateUsername() {
        String name = username.getText().toString().trim();
        Employee employee = mmvm.findEmployeeByUsername(name);
        if (employee != null) {
            Intent intent = new Intent(ForgotPassword.this, ChangePassword.class);
            intent.putExtra(FORGOT_PASSWORD_ACTIVITY, true);
            intent.putExtra("user", employee.getEmployeeID());
            startActivity(intent);
        }
        else {
            errorMessage.setVisibility(View.VISIBLE);
        }
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
}
