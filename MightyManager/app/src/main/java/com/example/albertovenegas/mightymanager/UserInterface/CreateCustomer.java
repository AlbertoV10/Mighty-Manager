package com.example.albertovenegas.mightymanager.UserInterface;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

public class CreateCustomer extends AppCompatActivity {
    private EditText customerName;
    private EditText customerPhone;
    private EditText customerEmail;
    private MightyManagerViewModel mmvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        customerName = findViewById(R.id.create_customer_name);
        customerPhone = findViewById(R.id.create_customer_phone);
        customerEmail = findViewById(R.id.create_customer_email);

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveCustomer();
                return true;
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

    private void saveCustomer() {
        if (customerName.getText().toString().isEmpty() || customerPhone.getText().toString().isEmpty() || customerEmail.getText().toString().isEmpty())
        {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
        }
        else {
            String name = customerName.getText().toString().trim();
            String phone = customerPhone.getText().toString().trim();
            String email = customerEmail.getText().toString().trim();

            mmvm.insert(new Customer(name, phone, email));
            Toast.makeText(this, "New Customer: " + name, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
