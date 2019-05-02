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

import com.example.albertovenegas.mightymanager.Adapter.CustomerListAdapter;
import com.example.albertovenegas.mightymanager.Database.Customer;
import com.example.albertovenegas.mightymanager.Database.Employee;
import com.example.albertovenegas.mightymanager.Database.MightyManagerViewModel;
import com.example.albertovenegas.mightymanager.R;

import java.util.List;

public class CustomerList extends AppCompatActivity {
    public static final String CUSTOMER_DESCRIPTION_EXTRA_KEY = "customer.list.screen.customer.id";
    public static final int CUSTOMER_DESCRIPTION_TASK = 1;

    private MightyManagerViewModel mmvm;
    private CustomerListAdapter adapter;

    int eId;
    private Employee currentEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("");

        mmvm = ViewModelProviders.of(this).get(MightyManagerViewModel.class);

        if (getIntent().hasExtra("user")) {
            eId = getIntent().getExtras().getInt("user");
            currentEmployee = mmvm.findEmployeeById(eId);
        }

        RecyclerView recyclerView = findViewById(R.id.customer_screen_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomerListAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CustomerListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Customer customer) {
                int customerId = customer.getCustomerID();
                Intent intent = new Intent(CustomerList.this, CustomerDetails.class);
                intent.putExtra(CUSTOMER_DESCRIPTION_EXTRA_KEY, customerId);
                intent.putExtra("user", eId);
                startActivityForResult(intent, CUSTOMER_DESCRIPTION_TASK);
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
        if (requestCode == CUSTOMER_DESCRIPTION_TASK) {
            if (resultCode == RESULT_OK) {
                //Toast.makeText(this, "Customer Edited", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Toast.makeText(this, "Customer Unchanged", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
