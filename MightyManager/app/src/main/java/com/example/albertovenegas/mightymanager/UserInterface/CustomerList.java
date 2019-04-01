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

import com.example.albertovenegas.mightymanager.Adapter.CustomerListAdapter;
import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class CustomerList extends AppCompatActivity {
    private MightyManagerViewModel mmvm;
    private CustomerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.customer_screen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CustomerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Customer customer) {
                Toast.makeText(CustomerList.this, "Edit Customer: " + customer.getCustomerName(), Toast.LENGTH_SHORT).show();
            }
        });

        mmvm.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                adapter.setCustomerList(customers);
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
